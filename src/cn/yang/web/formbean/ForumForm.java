package cn.yang.web.formbean;

import java.io.Serializable;
import java.util.Set;

import cn.yang.domain.Forum;
import cn.yang.domain.User;


/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-11-16
 */
public class ForumForm extends BaseForm { 
	private static final long serialVersionUID = 171605155708434823L;
	
	/*版块ID*/
	private Integer id;
    /*版块名称*/
	private String name;
	/*版块是否可见*/
	private Boolean visible;
	/*是否是查询*/
	private String query;//不能用Boolean，其默认值为null
	/*父版块*/
	private Forum parent=new Forum();
	/*页面传递过来的复选框值*/
	private Serializable[] forumIds;
	/*是否显示简版块列表*/
	private Boolean simpleForumList=false;
	/*是否是为其它模块服务*/
	private Boolean other=false;
	/*版主名称*/
   private String moderator;
   /*版主名称集合*/
   private String[] moderator_name;
   /*优先级*/
   private Integer weight;
   /*版主集合*/
   private Set<User> moderators;
   
	public Boolean getSimpleForumList() {
		return simpleForumList;
	}
	public void setSimpleForumList(Boolean simpleForumList) {
		this.simpleForumList = simpleForumList;
	}
	public Forum getParent() {
		return parent;
	}
	public void setParent(Forum parent) {
		this.parent = parent;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Serializable[] getForumIds() {
		return forumIds;
	}
	public void setForumIds(Serializable[] forumIds) {
		this.forumIds = forumIds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public Boolean getOther() {
		return other;
	}
	public void setOther(Boolean other) {
		this.other = other;
	}
	public String getModerator() {
		return moderator;
	}
	public void setModerator(String moderator) {
		this.moderator = moderator;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Set<User> getModerators() {
		return moderators;
	}
	public void setModerators(Set<User> moderators) {
		this.moderators = moderators;
	}
	public String[] getModerator_name() {
		return moderator_name;
	}
	public void setModerator_name(String[] moderatorName) {
		moderator_name = moderatorName;
	}
}
