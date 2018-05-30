package cn.yang.web.util;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import cn.yang.constant.ControlCenterConstants;
import cn.yang.constant.WebConstants;

/**
 * 说明：都是使用的默认编码。如调用的方法为：getBytes()，newString(String)
 */
public class ReturnPathUtils {

	/**
	 * 重定向到上一次访问的（注册或登录之前的）页面
	 * 
	 * @param request
	 * @param response
	 * @return 是否已重定向到之前页面（如果没有地址，就返回false）
	 */
	public static boolean rediectPreviousPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return rediectPreviousPage(request, response, null);
	}

	public static boolean rediectPreviousPage(HttpServletRequest request, HttpServletResponse response, String returnPath)
			throws IOException {
		returnPath = ReturnPathUtils.getAndDecodeReturnPath(request, returnPath);
		if (StringUtils.isBlank(returnPath)) {
			return false;
		}
		// FIXME 如果是访问的loginUI,login,logout，就不用跳转了
		if (returnPath.indexOf("/user.do?method=log") > 0) {
			return false;
		}

		response.sendRedirect(returnPath);
		return true;
	}

	/**
	 * 重定向到登陆页面
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void toLoginWithReturnPathDefaultIsCurrentRequestURI(HttpServletRequest request, HttpServletResponse response,
			String unencodedReturnPath) throws IOException {
		// returnPath
		String returnPath = makeReturnPathDefaultUseCurrentRequestURI(request, unencodedReturnPath);

		// LOGIN_USER_URL应是/user.do?method=loginUI
		String loginUserUrl =WebConstants.LOGIN_USER_URL;
		response.sendRedirect(request.getContextPath() + loginUserUrl //
				+ "&src=login&" + WebConstants.PARAMETER_RETURN_PATH + "=" + returnPath);
	}

	/**
	 * 重定向到登陆页面
	 * 
	 * @param request
	 * @param response
	 * @param returnPath
	 * @throws IOException
	 */
	public static void toLoginWithReturnPath(HttpServletRequest request, HttpServletResponse response, String returnPath)
			throws IOException {
		// LOGIN_USER_URL应是/user.do?method=loginUI
		String loginUserUrl = WebConstants.LOGIN_USER_URL;
		response.sendRedirect(request.getContextPath() + loginUserUrl //
				+ "&src=login&" + WebConstants.PARAMETER_RETURN_PATH + "=" + returnPath);
	}

	/**
	 * 从request的参数中取出登陆成功后要返回的地址，正确解码后返回<br>
	 * 使用 Base64 编码
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getAndDecodeReturnPath(HttpServletRequest request, String returnPath) {
		if (returnPath == null) {
			returnPath = request.getParameter(WebConstants.PARAMETER_RETURN_PATH);
		}
		if (StringUtils.isBlank(returnPath)) {
			return null;
		}
		byte[] buf = Base64.decodeBase64(returnPath.getBytes());
		String decodedPath = new String(buf);
		return decodedPath;
	}

	/**
	 * 准备登陆成功后要返回的地址 returnPath。<br>
	 * 如果传递的参数 unencodedReturnPath 为 null，则使用本次请求的地址。<br>
	 * 地址使用 Base64 编码。
	 * 
	 * @param request
	 * @param unencodedReturnPath 要进行处理的地址（未经编码），如果为 null，则使用本次请求的地址
	 * @return 把 returnPath 编码后的结果
	 */
	@SuppressWarnings("unchecked")
	public static String makeReturnPathDefaultUseCurrentRequestURI(HttpServletRequest request, String unencodedReturnPath) {
		if (unencodedReturnPath == null) {
			unencodedReturnPath = request.getRequestURL().toString();
			Map<String, String[]> params = request.getParameterMap();
			boolean isFirstFlag = true;
			for (Entry<String, String[]> e : params.entrySet()) {
				for (String value : e.getValue()) {
					if (isFirstFlag) {
						unencodedReturnPath += "?" + e.getKey() + "=" + value;
						isFirstFlag = false;
					} else {
						unencodedReturnPath += "&" + e.getKey() + "=" + value;
					}
				}
			}
		}
		return new String(Base64.encodeBase64(unencodedReturnPath.getBytes()));
	}

	/**
	 * 准备登陆成功后要返回的地址 returnPath。<br>
	 * 如果请求中没有传递表示returnPath参数，则使用 referer 指定的地址。<br>
	 * 1，地址使用 Base64 编码。<br>
	 * 2，在浏览器中直接输入地址访问时，就没有referer信息。
	 * 
	 * @param request
	 * @return 把 returnPathParamName 编码后的结果
	 */
	public static String getReturnPathDefaultUseRefererHeaderValue(HttpServletRequest request) {
		// 默认使用传递的returnPath值
		String returnPath = request.getParameter(WebConstants.PARAMETER_RETURN_PATH);
		if (StringUtils.isNotBlank(returnPath)) {
			return returnPath;
		}

		// 如果没有指定returnPath, 则使用referer指定的地址
		// 注：referer中包含参数
		String referer = request.getHeader("Referer");
		// referer不为null，且referer中的地址要是本站的中的
		// FIXME 在部署后，地址为http://bbs.itcast.cn，使用80端口（可以不写），所以BaseUrl中应没有端口号
		// FIXME 这样也不可以。问题是show_top中注销后，回到首页了，应再回到show_top。
		if (StringUtils.isBlank(referer) || !referer.startsWith(WebAppUtils.getBaseAccessUrlWithoutPort(request))) {
			return null;
		}
   
		return new String(Base64.encodeBase64(referer.getBytes()));
	}
}
