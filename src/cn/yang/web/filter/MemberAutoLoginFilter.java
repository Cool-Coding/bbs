package cn.yang.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.yang.service.UserService;
import cn.yang.web.util.MemberAuthenticationUtils;
import cn.yang.web.util.MemberAutoLoginUtils;


/**
 * 处理自动登录的过滤器
 * 
 */
public class MemberAutoLoginFilter implements Filter {

	// 使用@Resource不可以，因为Filter不是IOC管理。
	private UserService userService;

	// 初始化 userService
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext sc = filterConfig.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		userService = (UserService) ac.getBean("userServiceImpl");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		// 先尝试自动登录用户
		HttpServletRequest req = (HttpServletRequest) request;
		if (!MemberAuthenticationUtils.isLoggedOn(req)) { // 只检测未登录会员的请求
			MemberAutoLoginUtils.tryAutoLogin(req, userService);
		}

		// 再处理请求
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
