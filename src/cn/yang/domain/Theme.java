package cn.yang.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.yang.util.MyDate;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-11-28
 * 此实体表示帖子
 */

@Entity
public class Theme extends Article {
   
	private static final long serialVersionUID = -836518606141621836L;
	private String title;//帖子标题
	private PostType type=PostType.SUBJECT;//帖子类型(普通帖、公告)
	private Forum forum;//帖子所属版块(必填项)
	private Set<Reply> replies;//帖子回复
	private Date updateTime=new Date();//帖子最后更新时间
	private Integer readCount=0;//帖子被阅读次数
	private Boolean commend=false;//帖子是否被推荐
	private Boolean top=false;//帖子是否置顶
    private Reply lastReply;//最新回帖
	private Set<Attachment> attachments=new HashSet<Attachment>();//附件
	
    
	public Theme(){}
	
	public Theme(Integer themeId){
		super(themeId);
	}
	@Column(length=100,nullable=false)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
    @Column(length=7,nullable=false)
    @Enumerated(EnumType.STRING)
	public PostType getType() {
		return type;
	}
	public void setType(PostType type) {
		this.type = type;
	}
	
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name="forumid")
	public Forum getForum() {
		return forum;
	}
	public void setForum(Forum forum) {
		this.forum = forum;
	}
	
	@OneToMany(cascade={CascadeType.REFRESH,CascadeType.REMOVE,CascadeType.PERSIST},mappedBy="theme")
	public Set<Reply> getReplies() {
		return replies;
	}
	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}
	/**
	 * 添加回复
	 * @param reply 回复
	 * @return 帖子
	 */
	public Theme addReply(Reply reply){
		replies.add(reply);
		return this;
	}
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return new MyDate(updateTime.getTime());
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(nullable=false)
	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	
	@Column(length=1,nullable=false)
	public Boolean getCommend() {
		return commend;
	}
	public void setCommend(Boolean commend) {
		this.commend = commend;
	}
	@Column(length=1,nullable=false)
	public Boolean getTop() {
		return top;
	}
	public void setTop(Boolean top) {
		this.top = top;
	}
	
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="theme")
	public Set<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	@Transient
	public Reply getLastReply(){
       return this.lastReply;
	}
	public void setLastReply(Reply reply){
	       this.lastReply=reply;
		}
	/**
	 * 添加附件
	 * @param attachment 附件
	 * @return 帖子
	 */
	public Theme addAttachment(Attachment attachment){
		attachments.add(attachment);
		return this;
	}
}
