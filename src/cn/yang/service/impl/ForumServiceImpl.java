package cn.yang.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.yang.bean.QueryResult;
import cn.yang.bean.ThemeContainer;
import cn.yang.dao.DAOSupport;
import cn.yang.domain.Forum;
import cn.yang.domain.Theme;
import cn.yang.service.ForumService;

@Service
@Transactional
public class ForumServiceImpl extends DAOSupport<Forum> implements ForumService {


	@Override
	public void visibleManager(boolean visible,Serializable... ids) {
		StringBuffer idRange=new StringBuffer("");
		for(Serializable id:ids){
			idRange.append(id).append(",");
		}
		idRange.deleteCharAt(idRange.length()-1);
       	String jpql="update Forum o set o.visible=? where id in("+idRange.toString()+")";
       	
       	Query query=em.createQuery(jpql);
       	query.setParameter(1, visible);
       	
       	query.executeUpdate();
       	
	}
   /**
    * 得到所有的子版块ID
    */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public Set<Serializable> getAllChildForumIds(Serializable id) {
		/*所有子版块的ID*/
		Set<Serializable> childIds=new HashSet<Serializable>();
		LoopToGetChildrenIds(childIds,id);//递归得到所有的子版块ID
		return childIds;
	}
	private void LoopToGetChildrenIds(Set<Serializable> childIds,Serializable id){
        Forum forum=find(id);
        childIds.add(forum.getId());
        Set<Forum> childForums=forum.getChildren();
        if(childForums!=null&&childForums.size()>0){
        	for(Forum childrenForum:childForums){
        		Serializable childId=childrenForum.getId();//将forum中的Integer型向上转型，否则下一句会出错
        		LoopToGetChildrenIds(childIds,childId);
        	}
        }
	}
	//结束
    /**
     * 得到指定版块的所有父版块(包括自身)集合
     */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<Forum> getAllParentForum(Serializable id) {
		List<Forum> parents=new ArrayList<Forum>();
		//查询当前id对应的版块
		Forum forum=find(id);
		//如果当前版块存在，则进行下面操作
		if(forum!=null){
			parents.add(forum);
			//LoopToGetParent(parents,forum);
			Forum parent=forum.getParent();
			while(parent!=null){
				parents.add(parent);
				parent=parent.getParent();
			}
		}
		return parents;
	}
	
/*	private void LoopToGetParent(List<Forum> parents,Forum forum){
		  Forum parent=forum.getParent();
		  if(parent!=null) {
			  parents.add(parent);
			  LoopToGetParent(parents,parent);
		  }
	} */
   //结束
	/**
	 * 得到主键为id的版块的最后一级的某一个子版块
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public Forum getLastChildForum(Integer id) {
		Forum forum=find(id);
        return loopToGetLastOneForum(forum);
	}
    
	private Forum loopToGetLastOneForum(Forum forum){
		Set<Forum> forums=forum.getChildren();
		if(forums.size()<=0){
			return forum;
		}else{
		  return loopToGetLastOneForum(forums.iterator().next());
		}
	}
	
	/**
	 * 得到主键为id的版块的最后一级的所有子版块
	 */
	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public Set<Forum> getLastChildForums(Integer id) {
        Set<Forum> forums=new HashSet<Forum>();
        Forum forum=find(id);
        loopToGetLastForums(forums,forum);
		return forums;
	}
	private void loopToGetLastForums(Set<Forum> forums,Forum forum){
		Iterator<Forum> lists=null;
		Set<Forum> childForums=forum.getChildren();
		if(childForums.size()<=0){
			forums.add(forum);
		}else{
			lists=childForums.iterator();
			while(lists.hasNext()){
				loopToGetLastForums(forums,lists.next());
			}
		}
	}
	/**
	 * 得到所有版块下帖子的总和和最新帖子
	 * @param forums
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public ThemeContainer getThemeCountOfLastForms(Set<Forum> forums) {
		
		if(forums.size()<=0) return null;
		
		ThemeContainer container=null;
        StringBuffer ids=new StringBuffer();
        for(Forum forum:forums){
        	ids.append(forum.getId()).append(",");
        }
        ids.deleteCharAt(ids.length()-1);
        
        String jpql="select o from Theme o where o.forum.id in ("+ids.toString()+") order by o.createTime desc";
        
        Query query=em.createQuery(jpql);
        
        List<Theme> themes=query.getResultList();
        
        int count=themes.size();
        if(count>0){
        Theme theme=themes.get(0);
        container=new ThemeContainer();
        container.setCounts(count);
        container.setNewestTheme(theme);
        }
		return container;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<Theme> getThemesByForumIds(int firstIndex, int viewPageRecords, Set<Forum> forums) {
		
		if(forums.size()<=0) return null;
		QueryResult<Theme> results=new QueryResult<Theme>();
        StringBuffer ids=new StringBuffer();
        for(Forum forum:forums){
        	ids.append(forum.getId()).append(",");
        }
        ids.deleteCharAt(ids.length()-1);
        
        String jpql="select o from Theme o where o.visible=true and o.forum.id in ("+ids.toString()+") order by o.top desc,o.commend desc,o.createTime desc";
        
        Query query=em.createQuery(jpql);
        if(firstIndex!=-1 && viewPageRecords!=-1) query.setFirstResult(firstIndex).setMaxResults(viewPageRecords);
        
        List<Theme> themes=query.getResultList();
        if(themes.size()<=0) return results;
        
        results.setResultlist(themes);
        
         jpql="select count(o) from Theme o where o.forum.id in ("+ids.toString()+")";
         query=em.createQuery(jpql);
         results.setTotalrecord((Long)query.getSingleResult());
		return results;
	}
	//通过查询条件查询帖子
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<Theme> searchThemes(int firstIndex, int viewPageRecords, String wherejpql,Object[] params) {

		QueryResult<Theme> results=new QueryResult<Theme>();

        
        String jpql="select o from Theme o where o.visible=true and "+wherejpql+" order by o.top desc,o.commend desc,o.createTime desc";
        
        Query query=em.createQuery(jpql);
        if(firstIndex!=-1 && viewPageRecords!=-1) query.setFirstResult(firstIndex).setMaxResults(viewPageRecords);
       
        setQueryParams(query,params);
        
        List<Theme> themes=query.getResultList();
        if(themes.size()<=0) return results;
        
        results.setResultlist(themes);
        
         jpql="select count(o) from Theme o where "+wherejpql;
         query=em.createQuery(jpql);
         
         setQueryParams(query,params);
         
         results.setTotalrecord((Long)query.getSingleResult());
		return results;
	}
	@Override
	public List<Theme> getThemesInCurrentDay(Set<Forum> forums) {
		if(forums.size()<=0) return null;
        StringBuffer ids=new StringBuffer();
        for(Forum forum:forums){
        	ids.append(forum.getId()).append(",");
        }
        ids.deleteCharAt(ids.length()-1);
        
        String jpql="select o from Theme o where o.visible=true and o.forum.id in ("+ids.toString()+") and o.createTime between ?1 and ?2";
        
        Query query=em.createQuery(jpql);
        //当天开始
		Date today_start=new Date();
		today_start.setHours(0); 
		today_start.setMinutes(0); 
		today_start.setSeconds(0); 
        //当天结束
		Date today_end=new Date();
		today_end.setHours(23); 
		today_end.setMinutes(59); 
		today_end.setSeconds(50); 
		
		query.setParameter(1, today_start);
		query.setParameter(2, today_end);
		
        List<Theme> themes=query.getResultList();
		return themes;
	}
}
