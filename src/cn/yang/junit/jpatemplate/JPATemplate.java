package cn.yang.junit.jpatemplate;

import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import cn.yang.bean.QueryResult;
import cn.yang.domain.Forum;
import cn.yang.service.ForumService;
import cn.yang.service.impl.ForumServiceImpl;

/**
 * 当只有JPA时，为了方便，写了一个JPATemplate,如同HibernateTemplate一般。
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-11-7
 */
public class JPATemplate {
	private static EntityManagerFactory factory;
	private EntityManager em;
	private Forum forum;
    private QueryResult<Forum> queryResult;
	static{
		factory=Persistence.createEntityManagerFactory("bbs");
	}
	public void general(Callback callback){
		em=factory.createEntityManager();
		ForumServiceImpl forumService=new ForumServiceImpl();
		forumService.setEntityManager(em);
		em.getTransaction().begin();
		callback.invoke(forumService);
		em.getTransaction().commit();
		em.close();
	}
	public void save(final Forum forum){
		general(new Callback(){
			@Override
			public void invoke(ForumService em) {
               em.save(forum);				
			}
		});
	}
	public Forum find(final Serializable id){
		  general(new Callback(){
			@Override
			public void invoke(ForumService em) {
               forum=em.find(id);				
			}
		});
		  return forum;
	}
	public void update(final Forum forum){
		  general(new Callback(){
			@Override
			public void invoke(ForumService em) {
             em.update(forum);				
			}
		});
	}
	public void delete(final Serializable... ids){
		  general(new Callback(){
			@Override
		  public void invoke(ForumService em) {
           em.delete(ids);				
			}
		});
	}
	public QueryResult<Forum> scrollData(final String wherejpql,final Object[] params,final int firstindex,final int maxresult,final LinkedHashMap<String,String> orderby){
		  general(new Callback(){
			@Override
		  public void invoke(ForumService em) {
           queryResult=em.getScrollData(firstindex, maxresult, wherejpql, params, orderby);		
			}
		});
		  return queryResult;
	}
}
