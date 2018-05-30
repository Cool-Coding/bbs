package cn.yang.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.yang.domain.Gender;
import cn.yang.util.GenericEnumConverter;
import cn.yang.util.UtilDateConverter;


@SuppressWarnings("serial")
public class InitSystemServlet extends GenericServlet {
	private Log log = LogFactory.getLog(InitSystemServlet.class);

	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		// 注册转换器
		ConvertUtils.register(new UtilDateConverter(), java.util.Date.class);
		ConvertUtils.register(new GenericEnumConverter(Gender.class), Gender.class);
		log.info("已注册转换器：UtilDateConverter 与 new GenericEnumConverter(Gender.class)");


/*		// 初始化CompassUtils
		String applicationRealPath = getServletContext().getRealPath("/"); // 返回的目录将以'/'结尾
		CompassUtils.init(applicationRealPath);*/

	/*	// FIXME 准备application中的数据。放到一个专门的类中?
		List topicTypeList = DataDict.getList(DDConstants.TOPIC_TYPE);
		getServletContext().setAttribute(WebConstants.APPLICATION_TOPIC_TYPE_LIST, topicTypeList);*/
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}

}
