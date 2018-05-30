package cn.yang.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.yang.dao.DAO;
import cn.yang.domain.User;


/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 *2011-4-10
 * 
 */
public interface UserService extends DAO<User> {

	/**
	 * 检测登录名是否可用。<br>
	 * 用户名不区分大小写, 用户名ABC和abc是相同的
	 * 
	 * @param loginName
	 * @return 指定的用户名是否可以使用（未注册）
	 */
	boolean checkLoginName(String loginName);

	/**
	 * 检测昵称是否可用。<br>
	 * 不区分大小写, ABC和abc是相同的 *
	 * 
	 * @param nickname
	 * @return 指定的用户名是否可以使用（未注册）
	 */
	boolean checkNickname(String nickname);

	/**
	 * 检测邮箱是否可用。<br>
	 * 不区分大小写, ABC和abc是相同的 *
	 * 
	 * @param email
	 * @return 指定的用户名是否可以使用（未注册）
	 */
	boolean checkEmail(String email);
	/**
	 * 使用用户名和密码查询一个唯一的用户<br>
	 * 用户名不区分大小写, 用户名ABC和abc是相同的
	 * 
	 * @param loginName
	 * @param password 明文密码
	 * @return
	 */
	User findByLoginNameAndPassword(String loginName, String password);

	/**
	 * 锁定指定用户<br>
	 * 不会锁定超级管理员用户, 也不会锁定anonymous用户
	 * 
	 * @param loginName
	 */
	void lock(String loginName);

	/**
	 * 解锁指定用户
	 * 
	 * @param loginName
	 */
	void unlock(String loginName);

	/**
	 * 修改密码
	 * @param loginName
	 * @param newPassword
	 * @return 修改后的User
	 */
	User changePassword(String loginName, String newPassword);

	/**
	 * 会员每发表一个帖子增加相应的分数和发帖数
	 * @param user
	 * @param request
	 */
	void addScoreAndThemeCount(User user,HttpServletRequest request);
	
	/**
	 * 会员每发表一个回复增加相应的分数和发帖数
	 * @param user
	 * @param request
	 */
	void addScoreAndReplyCount(User user,HttpServletRequest request);
	
	/**
	 * 用户登陆名
	 * @param loginNames
	 * @return
	 */
	List<User> getUsersByLoginNames(String... loginNames);
	/**
	 * 得到所有注册的用户
	 * @return
	 */
	int allUsers();
	
	
}
