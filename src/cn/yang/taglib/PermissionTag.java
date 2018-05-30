package cn.yang.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.yang.domain.User;
import cn.yang.util.PrivilegeCheckUtils;
import cn.yang.web.util.MemberAuthenticationUtils;


@SuppressWarnings("serial")
public class PermissionTag extends TagSupport {
	private String resource;
	private String action;

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		User user = MemberAuthenticationUtils.getLoggedOnUser(request);
		if (user == null) { // 未登录
			return SKIP_BODY;
		}

		// 如果有权限，就执行标签体
		if (PrivilegeCheckUtils.checkPrivilege(user, resource, action)) {
			return EVAL_BODY_INCLUDE;
		}
		// 没有权限，就跳过
		else {
			return SKIP_BODY;
		}
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
