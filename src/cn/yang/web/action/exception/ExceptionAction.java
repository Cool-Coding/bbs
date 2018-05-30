package cn.yang.web.action.exception;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.yang.constant.WebConstants;
import cn.yang.domain.ExceptionLog;
import cn.yang.service.SystemLogService;
import cn.yang.util.SystemLogHelper;
import cn.yang.web.action.base.ActionBase;
import cn.yang.web.util.MemberAuthenticationUtils;
import cn.yang.web.util.ReturnPathUtils;

@Controller("/exception")
public class ExceptionAction extends ActionBase {

	@Resource(name="systemLogServiceImpl")
	private SystemLogService systemLogService;
	/**
	 * 记录异常并转到异常处理页面
	 */
	public ActionForward showMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try { // 方法不再抛出异常, 否则就可能会死循环
			Object flag = request.getAttribute(WebConstants.REQUEST_HAS_EXCEPTIONACTION);
			// 通过一个标志预防死循环, 预防return那儿还有可能抛异常, 或是其他未想到的情况
			if (flag != null) {
				saveExceptionLog(new Exception("在ExceptionAction中产生死循环!"), request);
				return null;
			}

			Exception ex = (Exception) request.getAttribute(Globals.EXCEPTION_KEY);
			saveExceptionLog(ex, request);
			request.setAttribute("exception", ex);
		} catch (Exception e) {
			try {
				saveExceptionLog(e, request);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}

		request.setAttribute(WebConstants.REQUEST_HAS_EXCEPTIONACTION, this);
		return mapping.findForward("showMessage");
	}

	/**
	 * 权限不足异常处理页面
	 */
	public ActionForward permissionDenied(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 1. 如果未登陆, 转到登陆界面
		if (!MemberAuthenticationUtils.isLoggedOn(request)) {
			// FIXME 访问/a.do,req.getReqURI()="/a.do"。转发到/b.do后，同一请求，req.getReqURI()="/b.do"？注意！
			// 可以在登陆后再转到登陆前的页面, 这个页面地址可以放到 request 作用域中;
			String returnPath = (String)request.getAttribute(WebConstants.REQUEST_ENCODED_RETURN_PATH);
			// 转到登录页面
			ReturnPathUtils.toLoginWithReturnPath(request, response, returnPath);
			return null;
		}

		Exception ex = (Exception) request.getAttribute(Globals.EXCEPTION_KEY);
		request.setAttribute("exception", ex);
		return mapping.findForward("permissionDenied");
	}

	/**
	 * 生成并保存ExcpetionLog
	 * 
	 * @param ex
	 * @param request
	 */
	private void saveExceptionLog(Exception ex, HttpServletRequest request) {
		ExceptionLog elog = new ExceptionLog();
		elog.setLogTime(new Date());
		elog.setOperator(MemberAuthenticationUtils.getLoggedOnUser(request));
		elog.setOperIpAddr(request.getRemoteAddr());

		if (ex != null) {
			elog.setClassName(ex.getClass().getName());
			elog.setSummary(ex.getMessage());
			elog.setDetailMessage(SystemLogHelper.getDetailMessage(ex));
		}

		systemLogService.save(elog);

		// 替换为可以在网页上显示的换行与空白
		String detailMessage = elog.getDetailMessage()//
				.replace("\r\n", "<br>")//
				.replace("\t", "<span style='width: 30px;'></span>");
		request.setAttribute("detailMessage", detailMessage);
		request.setAttribute("exceptionLog", elog);
	}
}
