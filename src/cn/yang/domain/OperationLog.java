package cn.yang.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 操作日志
 * 
 */
@SuppressWarnings("unchecked")
@Entity
public class OperationLog extends SystemLog {
	private Class entityType;// 所操作的实体(资源)类型
	private Serializable entityId;// 所操作的实体的id
	private String type;

	public OperationLog() {
	}

	@Column(length=128)
	public Class getEntityType() {
		return entityType;
	}

	@Column(length=45)
	public Serializable getEntityId() {
		return entityId;
	}

	@Column(length=32)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setEntityType(Class entityType) {
		this.entityType = entityType;
	}

	public void setEntityId(Serializable entityId) {
		this.entityId = entityId;
	}

	@Override
	public String toString() {
		return new StringBuffer().append("[OperationLog: ")//
				.append("id=").append(getId())//
				.append(",logTime=").append(getLogTime())//
				.append(",operator=").append(getOperator() == null ? null : getOperator().getLoginName())//
				.append(",operIpAddr=").append(getOperIpAddr())//
				.append(",comment=").append(getComment())//
				.append(",entityType=").append(entityType)//
				.append(",entityId=").append(entityId)//
				.append(",type=").append(type)//
				.append("]")//
				.toString();
	}
}
