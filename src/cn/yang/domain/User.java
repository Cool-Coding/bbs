package cn.yang.domain;

import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 用户(会员)
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 *2011-4-7
 * 
 */

@Entity
public class User {
	// --- 基本信息
	private String loginName;// 登录名(唯一),最长为128个字符
	private String password;// 密码，最长为128个字符
	private String email;// Email地址,最长为128个字符

	// --- 个人信息
	private String nickname;// 昵称,最多128个字符，64个汉字
	private Gender gender; // 性别
	private byte[] avatar;// 头像
	private String signature;// 签名,最多127个汉字
    private Integer score=0;//积分
    private String  rank;//等级
    
	// --- 其他
	private Date registrationTime;// 注册时间
	private Date lastVisitTime; // 最后(上次)访问时间
	private String lastVisitIpAddr; // 最后(上次)访问时所使用的ip地址

	private int themeCount=0;// 发表的总主题数
	private int replyCount=0;// 发表的总回复数

	private boolean locked;// 是否被锁定
	private String autoLoginAuthKey;// 用于自动登陆的一个认证的字符串

	// private String activationKey;// 锁定用户自己激活帐号所用的激活码
	private Set<Role> roles = new HashSet<Role>(0);// 所拥有的角色（权限）

	public User(){}
	public User(String loginName){
		this.loginName=loginName;
	}
	@Id @Column(length=128)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
   
	@Column(length=128)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
   
	@Column(length=128)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
   
	@Column(length=128)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
   
	@Enumerated(EnumType.STRING)
	@Column(length=6)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
   
	@Lob   @Basic(fetch=FetchType.LAZY)
	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
   
	@Column(length=255)
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastVisitTime() {
		return lastVisitTime;
	}

	public void setLastVisitTime(Date lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}
   
	@Column(length=15)
	public String getLastVisitIpAddr() {
		return lastVisitIpAddr;
	}

	public void setLastVisitIpAddr(String lastVisitIpAddr) {
		this.lastVisitIpAddr = lastVisitIpAddr;
	}
  
	@Column(length=1)
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	@Column(nullable=false)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
    
	@Column(length=20)
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
  
	@Column(nullable=false)
	public int getThemeCount() {
		return themeCount;
	}

	public void setThemeCount(int themeCount) {
		this.themeCount = themeCount;
	}
   
	@Column(nullable=false)
	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	
  @Column(length=128)
   public String getAutoLoginAuthKey() {
		return autoLoginAuthKey;
	}
	
	public void setAutoLoginAuthKey(String autoLoginAuthKey) {
		this.autoLoginAuthKey = autoLoginAuthKey;
	}
	
@ManyToMany(fetch=FetchType.EAGER)
@JoinTable(
	    name="user_role",
	    joinColumns=@JoinColumn(name="user_loginName", referencedColumnName="loginName"),
	    inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id")
)
  public Set<Role> getRoles() {
		return roles;
	}
	
  public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return new StringBuffer().append("[User: ")//
				.append(",loginName=").append(loginName)//
				.append(",email=").append(email)//
				.append(",registrationTime=").append(registrationTime)//
				.append(",nickname=").append(nickname)//
				.append(",replyCount=").append(themeCount)//
				.append(",replyCount=").append(replyCount)//
				.append("]")//
				.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((loginName == null) ? 0 : loginName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		return true;
	}
}
