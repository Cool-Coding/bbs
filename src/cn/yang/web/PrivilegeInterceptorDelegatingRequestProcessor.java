package cn.yang.web;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.web.struts.DelegatingRequestProcessor;

import cn.yang.annotation.Permission;
import cn.yang.bbs.exception.ActionException;
import cn.yang.bbs.exception.PermissionDeniedException;
import cn.yang.constant.WebConstants;
import cn.yang.domain.User;
import cn.yang.util.PrivilegeCheckUtils;
import cn.yang.web.util.MemberAuthenticationUtils;
import cn.yang.web.util.ReturnPathUtils;

/**
 * FIXME 使用缓存方法提高效率
 */
public class PrivilegeInterceptorDelegatingRequestProcessor extends DelegatingRequestProcessor {

	private static final Class<?>[] parameterTypes = { ActionMapping.class, //
			ActionForm.class, //
			HttpServletRequest.class, //
			HttpServletResponse.class };

	@Override
	protected ActionForward processActionPerform(HttpServletRequest request, HttpServletResponse response, Action action,
			ActionForm form, ActionMapping mapping) throws IOException, ServletException {
		try {
			// 1，获取要调用的方法
			String methodName = "execute";
			if (DispatchAction.class.isAssignableFrom(action.getClass())) {
				methodName = request.getParameter(mapping.getParameter());
				if (methodName == null) {
					methodName = "unspecified";
				}
			}

			Method method = null;
			try {
				method = action.getClass().getDeclaredMethod(methodName, parameterTypes);
			} catch (Exception e) {
				throw new ActionException(e);
			}

			// 2，得到这个方法所要求的权限
			Permission permission = method.getAnnotation(Permission.class);
			if (permission == null) {
				permission = action.getClass().getAnnotation(Permission.class);
			}

			if (permission != null) {
				// 3，查看用户是否有这个权限
				User currentUser = MemberAuthenticationUtils.getLoggedOnUser(request);
				if (!PrivilegeCheckUtils.checkPrivilege(currentUser, permission.resource(), permission.action())) {
					// 4，如果没有权限，抛出异常
					// 使用当前地址为returnPath
					String returnPath= ReturnPathUtils.makeReturnPathDefaultUseCurrentRequestURI(request, null);
					request.setAttribute(WebConstants.REQUEST_ENCODED_RETURN_PATH, returnPath);
					// 抛异常
					String msg = "您没有权限【resource=" + permission.resource() + ",action=" + permission.action() //
							+ "】访问方法【" + action.getClass() + "." + methodName + "】";
					throw new PermissionDeniedException(msg);
				}
			}

			return super.processActionPerform(request, response, action, form, mapping);
		} catch (Exception e) {
			// 出现异常后，由Struts处理。
			return (processException(request, response, e, form, mapping));
		}
	}
}
