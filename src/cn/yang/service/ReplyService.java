package cn.yang.service;

import cn.yang.dao.DAO;
import cn.yang.domain.Reply;



public interface ReplyService extends DAO<Reply> {

	
	/**
	 * 设置回帖的登陆可见性
	 * @param id 实体id
	 * @param visible
	 */
	public void onlyLogin(Integer[] ids,Boolean visible);
	
	/**
	 * 设置回帖的前台可见性
	 * @param id 实体id
	 * @param visible
	 */
	public void seenByFront(Integer[] ids,Boolean visible);
	
}
