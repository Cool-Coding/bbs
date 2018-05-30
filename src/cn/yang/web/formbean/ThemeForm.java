package cn.yang.web.formbean;

import java.util.Set;

import org.apache.struts.upload.FormFile;

import cn.yang.bean.ThemeType;
import cn.yang.domain.Attachment;
import cn.yang.domain.Forum;
import cn.yang.domain.PostType;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-12-7
 */
public class ThemeForm extends BaseForm {

	private static final long serialVersionUID = -3183227991149442318L;
	private Integer forumId;//帖子所属版块的ID
	private String forumName;//帖子所属版块的名称
	private Set<Forum> siblingForums;//帖子所在版块的所有同级版块(包括自身)
	private String author;//发表帖子的用户名
	private String title;//帖子的标题
	private String content;//帖子的内容
	private Boolean isTop=false;//是否置顶
	private Integer themeId;//帖子ID
	private Integer[] themeIds;//帖子ID数组
	private String[] attachments;//附件相关信息(格式为：附件上传时的名字*附件保存时的名字*附件的大小)
	private Integer attachmentId;//附件ID
	private String[] remarks;//附件评论
    private ThemeType[] themeTypes;//帖子类型
	private Boolean query;
	private Integer category;//查询的类别，0代表标题，1代表作者
	private String keywords;//查询的关键字
	private String type;//帖子的类型(公告、普通帖)
	
	public String getForumName() {
		return forumName;
	}
	public void setForumName(String forumName) {
		this.forumName = forumName;
	}
	public Set<Forum> getSiblingForums() {
		return siblingForums;
	}
	public void setSiblingForums(Set<Forum> siblingForums) {
		this.siblingForums = siblingForums;
	}
	public Integer getThemeId() {
		return themeId;
	}
	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}
	public Boolean getIsTop() {
		return isTop;
	}
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}
	public Boolean getQuery() {
		return query;
	}
	public void setQuery(Boolean query) {
		this.query = query;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	//帖子所属版块的ID
	public Integer getForumId() {
		return forumId;
	}
	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}
	//发表帖子的用户名
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	//帖子的标题
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	//帖子的内容
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	//帖子ID数组
	public Integer[] getThemeIds() {
		return themeIds;
	}
	public void setThemeIds(Integer[] themeIds) {
		this.themeIds = themeIds;
	}
	//附件
	public String[] getAttachments() {
		return attachments;
	}
	public void setAttachments(String[] attachments) {
		this.attachments = attachments;
	}
	public String[] getRemarks() {
		return remarks;
	}
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	public ThemeType[] getThemeTypes() {
		return themeTypes;
	}
	public void setThemeTypes(ThemeType[] themeTypes) {
		this.themeTypes = themeTypes;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}
}
