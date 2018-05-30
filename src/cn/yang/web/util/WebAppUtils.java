package cn.yang.web.util;

import javax.servlet.http.HttpServletRequest;


public class WebAppUtils {

	/**
	 * @param request
	 * @return 应用程序的访问地址，如 http://localhost:8080/ItcastBBS/
	 */
	public static String getBaseAccessUrl(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		return basePath;
	}

	/**
	 * @param request
	 * @return 应用程序的访问地址（不含端口号，因为发布后要使用80端口，即不用指定），如 http://localhost/ItcastBBS/
	 */
	public static String getBaseAccessUrlWithoutPort(HttpServletRequest request) {
		String path = request.getContextPath();
		// FIXME 把应用放到ROOT下时，path是否为'/'?!
		// 如果是，就应如下：
		if ("/".equals(path)) {
			path = ""; // 就忽略他
		}
		// FIXME 不加path了，是同一网站就行。   
		String basePath = request.getScheme() + "://" + request.getServerName(); // + path + "/";
		return basePath;
	}

	/**
	 * @param request
	 * @return 应用程序的真实路径地址，如 d:\webapps\ItcastBBS\ 或 /var/www/ItcastBBS
	 */
	public static String getBaseRealPath(HttpServletRequest request) {
		// 1，如果传递""或"/"，得到的路径包含最后的'/'。
		// 2，如果传递一个子路径"/aa"或"/aa/"或"aa"或"aa/"。得到的为"/www/ItcastBBS/aa"，得到的路径没有最后的'/'。
		String realPath = request.getSession().getServletContext().getRealPath("/");
		return realPath;
	}
}
