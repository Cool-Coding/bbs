package cn.yang.web.formbean;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import cn.yang.domain.Gender;



@SuppressWarnings("serial")
public class UserForm extends BaseForm {
	
	// --- 基本信息
	private String loginName;// 登录名
	private String password;// 密码
	private String password2;// 密码2
	private String email;// Email地址

	// --- 个人信息
	private String nickname;// 昵称
	private Gender gender = Gender.MALE; // 性别, 默认为保密
	private FormFile avatarResource;// 头像
	private String signature;// 签名
    private String rank;//等级
	private boolean autoLogin; // 是否自动登录
	private int autoLoginDays;// 自动登录的天数

	private Integer[] roleIdList;

	// 管理会员时的过滤条件用
	private Integer roleId;
	private String locked;

	public ActionErrors forRegister(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (StringUtils.isBlank(loginName) || loginName.length() < 2 || loginName.length() > 64) {
			errors.add("loginName", new ActionMessage("请输入登录名，长度为2-64个字符", false));
		} else if (loginName.indexOf("<") > -1 || loginName.indexOf(">") > -1) {
			errors.add("loginName", new ActionMessage("登录名只能包括大小写字母、中文、数字", false));
		}
		if (StringUtils.isBlank(nickname) || nickname.length() < 2 || nickname.length() > 64) {
			errors.add("nickname", new ActionMessage("请输入昵称，长度为2-64个字符", false));
		} else if (nickname.indexOf("<") > -1 || nickname.indexOf(">") > -1) {
			errors.add("loginName", new ActionMessage("昵称只能包括大小写字母、中文、数字", false));
		}
/*		 if (!RegExpUtils.isEmail(email)) {
		 errors.add("email", new ActionMessage("请输入正确的email地址", false));
		 }*/

		if (StringUtils.isEmpty(password)) {
			errors.add("password", new ActionMessage("请输入密码", false));
		} else if (!password.equals(password2)) {
			errors.add("password2", new ActionMessage("请重复输入密码", false));
		}

		if (signature != null && signature.length() > 255) {
			errors.add("password", new ActionMessage("签名的长度不能超过255个字符", false));
		}

		return errors;
	}

	public ActionErrors forEdit(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (StringUtils.isBlank(nickname) || nickname.length() < 2 || nickname.length() > 64) {
			errors.add("nickname", new ActionMessage("请输入昵称，长度为2-64个字符", false));
		} else if (nickname.indexOf("<") > -1 || nickname.indexOf(">") > -1) {
			errors.add("loginName", new ActionMessage("昵称只能包括大小写字母、中文、数字", false));
		}

		if (signature != null && signature.length() > 255) {
			errors.add("password", new ActionMessage("签名的长度不能超过255个字符", false));
		}

		return errors;
	}
	
	public ActionErrors forLogin(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (StringUtils.isBlank(loginName)) {
			errors.add("loginName", new ActionMessage("请输入用户名", false));
		}
		if (StringUtils.isEmpty(password)) {
			errors.add("password", new ActionMessage("请输入密码", false));
		}

		return errors;
	}

	public ActionErrors forChangePassword(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (StringUtils.isEmpty(password)) {
			errors.add("password", new ActionMessage("请输入密码", false));
		} else if (!password.equals(password2)) {
			errors.add("password2", new ActionMessage("请重复输入密码", false));
		}

		return errors;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public FormFile getAvatarResource() {
		return avatarResource;
	}

	public void setAvatarResource(FormFile avatarResource) {
		this.avatarResource = avatarResource;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public boolean isAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}

	public int getAutoLoginDays() {
		return autoLoginDays;
	}

	public void setAutoLoginDays(int autoLoginDays) {
		this.autoLoginDays = autoLoginDays;
	}


	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public Integer[] getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(Integer[] roleIdList) {
		this.roleIdList = roleIdList;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
	
}
