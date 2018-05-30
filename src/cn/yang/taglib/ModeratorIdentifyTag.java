package cn.yang.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.yang.domain.Forum;
import cn.yang.domain.User;
import cn.yang.util.ModeratorIdentifyUtil;
import cn.yang.util.PrivilegeCheckUtils;
import cn.yang.web.util.MemberAuthenticationUtils;

public class ModeratorIdentifyTag extends TagSupport {

	private Forum forum;
	
	@Override
	public int doStartTag() throws JspException {
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		User user = MemberAuthenticationUtils.getLoggedOnUser(request);
		if (user == null) { // 未登录
			return SKIP_BODY;
		}
		// 如果是当前版主或超级管理员，则执行标签体
		if (ModeratorIdentifyUtil.checkModeratorForForum(user, forum)) {
			return EVAL_BODY_INCLUDE;
		}
		// 没有权限，就跳过
		else {
			return SKIP_BODY;
		}
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}
}
