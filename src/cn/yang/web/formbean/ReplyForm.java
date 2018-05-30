package cn.yang.web.formbean;

public class ReplyForm extends BaseForm {
    
	/*帖子的ID*/
	private Integer themeId;
	/*帖子所对应版块的ID*/
   private Integer forumId;
   
	/*回帖ID*/
	private Integer replyId;
	
	/*登陆的用户*/
	private String author;
	
	/*回帖内容*/
	private String content;
	
	public Integer getThemeId() {
		return themeId;
	}
	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}
	public Integer getForumId() {
		return forumId;
	}
	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
    
	
}
