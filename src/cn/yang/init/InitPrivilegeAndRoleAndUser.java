package cn.yang.init;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yang.domain.Role;
import cn.yang.domain.SystemPrivilege;
import cn.yang.domain.User;
import cn.yang.service.SystemPrivilegeService;
import cn.yang.service.ThemeService;


public class InitPrivilegeAndRoleAndUser {

	private static EntityManagerFactory factory;
	private EntityManager em;
	
	private SystemPrivilegeService systemPrivilegeService;
	
	@Test
	public void init() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/beans.xml");
		factory=(EntityManagerFactory) applicationContext.getBean("entityManagerFactory");
		systemPrivilegeService=(SystemPrivilegeService) applicationContext.getBean("systemPrivilegeServiceImpl");
        em=factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		// 删除所有信息
		// session.createQuery("DELETE FROM User").executeUpdate();
		// session.createQuery("DELETE FROM SystemPrivilege").executeUpdate();
		// session.createQuery("DELETE FROM Role").executeUpdate();

		// 添加权限数据
		em.persist(new SystemPrivilege("发表主题", "Theme", "Create"));
		em.persist(new SystemPrivilege("查看主题", "Theme", "Retrieval"));
		em.persist(new SystemPrivilege("修改主题", "Theme", "Update"));
		em.persist(new SystemPrivilege("删除主题", "Theme", "Delete"));
		em.persist(new SystemPrivilege("移动主题", "Theme", "Move"));

		em.persist(new SystemPrivilege("发表回复", "Reply", "Create"));
		em.persist(new SystemPrivilege("查看回复", "Reply", "Retrieval"));
		em.persist(new SystemPrivilege("修改回复", "Reply", "Update"));
		em.persist(new SystemPrivilege("删除回复", "Reply", "Delete"));

		em.persist(new SystemPrivilege("发表附件", "Attachment", "Create"));
		em.persist(new SystemPrivilege("查看附件", "Attachment", "Retrieval"));
		em.persist(new SystemPrivilege("更新附件", "Attachment", "Update"));
		em.persist(new SystemPrivilege("删除附件", "Attachment", "Delete"));
		em.persist(new SystemPrivilege("下载附件", "Attachment", "Download"));

/*		em.persist(new SystemPrivilege("发表投票", "Poll", "Create"));
		em.persist(new SystemPrivilege("查看投票", "Poll", "Retrieval"));
		em.persist(new SystemPrivilege("修改投票", "Poll", "Update"));
		em.persist(new SystemPrivilege("删除投票", "Poll", "Delete"));
		em.persist(new SystemPrivilege("参与投票", "Poll", "Vote"));*/

		em.persist(new SystemPrivilege("管理系统", "System", "Manage"));
		
		tx.commit();
		
		tx.begin();
		// 添加角色数据
		Role adminRole = new Role("管理员"); // 管理员有所有的权限
		adminRole.getSystemPrivileges().addAll(systemPrivilegeService.getScrollData().getResultlist());
		em.persist(adminRole);

		Role commonRole = new Role("普通会员"); // 普通会员，由管理员在自行设置权限
		commonRole.setDefaultForNewUser(true);
		em.persist(commonRole);
		
		// 添加管理员用户
		User user = new User();
		user.setLoginName("superman");
		user.setPassword(DigestUtils.md5Hex("it"));
		user.setRegistrationTime(new Date());
		user.setNickname("超级管理员");
		user.getRoles().add(adminRole);
		em.persist(user);

		tx.commit();
		em.close();
	}
	
	@Test
	public void addSuperman(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/beans.xml");
		factory=(EntityManagerFactory) applicationContext.getBean("entityManagerFactory");
		systemPrivilegeService=(SystemPrivilegeService) applicationContext.getBean("systemPrivilegeServiceImpl");
        em=factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		// 添加角色数据
		Role adminRole = new Role(1); // 管理员有所有的权限
/*		adminRole.getSystemPrivileges().addAll(systemPrivilegeService.getScrollData().getResultlist());
		em.persist(adminRole);*/

		/*	Role commonRole = new Role("普通会员"); // 普通会员，由管理员在自行设置权限
    	commonRole.setDefaultForNewUser(true);
		em.persist(commonRole);*/
		
		// 添加管理员用户
		User user = new User();
		user.setLoginName("superman");
		user.setPassword(DigestUtils.md5Hex("it"));
		user.setRegistrationTime(new Date());
		user.setNickname("超级管理员");
		user.getRoles().add(adminRole);
		em.persist(user);

		tx.commit();
		em.close();
	}
}
