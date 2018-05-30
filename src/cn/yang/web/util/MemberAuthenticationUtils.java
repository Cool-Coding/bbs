package cn.yang.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.yang.constant.WebConstants;
import cn.yang.domain.User;


/**
 * 会员认证用的工具类。和{@link ReturnPathUtils}工具类配使用使用
 * 
 * @author tyg
 * 
 */
public class MemberAuthenticationUtils {

	/**
	 * 检测是否已登录用户
	 * 
	 * @param request
	 * @return 是否已登录用户
	 */
	public static boolean isLoggedOn(HttpServletRequest request) {
		Object user = request.getSession().getAttribute(WebConstants.SESSION_LOGGED_ON_USER);
		return (user != null); // 不为空，代表已登录用户
	}

	/**
	 * 获取当前的登录用户
	 * 
	 * @param request
	 * @return
	 */
	public static User getLoggedOnUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(WebConstants.SESSION_LOGGED_ON_USER);
	}

	/**
	 * 在当前Session中登录用户
	 * 
	 * @param request
	 * @param user
	 */
	public static void login(HttpServletRequest request, User user) {
		// get session
		HttpSession session = request.getSession();
		// 1, 设置user到session作用域
		session.setAttribute(WebConstants.SESSION_LOGGED_ON_USER, user);
		// 2, FIXME 替换掉原先在HttpSessionManager中的session
		// OnlineUsersManager.getInstance().register(session, user);
	}

	/**
	 * 注销，仅移除当前Session中的登录信息
	 * 
	 * @param request
	 */
	public static void logout(HttpServletRequest request) {
		request.getSession().removeAttribute(WebConstants.SESSION_LOGGED_ON_USER);
	}


}
