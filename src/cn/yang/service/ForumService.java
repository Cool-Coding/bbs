package cn.yang.service;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.yang.bean.QueryResult;
import cn.yang.bean.ThemeContainer;
import cn.yang.dao.DAO;
import cn.yang.domain.Forum;
import cn.yang.domain.Theme;

public interface ForumService extends DAO<Forum> {
    
	/**
	 * 启用或禁用版块
	 * @param visible
	 * @param ids
	 */
	public void visibleManager(boolean visible,Serializable... ids);
	/**
	 * 得到父版块所有子版块(包括子版块下的子版块)的Id
	 * @param id
	 * @return
	 */
	public Set<Serializable> getAllChildForumIds(Serializable id);
	/**
	 * 得到当前版块的所有父版块
	 * @param id
	 * @return
	 */
	public List<Forum> getAllParentForum(Serializable id);
	/**
	 * 得到主键为id的版块的最后一级的某一个子版块
	 * @param id
	 * @return
	 */
	public Forum getLastChildForum(Integer id);
	/**
	 * 得到主键为id的版块的最后一级的所有子版块
	 * @param id
	 * @return 最后一级子版块的集合
	 */
	public Set<Forum> getLastChildForums(Integer id);
	
	/**
	 * 得到所有版块下帖子的总和和最新帖子
	 * @param forums
	 * @return 
	 */
	public ThemeContainer getThemeCountOfLastForms(Set<Forum> forums);
	/**
	 * 得到所有版块下当天的所有帖子
	 * @param forums
	 * @return
	 */
	public List<Theme> getThemesInCurrentDay(Set<Forum> forums);
	/**
	 * 得到属于某些版块下所有帖子
	 * @param froumIds 版块ID
	 * @return 帖子集合
	 */
	public QueryResult<Theme>  getThemesByForumIds(int firstIndex, int viewPageRecords, Set<Forum> forums);

	/**
	 * 通过查询条件查询帖子
	 * @param firstIndex
	 * @param viewPageRecords
	 * @param wherejpql
	 * @param params
	 * @return
	 */
	public QueryResult<Theme> searchThemes(int firstIndex, int viewPageRecords, String wherejpql,Object[] params);

}
