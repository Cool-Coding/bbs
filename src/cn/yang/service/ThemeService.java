package cn.yang.service;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

import cn.yang.bean.QueryResult;
import cn.yang.bean.ThemeContainer;
import cn.yang.dao.DAO;
import cn.yang.domain.Forum;
import cn.yang.domain.Reply;
import cn.yang.domain.Theme;

public interface ThemeService extends DAO<Theme> {
	/**
	 * 启用或禁用帖子
	 * @param visible
	 * @param ids
	 */
	public void visibleManager(boolean visible,Serializable... ids);
	/**
	 * 删除所有id号所对应的帖子
	 * @param ids
	 */
	public void deleteTheme(Serializable... ids);
	
	/**
	 * 将所有id号所对应的帖子移到到指定的版块下
	 * @param ids
	 */
	public void moveThemesToForum(Serializable[] ids,Integer forumId);
	
	/**
	 * 得到帖子的最新回帖
	 * @param theme
	 * @return
	 */
	public Reply getLastReply(Theme theme);
}
