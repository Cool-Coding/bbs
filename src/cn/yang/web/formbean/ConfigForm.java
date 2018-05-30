package cn.yang.web.formbean;

import org.apache.struts.action.ActionForm;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 *2011-4-21
 * 
 */
public class ConfigForm extends ActionForm {

	private static final long serialVersionUID = 7258682141442366200L; 
	
	//后台设置参数
	private boolean disableAllChildren_forum;//禁用父版块时是否禁用所有子版块
	private boolean enableAllChildren_forum;//启用父版块时是否启用所有子版块
	private String  viewPageRecordsCols_simpleForum;//简单版块列表的列数
	private String  viewPageRecords;//每页所显示的记录数(版块、帖子列表)
	private String replyCount_perPage;// 回帖列表页面显示的回帖数
	private String viewPageCount;//每页显示页码数
	private boolean show_invisible_reply;//显示回帖时是否将不可见的回帖显示出来
	//前台设置参数
	private String front_viewPageRecords;//每页所显示的记录数(版块、帖子列表)
	private String front_viewPageCount; //每页显示页码数
	private String front_replyCount_perPage;//回帖列表页面显示的回帖数
	private String front_score_per_theme;// 会员每发表一个帖子增加的分数
	private String front_score_per_reply;//会员每发表一个回复增加的分数
	//通用配置
	private String attachment_size;//附件允许上传的大小
	private String attachment_count;//允许用户上传附件的个数
	private boolean  theme_delete_for_attachment_delete;//删除帖子时是否也删除相应的附件
	private boolean attachment_delete_for_user;//用户上传附件之后，删除时是否进行物理删除
	private String avatar_max_width;//会员头像的最大宽度 
	private String avatar_max_height;//会员头像的最大高度 
	
	public boolean isDisableAllChildren_forum() {
		return disableAllChildren_forum;
	}
	public void setDisableAllChildren_forum(boolean disableAllChildrenForum) {
		disableAllChildren_forum = disableAllChildrenForum;
	}
	public boolean isEnableAllChildren_forum() {
		return enableAllChildren_forum;
	}
	public void setEnableAllChildren_forum(boolean enableAllChildrenForum) {
		enableAllChildren_forum = enableAllChildrenForum;
	}
	public String getViewPageRecordsCols_simpleForum() {
		return viewPageRecordsCols_simpleForum;
	}
	public void setViewPageRecordsCols_simpleForum(
			String viewPageRecordsColsSimpleForum) {
		viewPageRecordsCols_simpleForum = viewPageRecordsColsSimpleForum;
	}
	public String getViewPageRecords() {
		return viewPageRecords;
	}
	public void setViewPageRecords(String viewPageRecords) {
		this.viewPageRecords = viewPageRecords;
	}
	public String getReplyCount_perPage() {
		return replyCount_perPage;
	}
	public void setReplyCount_perPage(String replyCountPerPage) {
		replyCount_perPage = replyCountPerPage;
	}
	public String getViewPageCount() {
		return viewPageCount;
	}
	public void setViewPageCount(String viewPageCount) {
		this.viewPageCount = viewPageCount;
	}
	public boolean isShow_invisible_reply() {
		return show_invisible_reply;
	}
	public void setShow_invisible_reply(boolean showInvisibleReply) {
		show_invisible_reply = showInvisibleReply;
	}
	public String getFront_viewPageRecords() {
		return front_viewPageRecords;
	}
	public void setFront_viewPageRecords(String frontViewPageRecords) {
		front_viewPageRecords = frontViewPageRecords;
	}
	public String getFront_viewPageCount() {
		return front_viewPageCount;
	}
	public void setFront_viewPageCount(String frontViewPageCount) {
		front_viewPageCount = frontViewPageCount;
	}
	public String getFront_replyCount_perPage() {
		return front_replyCount_perPage;
	}
	public void setFront_replyCount_perPage(String frontReplyCountPerPage) {
		front_replyCount_perPage = frontReplyCountPerPage;
	}
	public String getFront_score_per_theme() {
		return front_score_per_theme;
	}
	public void setFront_score_per_theme(String frontScorePerTheme) {
		front_score_per_theme = frontScorePerTheme;
	}
	public String getFront_score_per_reply() {
		return front_score_per_reply;
	}
	public void setFront_score_per_reply(String frontScorePerReply) {
		front_score_per_reply = frontScorePerReply;
	}
	public String getAttachment_size() {
		return attachment_size;
	}
	public void setAttachment_size(String attachmentSize) {
		attachment_size = attachmentSize;
	}
	public String getAttachment_count() {
		return attachment_count;
	}
	public void setAttachment_count(String attachmentCount) {
		attachment_count = attachmentCount;
	}
	public boolean isTheme_delete_for_attachment_delete() {
		return theme_delete_for_attachment_delete;
	}
	public void setTheme_delete_for_attachment_delete(
			boolean themeDeleteForAttachmentDelete) {
		theme_delete_for_attachment_delete = themeDeleteForAttachmentDelete;
	}
	public boolean isAttachment_delete_for_user() {
		return attachment_delete_for_user;
	}
	public void setAttachment_delete_for_user(boolean attachmentDeleteForUser) {
		attachment_delete_for_user = attachmentDeleteForUser;
	}
	public String getAvatar_max_width() {
		return avatar_max_width;
	}
	public void setAvatar_max_width(String avatarMaxWidth) {
		avatar_max_width = avatarMaxWidth;
	}
	public String getAvatar_max_height() {
		return avatar_max_height;
	}
	public void setAvatar_max_height(String avatarMaxHeight) {
		avatar_max_height = avatarMaxHeight;
	}	
}
