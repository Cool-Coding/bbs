package cn.yang.web.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
	/**
	 * 获取布尔型参数值，传递的参数值为 true 时，返回 true，其它情况返回 false
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static boolean getBoolParam(HttpServletRequest request, String name) {
		return "true".equals(request.getParameter(name));
	}

	/**
	 * 获取 Integer 型参数值. 如果没有传递, 则返回 defaultValue
	 * 
	 * @param request
	 * @param name
	 * @param defaultValue 默认值
	 * @return
	 */
	public static Integer getIntegerParam(HttpServletRequest request, String name, Integer defaultValue) {
		try {
			return Integer.parseInt(request.getParameter(name));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
