package cn.yang.constant;

/**
 * Web作用域变量存储时用到的常量。格式为：{标识}_{其他名称部分}<br>
 * 标识可以为以下值：<br>
 * PAGE、REQUEST、SESSION、APPLICATION 代表是相应的作用域的变量的KEY<br>
 * PARAMETER 代表是请求参数名称<br>
 * COOKIE 代表是Cookie的名称<br>
 * 
 * @author tyg
 */
public class WebConstants {

	/** 主题类型列表 */
	public static final String APPLICATION_TOPIC_TYPE_LIST = "APPLICATION_TOPIC_TYPE_LIST";

	/** 当前登录用户 */
	public static final String SESSION_LOGGED_ON_USER = "SESSION_LOGGED_ON_USER";

	/** 登录成功后返回的路径 */
	public static final String PARAMETER_RETURN_PATH = "PARAMETER_RETURN_PATH";

	/** 自动登录的Cookie的名称 */
	public static final String COOKIE_NAME_AUTO_LOGIN = "COOKIE_AUTO_LOGIN_ITCASTBBS";

	// FIXME 以下要统一。
	/** 登录成功后返回的路径（未编码的） */
	public static final String REQUEST_UNENCODED_RETURN_PATH = "REQUEST_UNENCODED_RETURN_PATH";
	
	/** 登录成功后返回的路径（已编码的） */
	public static final String REQUEST_ENCODED_RETURN_PATH = "REQUEST_ENCODED_RETURN_PATH";

	public static final String REQUEST_HAS_EXCEPTIONACTION = "REQUEST_HAS_EXCEPTIONACTION";
	
    /** 会员登录的地址 */
	public static final String LOGIN_USER_URL = "/user.do?method=loginUI";
}
