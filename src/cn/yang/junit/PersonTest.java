package cn.yang.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yang.domain.User;
import cn.yang.service.ReplyService;
import cn.yang.service.UserService;
import cn.yang.util.PutImg;
import cn.yang.util.GetImg;


public class PersonTest {
	
	private static ApplicationContext factory;
	private static UserService userService;
	private static EntityManagerFactory emfactory;
	private static EntityManager em;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory=new ClassPathXmlApplicationContext("beans.xml");
		userService=(UserService) factory.getBean("userServiceImpl");
		emfactory=(EntityManagerFactory)factory.getBean("entityManagerFactory");
		em=emfactory.createEntityManager();
	}
	
	@Test
	public void save() throws Exception{
/*        File image=new File("f:\\0.jpg");
       InputStream imageInput=new FileInputStream(image);
       byte[] bytes=new byte[(int) image.length()];
        imageInput.read(bytes);
	    imageInput.close();
        String imagestr=new String(bytes);
        imagestr.replaceAll("/?/", " u003F");
        bytes=imagestr.getBytes();
        
	    User user=new User();
	    user.setLoginName("yang");
	    user.setPassword("123");
	    user.setNickname("yang");
	    user.setAvatar(bytes);
	    user.setEmail("abc");
	    em.getTransaction().begin();
	    em.persist(user);
	    em.getTransaction().commit();*/
	    //new PutImg().putimg();
	    new GetImg().blobRead("f:\\1.JPG", 2);
       //userService.save(user);
	}
}
