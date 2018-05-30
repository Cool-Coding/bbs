package cn.yang.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 权限
 * 
 */
@Entity
public class SystemPrivilege {
	private Integer id;
	private String name; // 权限名称

	private String resource; // 所操作的资源
	private String action;// 对资源所做的操作

	public SystemPrivilege() {
	}

	public SystemPrivilege(String resource, String action) {
		this.resource = resource;
		this.action = action;
	}

	public SystemPrivilege(String name, String resource, String action) {
		this.name = name;
		this.resource = resource;
		this.action = action;
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
	@Column(length=64)
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	@Column(length=64)
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return new StringBuffer().append("[SystemPrivilege: ")//
				.append("id=").append(id)//
				.append(",name=").append(name)//
				.append(",resource=").append(resource)//
				.append(",action=").append(action)//
				.append("]")//
				.toString();
	}

}
