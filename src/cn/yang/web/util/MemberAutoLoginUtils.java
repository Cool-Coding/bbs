package cn.yang.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.yang.constant.WebConstants;
import cn.yang.domain.User;
import cn.yang.service.UserService;

/**
 * 使用Cookie实现。Cookie的格式为：<br>
 * 1， 名称：ItcastBBS_AutoLogin_Cookie<br>
 * 2， 值：{userId}_{失效日期的毫秒表示}<br>
 * Cookie的值就是AutoLoginAuthKey，也需要在数据库中存储一份。
 * 
 * @author tyg
 */
public class MemberAutoLoginUtils {
	private static Log log = LogFactory.getLog(MemberAutoLoginUtils.class);

	/**
	 * 向浏览器发送自动登录的Cookie，并把Cookie的值（AutoLoginAuthKey）更新到用户实体中。<br>
	 * 需要在外面update一下User对象。
	 * 
	 * @param user
	 * @param autoLoginDays 自动登录的时间，单位：天。
	 * @param response
	 */
	public static void addAutoLoginCookieAndSetUserAutoLoginAuthKey(User user, int autoLoginDays, HttpServletResponse response) {
		// 有效天数 --> 过期时间 --> autoLoginAuthKey
		// 使用long，要不就很有可能会溢出
		long expirationTime = autoLoginDays * (long) 24 * 3600 * 1000 + System.currentTimeMillis();
		String autoLoginAuthKey = user.getLoginName()+ "_" + expirationTime;

		// 1，保存到客户端(发送Cookie)
		String cookieName = WebConstants.COOKIE_NAME_AUTO_LOGIN;
		String cookieValue = autoLoginAuthKey;
		Integer cookieAge = (int) autoLoginDays * 24 * 3600;
		// TODO 默认的path应是ContextPath
		String cookiePath = "/";
		// add cookie
		CookieUtils.addCookie(response, cookieName, cookieValue, cookieAge, cookiePath);

		// 2，更新到用户实体中（需要update一下）
		user.setAutoLoginAuthKey(autoLoginAuthKey);
	}

	/**
	 * 检查自动登录，如果符合自动登录的条件，就登录用户。
	 * 
	 * @param request
	 * @param userService
	 */
	public static void tryAutoLogin(HttpServletRequest request, UserService userService) {
		// // 情况1：已登录用户，返回
		// if (MemberAuthenticationUtils.isLoggedOn(request)) {
		// return;
		// }

		// 情况2：如是没有自动登录的Cookie，返回
		Cookie autoLoginCookie = CookieUtils.getCookie(request, WebConstants.COOKIE_NAME_AUTO_LOGIN);
		if (autoLoginCookie == null) {
			return;
		}

		// 情况3：检测自动登录。如果通过，登录用户。
		try {
			// 得到userId与过期时间信息
			String[] valueTokens = autoLoginCookie.getValue().split("_");
			String userName = valueTokens[0];
			long expirationTime = Long.parseLong(valueTokens[1]);
			// 获取用户
			User user = userService.find(userName);;

			// 检测
			if (!user.isLocked() && autoLoginCookie.getValue().equals(user.getAutoLoginAuthKey()) // 1，Cookie的value要和数据库中的值一致
					&& System.currentTimeMillis() < expirationTime) { // 并且未过期
				MemberAuthenticationUtils.login(request, user);
			}
		} catch (RuntimeException e) {
			log.warn("自动登录失败，【cookie.value=" + autoLoginCookie.getValue() + "】，相关异常信息为：" + e.getMessage());
		}
	}

	/**
	 * 通知浏览器删除自动登录的Cookie，并把用户实体的AutoLoginAuthKey清除。<br>
	 * 需要在外面update一下User对象。
	 * 
	 * @param response
	 * @param user
	 */
	public static void delAutoLoginCookieAndClearUserAutoLoginAuthKey(HttpServletResponse response, User user) {
		// 1，保存到客户端(发送Cookie)
		String cookieName = WebConstants.COOKIE_NAME_AUTO_LOGIN;
		String cookieValue = "";
		Integer cookieAge = 0;
		String cookiePath = "/";
		// add cookie
		CookieUtils.addCookie(response, cookieName, cookieValue, cookieAge, cookiePath);

		// 2，更新到用户实体中（需要update一下）
		// 不设置也可以
		// user.setAutoLoginAuthKey(null);
	}
}
