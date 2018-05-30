package cn.yang.service.impl;

import java.io.Serializable;

import javax.persistence.Query;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.yang.bean.QueryResult;
import cn.yang.bean.ThemeContainer;
import cn.yang.dao.DAOSupport;
import cn.yang.domain.Attachment;
import cn.yang.domain.Forum;
import cn.yang.domain.Reply;
import cn.yang.domain.Theme;
import cn.yang.service.ThemeService;

@Service
@Transactional
public class ThemeServiceImpl extends DAOSupport<Theme> implements ThemeService {

	@Override
	public void visibleManager(boolean visible,Serializable... ids) {
		StringBuffer idRange=new StringBuffer("");
		for(Serializable id:ids){
			idRange.append(id).append(",");
		}
		idRange.deleteCharAt(idRange.length()-1);
       	String jpql="update Article o set o.visible=?1 where id in("+idRange.toString()+")";
       	
       	Query query=em.createQuery(jpql);
       	query.setParameter(1, visible);
       	
       	query.executeUpdate();
       	
	}

	@Override
	public void deleteTheme(Serializable... ids) {
		StringBuffer idRange=new StringBuffer("");
		for(Serializable id:ids){
			idRange.append(id).append(",");
		}
		idRange.deleteCharAt(idRange.length()-1);
       	String jpql="delete from Theme o where id in("+idRange.toString()+")";
       	
       	Query query=em.createQuery(jpql);
       	
       	query.executeUpdate();
	}

	@Override
	public void moveThemesToForum(Serializable[] ids,Integer forumId) {
		StringBuffer idRange=new StringBuffer("");
		for(Serializable id:ids){
			idRange.append(id).append(",");
		}
		idRange.deleteCharAt(idRange.length()-1);

		String jpql="update Article o set o.forum.id="+forumId+" where o.id in("+idRange.toString()+")";//更新操作作用的类是Article,删除操作的对象是具体的子类。
       	
       	Query query=em.createQuery(jpql);
       	
       	query.executeUpdate();
		
	}

	@Override
	public Reply getLastReply(Theme theme) {
		String jpql="select o from Reply o where o.visible=true and o.theme.id = ? order by createTime desc";
       	Query query=em.createQuery(jpql);
       	query.setParameter(1, theme.getId());
       	query.setFirstResult(0);
       	query.setMaxResults(1);
       	
       	Reply reply=null;
       	try{
       	reply=(Reply)query.getSingleResult();
       	}catch(Exception e){
       		return null;
       	}
		return reply;
	}
}
