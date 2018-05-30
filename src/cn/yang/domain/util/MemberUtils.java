package cn.yang.domain.util;

import cn.yang.domain.User;


public class MemberUtils {

	// FIXME 还使用 anonymous 吗？
	// /**
	// * 用户名为anonymous的为匿名用户
	// *
	// * @param user
	// * @return 是否是匿名用户
	// */
	// public static boolean isAnonymousUser(User user) {
	// return "anonymous".equals(user.getLoginName());
	// }

	/**
	 * 用户名为 superman 的为超级管理员
	 * 
	 * @param user
	 * @return 是否为超级管理员用户
	 */
	public static boolean isSuperman(User user) {
		return "superman".equals(user.getLoginName());
	}
}
