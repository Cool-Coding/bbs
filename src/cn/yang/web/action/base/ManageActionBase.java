package cn.yang.web.action.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.yang.domain.OperationLog;
import cn.yang.service.SystemLogService;
import cn.yang.web.util.MemberAuthenticationUtils;



@SuppressWarnings("unchecked")
public class ManageActionBase extends ActionBase {

	private static List<Pattern> excludeMethodNamePatternList = new ArrayList<Pattern>();

	@Resource(name="systemLogServiceImpl")
	private SystemLogService systemLogService;
	static {
		List<String> excludeMethodNamePatternStringList = new ArrayList<String>();
		excludeMethodNamePatternStringList.add("list");
		excludeMethodNamePatternStringList.add(".*UI");

		for (String patternString : excludeMethodNamePatternStringList) {
			excludeMethodNamePatternList.add(Pattern.compile(patternString));
		}
	}

	// FIXME 记录日志的功能放到一个专门的过滤器中是否更好一些？
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 记录管理痕迹
		String methodName = getMethodName(mapping, form, request, response,//
				getParameter(mapping, form, request, response));
		
		boolean logTrace = true;
		if (methodName == null) {
			logTrace = false;
		} else {
			for (Pattern pattern : excludeMethodNamePatternList) { // 是否记录
				Matcher m = pattern.matcher(methodName);
				if (m.matches()) { // 如果匹配，就不记录
					logTrace = false;
					break;
				}
			}
		}

		if (logTrace) {// 记录访问的Action与方法名
			StringBuffer sbf = new StringBuffer()//
					.append("ActionClass=").append(this.getClass().getName()).append(", ")//
					.append("methodName=").append(methodName).append(".\n")//
					.append("parameters={");

			// 保存所有的请求参数
			// FIXME 如果是 multipart/form-data 格式，则取不到表单内容。目前也不需要保存这样的请求（后台没有上传文件）。
			Map parameters = request.getParameterMap();
			for (Object name : parameters.keySet()) {
				Object[] value = (Object[]) parameters.get(name);
				sbf.append(String.valueOf(name)).append("=").append(Arrays.toString(value)).append(", ");
			}
			String comment = sbf.append("}").toString();

			// save log info
    	   OperationLog olog = new OperationLog();
			olog.setType("系统管理操作信息");
			olog.setComment(comment);
			olog.setLogTime(new Date());
			olog.setOperator(MemberAuthenticationUtils.getLoggedOnUser(request));
			olog.setOperIpAddr(request.getRemoteAddr());
			systemLogService.save(olog); // Save log
	}

		return super.execute(mapping, form, request, response);
	}
}
