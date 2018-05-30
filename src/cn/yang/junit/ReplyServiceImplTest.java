package cn.yang.junit;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yang.service.ForumService;
import cn.yang.service.ReplyService;

public class ReplyServiceImplTest {

	private static ApplicationContext factory;
	private static ReplyService replyService;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory=new ClassPathXmlApplicationContext("beans.xml");
		replyService=(ReplyService) factory.getBean("replyServiceImpl");
	}

	@Test
	public void testOnlyLogin() {
		replyService.onlyLogin(new Integer[]{3,4},true);
    }

	@Test
	public void testSeenByFront() {
       replyService.seenByFront(new Integer[]{3,4},false);
	}

}
