package cn.yang.dao;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.yang.bean.QueryResult;
import cn.yang.util.GenericUtil;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-11-6
 * @param <T>
 */

@Transactional
public class DAOSupport<T> implements DAO<T> {
    
	@PersistenceContext
	protected EntityManager em;
    
	private Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public DAOSupport(){
		entityClass=GenericUtil.getSuperClassGenericType(this.getClass());
	}
	public void save(T entity) {
		em.persist(entity);
	}
   public void setEntityManager(EntityManager em){
		this.em=em;
	}
	public void delete(Serializable... ids) {
        for(Serializable id:ids){
        	em.remove(em.getReference(this.entityClass,id));
        }
	}
	public void delete(Serializable id) {
		em.remove(em.getReference(this.entityClass,id));
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public T find(Serializable id) {
		return em.find(entityClass,id);
	}
    public void update(T entity) {
		em.merge(entity);
	}
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int firstIndex, int viewPageRecords, LinkedHashMap<String, String> orderby) {
		return getScrollData(firstIndex,viewPageRecords,null,null,orderby);
	}
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int firstIndex, int viewPageRecords, String wherejpql, Object[] queryParams) {
		return getScrollData(firstIndex,viewPageRecords,wherejpql,queryParams,null);
	}
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int firstIndex, int viewPageRecords) {
		return getScrollData(firstIndex,viewPageRecords,null,null,null);
	}
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData() {
		return getScrollData(-1, -1);
	}
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(String wherejpql,Object[] params,LinkedHashMap<String, String> orderby){
		return getScrollData(-1,-1,wherejpql,params,orderby);
	}
	 
	@SuppressWarnings("unchecked")
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int firstIndex, int viewPageRecords
			, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby) {
		QueryResult<T> qr = new QueryResult<T>();
		String entityname = getEntityName(this.entityClass);
		Query query = em.createQuery("select o from "+ entityname+ " o "+(wherejpql==null || "".equals(wherejpql.trim())? "": "where "+ wherejpql)+ buildOrderby(orderby));
		setQueryParams(query, queryParams);
		if(firstIndex!=-1 && viewPageRecords!=-1) query.setFirstResult(firstIndex).setMaxResults(viewPageRecords);
		qr.setResultlist(query.getResultList());
		query = em.createQuery("select count("+ getCountField(this.entityClass)+ ") from "+ entityname+ " o "+(wherejpql==null || "".equals(wherejpql.trim())? "": "where "+ wherejpql));
		setQueryParams(query, queryParams);
		qr.setTotalrecord((Long)query.getSingleResult());
		return qr;
	}
	/**
	 * 组装JPQL语句
	 * @param query
	 * @param queryParams
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	protected static void setQueryParams(Query query, Object[] queryParams){
		if(queryParams!=null && queryParams.length>0){
			for(int i=0; i<queryParams.length; i++){
				query.setParameter(i+1, queryParams[i]);
			}
		}
	}
	/**
	 * 组装order by语句
	 * @param orderby
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	protected static String buildOrderby(LinkedHashMap<String, String> orderby){
		StringBuffer orderbyql = new StringBuffer("");
		if(orderby!=null && orderby.size()>0){
			orderbyql.append(" order by ");
			for(String key : orderby.keySet()){
				orderbyql.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length()-1);
		}
		return orderbyql.toString();
	}
	/**
	 * 获取实体的名称
	 * @param <E>
	 * @param clazz 实体类
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	protected static <E> String getEntityName(Class<E> clazz){
		String entityname = clazz.getSimpleName();
		Entity entity = clazz.getAnnotation(Entity.class);
		if(entity.name()!=null && !"".equals(entity.name())){
			entityname = entity.name();
		}
		return entityname;
	}
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	protected static <E> String getCountField(Class<E> clazz){
		String out = "o";
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for(PropertyDescriptor propertydesc : propertyDescriptors){
				Method method = propertydesc.getReadMethod();
				if(method!=null && method.isAnnotationPresent(EmbeddedId.class)){					
					PropertyDescriptor[] ps = Introspector.getBeanInfo(propertydesc.getPropertyType()).getPropertyDescriptors();
					out = "o."+ propertydesc.getName()+ "." + (!ps[1].getName().equals("class")? ps[1].getName(): ps[0].getName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return out;
	}
}
