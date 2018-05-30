package cn.yang.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;



/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-11-5
 */

@Entity
public class Forum implements Serializable{


	private static final long serialVersionUID = -8130671380161650813L;
	private Integer id;//版块编号
	private String  name;//版块名称
	private Boolean visible=true;//版块是否可见
	private Set<Forum> children=new HashSet<Forum>();//子版块
	private Forum parent;//父版块
	private Integer weight=1;//版块优先级，值越大，优先级越高
	private Set<User> users=new HashSet<User>();//版主
	/*帖子*/
	private Set<Theme> themes;
	/*此版块包括其所有子版块下的帖子总数*/
	private Integer allThemesCount=0;
	/*此版块包括其所有子版块下的最新帖子*/
	private Theme lastTheme;
	/*当天的帖子*/
	private List<Theme> currentDay_Themes;
	/**
	 * 构造方法
	 */
	public Forum(){}
	
	
	public Forum(Integer id) {
		super();
		this.id = id;
	}

	public Forum(String name) {
		this.name = name;
	}
	
	public Forum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	/**
	 * 版块编号
	 * @return
	 */
	@Id @GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 版块名称
	 * @return
	 */
	@Column(length=20,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    /**
     * 版块是否可见
     * @return
     */
	@Column(length=1,nullable=false)
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	/**
	 * 子版块
	 * @return
	 */
	@OneToMany(cascade={CascadeType.REFRESH,CascadeType.REMOVE,CascadeType.PERSIST},mappedBy="parent")
	@OrderBy(value="weight desc")
	public Set<Forum> getChildren() {
		return children;
	}
	public void setChildren(Set<Forum> children) {
		this.children = children;
	}
	/**
	 * 父版块
	 * @return
	 */
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="parentid")
	public Forum getParent() {
		return parent;
	}
	public void setParent(Forum parent) {
		this.parent = parent;
	}
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		    name="forum_user",
		    joinColumns=@JoinColumn(name="forum_id", referencedColumnName="id"),
		    inverseJoinColumns=@JoinColumn(name="user_loginName", referencedColumnName="loginName")
	)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void addModerator(User user){
		users.add(user);
	}

	@OneToMany(cascade={CascadeType.REFRESH,CascadeType.REMOVE,CascadeType.PERSIST},mappedBy="forum")
	public Set<Theme> getThemes() {
		return themes;
	}
	public void setThemes(Set<Theme> themes) {
		this.themes = themes;
	}
	
	@Column(nullable=false)
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	/**
	 * 添加帖子
	 * @param theme 帖子
	 * @return 对象本身
	 */
	public Forum addTheme(Theme theme){
		themes.add(theme);
		return this;
	}
	
	@Transient
	public Integer getAllThemesCount() {
		return allThemesCount;
	}


	public void setAllThemesCount(Integer allThemesCount) {
		this.allThemesCount = allThemesCount;
	}

	@Transient
	public Theme getLastTheme() {
		return lastTheme;
	}


	public void setLastTheme(Theme lastTheme) {
		this.lastTheme = lastTheme;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Forum other = (Forum) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	/**
	 * 添加子版块
	 * @param forum 子版块
	 * @return 对象本身
	 */
	public Forum addChild(Forum forum){
		children.add(forum);
		return this;
	}
	/**
	 * 删除子版块
	 * @param forum 子版块
	 * @return 对象本身
	 */
	public Forum removeChild(Forum forum){
		children.remove(forum);
		return this;
	}
	@Override
	public String toString() {
		return "版块号 :"+id+" 版块名称: "+name;
	}

/**
 * 当天的帖子
 * @return
 */
	@Transient
	public List<Theme> getCurrentDay_Themes() {
		return currentDay_Themes;
	}


	public void setCurrentDay_Themes(List<Theme> currentDayThemes) {
		currentDay_Themes = currentDayThemes;
	}
}
