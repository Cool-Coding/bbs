package cn.yang.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-11-28
 * 帖子的回复
 */

@Entity
public class Reply extends Article {

	private static final long serialVersionUID = 8235541473931541519L;
    
	/*是否只有登陆才能查看*/
	private Boolean onlyLogin=false;
    
	/*回复所属帖子*/
	private Theme theme;
	
	public Reply(){}
	
	public Reply(Integer replyId) {
		super(replyId);
	}

	@Column(length=1,nullable=false)
	public Boolean getOnlyLogin() {
		return onlyLogin;
	}

	public void setOnlyLogin(Boolean onlyLogin) {
		this.onlyLogin = onlyLogin;
	}
    
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name="themeid")
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
}
