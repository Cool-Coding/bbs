package cn.yang.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.yang.constant.ControlCenterConstants;
import cn.yang.constant.WebConstants;
import cn.yang.dao.DAOSupport;
import cn.yang.domain.Role;
import cn.yang.domain.User;
import cn.yang.domain.util.MemberUtils;
import cn.yang.service.RoleService;
import cn.yang.service.UserService;
import cn.yang.util.PropertiesUtil;


/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 *2011-4-10
 * 
 */
@Service
@Transactional
public class UserServiceImpl extends DAOSupport<User> implements UserService {

	@Resource(name="roleServiceImpl")
	private RoleService roleService;
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public boolean checkLoginName(String loginName) {
		// 去掉用户名两端的空格并转为小写
		loginName = loginName.trim().toLowerCase();
		return find(loginName)== null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public boolean checkNickname(String nickname) {
		// 去掉用户名两端的空格并转为小写
		nickname = nickname.trim().toLowerCase();
		String jqpl="select o from User o where o.nickname=?";
		Query query=em.createQuery(jqpl);
		query.setParameter(1, nickname);
		try{
		Object obj=query.getSingleResult();
		}catch(NoResultException e){
			return true;
		}catch(Exception ex){
			throw  new RuntimeException(ex.getMessage());
		}
		return false;
	}
   
	
 @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public User findByLoginNameAndPassword(String loginName, String password) {
		// 去掉用户名两端的空格并转为小写
		loginName = loginName.trim().toLowerCase();
		// 使用明文密码的MD5摘要
		password = DigestUtils.md5Hex(password);
		String jqpl="select o from User o where o.loginName=?1 and o.password=?2";
		Query query=em.createQuery(jqpl);
		query.setParameter(1, loginName);
		query.setParameter(2, password);
		
		User user=null;
		try{
			user=(User) query.getSingleResult();
			}catch(NoResultException e){
			}catch(Exception ex){
				throw  new RuntimeException(ex.getMessage());
			}
			return user;
	}

	@Override
public void save(User user) {
		if (user.getPassword() == null) {
			throw new RuntimeException("用户的密码不能为 null");
		}

		// 去掉用户名两端的空格并转为小写
		String loginName = user.getLoginName().trim().toLowerCase();

		// 设置基本信息
		user.setLoginName(loginName); // 保存为小写的用户名
		user.setPassword(DigestUtils.md5Hex(user.getPassword())); // 使用md5摘要作为密码
		user.setLocked(false); // 新注册即可使用
		user.setRegistrationTime(new Date());
        user.setRank("新手菜鸟");
		// 新注册用户有默认的角色
		List<Role> roles=roleService.getDefaultRoles();
		Set<Role> setRoles=new HashSet<Role>();
		setRoles.addAll(roles);
		user.setRoles(setRoles);
		super.save(user);
	}

	public User changePassword(String loginName, String newPassword) {
		User user = (User) find(loginName);
		user.setPassword(DigestUtils.md5Hex(newPassword)); // 使用md5摘要作为密码
		update(user);;
		return user;
	}

	public void lock(String loginName) {
		User user = (User) find(loginName);
		if (!MemberUtils.isSuperman(user)) { // 不能锁定超级管理员
/*			user.setLocked(true);
			this.update(user);*/
			
			String jqpl="update User o set o.locked=true  where o.loginName=?1";
			Query query=em.createQuery(jqpl);
			query.setParameter(1, loginName);
			query.executeUpdate();
		}
	}

	public void unlock(String loginName) {
		/*	User user = (User) find(loginName);
    	user.setLocked(false);
		this.update(user);*/
		String jqpl="update User o set o.locked=false  where o.loginName=?1";
		Query query=em.createQuery(jqpl);
		query.setParameter(1, loginName);
		query.executeUpdate();
	}

	@Override
	public void addScoreAndThemeCount(User user, HttpServletRequest request) {
		//更新用户信息
		//得到增加的分数
/*	   Integer score=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.FRONT_SCORE_PER_THEME));
		user.setScore(user.getScore()+score);
		user.setThemeCount(user.getThemeCount()+1);
		HttpSession session = request.getSession();
		session.setAttribute(WebConstants.SESSION_LOGGED_ON_USER, user);*/
		String jqpl="update User o set o.themeCount=o.themeCount+1  where o.loginName=?1";
		Query query=em.createQuery(jqpl);
		query.setParameter(1, user.getLoginName());
		query.executeUpdate();
	}

	@Override
	public void addScoreAndReplyCount(User user, HttpServletRequest request) {
		//更新用户信息
/*	   Integer score=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.FRONT_SCORE_PER_REPLY));
	    user.setScore(user.getScore()+score);
		user.setReplyCount(user.getReplyCount()+1);
		HttpSession session = request.getSession();
		session.setAttribute(WebConstants.SESSION_LOGGED_ON_USER, user);*/
		
		String jqpl="update User o set o.themeCount=o.themeCount+1  where o.loginName=?1";
		Query query=em.createQuery(jqpl);
		query.setParameter(1, user.getLoginName());
		query.executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByLoginNames(String... loginNames) {
		
		StringBuffer idRange=new StringBuffer("");
		for(Serializable loginName:loginNames){
			idRange.append("'").append(loginName).append("'").append(",");
		}
		idRange.deleteCharAt(idRange.length()-1);
		
       	String jpql="Select  o from User o where o.loginName  in("+idRange.toString()+")";
       	Query query=em.createQuery(jpql);
       	return query.getResultList();
	}

	@Override
	public boolean checkEmail(String email) {
		// 去掉邮箱两端的空格并转为小写
		email = email.trim().toLowerCase();
		String jqpl="select o from User o where o.email=?";
		Query query=em.createQuery(jqpl);
		query.setParameter(1, email);
		try{
		Object obj=query.getSingleResult();
		}catch(NoResultException e){
			return true;
		}catch(Exception ex){
			throw  new RuntimeException(ex.getMessage());
		}
		return false;
	}

	@Override
	//FIXME 若锁定/解锁用户时调用此方法就会出现错误，好像它也更新了图片?
	public void update(User entity) {
		String jpql="update User o set o.password=?1,o.email=?2,o.nickname=?3,o.gender=?4,o.signature=?5,o.score=?6,o.rank=?7,o.lastVisitTime=?8,o.lastVisitIpAddr=?9" +
				" where o.loginName=?10";
		Query query=em.createQuery(jpql);
		query.setParameter(1, entity.getPassword());
		query.setParameter(2, entity.getEmail());
		query.setParameter(3, entity.getNickname());
		query.setParameter(4, entity.getGender());
		query.setParameter(5, entity.getSignature());
		query.setParameter(6, entity.getScore());
		query.setParameter(7, entity.getRank());
		query.setParameter(8, entity.getLastVisitTime());
		query.setParameter(9, entity.getLastVisitIpAddr());
		query.setParameter(10, entity.getLoginName());
		
		query.executeUpdate();
	}

	@Override
	public int allUsers() {
       	String jpql="Select  count(o) from User o";
       	Query query=em.createQuery(jpql);
       	int count=0;
       	try{
       	count=(Integer)query.getSingleResult();
       	}catch(Exception e){
       		throw new RuntimeException(e.getMessage());
       	}
		return count;
	}
}
