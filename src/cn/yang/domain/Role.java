package cn.yang.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * 角色
 * 
 */

@Entity
public class Role {
	private Integer id;
	private String name; // 名称
	private String description;// 描述
	private boolean defaultForNewUser;// 是否是新注册用户的默认角色
	private Set<SystemPrivilege> systemPrivileges = new HashSet<SystemPrivilege>(0);// 拥有的权限

	private Set<User> users=new HashSet<User>();
	public Role() {
	}
   public Role(Integer id){
	   this.id=id;
   }
	public Role(String name) {
		this.name = name;
	}
   
   @Id @GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
   @Column(length=64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length=255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		    name="role_privilege",
		    joinColumns=@JoinColumn(name="role_id", referencedColumnName="id"),
		    inverseJoinColumns=@JoinColumn(name="privilege_id", referencedColumnName="id")
	)
	public Set<SystemPrivilege> getSystemPrivileges() {
		return systemPrivileges;
	}
	
	public void setSystemPrivileges(Set<SystemPrivilege> systemPrivileges) {
		this.systemPrivileges = systemPrivileges;
	}
	
	@ManyToMany(mappedBy="roles")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Column(length=1)
	public boolean isDefaultForNewUser() {
		return defaultForNewUser;
	}

	public void setDefaultForNewUser(boolean defaultForNewUser) {
		this.defaultForNewUser = defaultForNewUser;
	}

}
