package cn.yang.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-12-10
 */
@Entity
public class Attachment implements Serializable {

	private static final long serialVersionUID = -4129946816300758724L;
	private Integer id;//附件id
	private String realname;//附件被上传时的名称
	private String byname;//附件在物理存储器上别名
	private String path;//附件在服务器端的相对路径
	private Float size=0.0f;//附件的大小(以KB为单位)
	private Integer downloadedCount=0;//附件被下载的次数
	private Theme theme;//附件对应的帖子
	private String remark;//附件备注
	
	@Id @GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(length=256,nullable=false)
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	@Column(length=42,nullable=false)
	public String getByname() {
		return byname;
	}
	public void setByname(String byname) {
		this.byname = byname;
	}
	@Column(nullable=false)
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Column(nullable=false)
	public Float getSize() {
		return size;
	}
	public void setSize(Float size) {
		this.size = size;
	}
	@Column(nullable=false)
	public Integer getDownloadedCount() {
		return downloadedCount;
	}
	public void setDownloadedCount(Integer downloadedCount) {
		this.downloadedCount = downloadedCount;
	}
	
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name="themeid")
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	@Column(length=256)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((byname == null) ? 0 : byname.hashCode());
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
		Attachment other = (Attachment) obj;
		if (byname == null) {
			if (other.byname != null)
				return false;
		} else if (!byname.equals(other.byname))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Attachment [downloadedCount=" + downloadedCount + ", realname="
				+ realname + ", size=" + size + "]";
	}
}
