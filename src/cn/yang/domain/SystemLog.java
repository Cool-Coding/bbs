package cn.yang.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 系统日志
 * 
 * 
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class SystemLog {
	private Integer id;
	private Date logTime;// 记录时间
	private String comment; // 备注
	private User operator;// 操作者
	private String operIpAddr;// 操作者所用的IP地址

	
	@Id @GeneratedValue
	public Integer getId() {
		return id;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLogTime() {
		return logTime;
	}

	 @ManyToOne
	 @JoinColumn(name="userid")
	public User getOperator() {
		return operator;
	}
	 
	 @Column(length=16)
	public String getOperIpAddr() {
		return operIpAddr;
	}

	@Lob
	public String getComment() {
		return comment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}   
	
	public void setOperator(User operator) {
		this.operator = operator;
	}

	public void setOperIpAddr(String operIpAddr) {
		this.operIpAddr = operIpAddr;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return new StringBuffer().append("[SystemLog: ")//
				.append("id=").append(id)//
				.append(",logTime=").append(logTime)//
				.append(",operator=").append(operator == null ? null : operator.getLoginName())//
				.append(",operIpAddr=").append(operIpAddr)//
				.append(",comment=").append(comment)//
				.append("]")//
				.toString();
	}
}
