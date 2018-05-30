package cn.yang.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;

import cn.yang.bean.QueryResult;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-11-5
 * @param <T>
 */
public interface DAO<T> {
    
   /**
    * 保存实体
    * @param entity 实体
    */
	public void save(T entity);
	/**
	 * 更新实体
	 * @param entity 实体
	 */
	public void update(T entity);
	/**
	 * 根据id删除实体
	 * @param id 实体id
	 */
	public void delete(Serializable id);
	/**
	 * 根据id数组删除实体
	 * @param ids 实体id数组
	 */
	public void delete(Serializable... ids);
	/**
	 * 根据id查询实体
	 * @param id
	 * @return 实体对象
	 */
	public T find(Serializable id);
	/**
	 * 根据条件进行查询，获得分布数据
	 * @param firstIndex  第一条记录
	 * @param viewPageRecords   一次查询的记录条数
	 * @param wherejpql  jpql语句
	 * @param queryParams jpql语句所对应的参数
	 * @param orderby 排序语句
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstIndex, int viewPageRecords, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby);
	/**
	 * 根据第一条记录号和记录条数以及条件获得分布数据
	 * @param <T>
	 * @param firstIndex
	 * @param viewPageRecords
	 * @param wherejpql
	 * @param queryParams
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstIndex, int viewPageRecords
			, String wherejpql, Object[] queryParams);
	
	/**
	 * 根据第一条记录号和记录条数以及排序语句获得分布数据
	 * @param <T>
	 * @param firstIndex
	 * @param viewPageRecords
	 * @param orderby
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstIndex, int viewPageRecords
			, LinkedHashMap<String, String> orderby);
	
	/**
	 * 只根据第一条记录和记录条数获得分布数据
	 * @param <T>
	 * @param firstIndex
	 * @param viewPageRecords
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstIndex, int viewPageRecords);
	
	/**
	 * 获得所有数据
	 * @param <T>
	 * @return
	 */
	public QueryResult<T> getScrollData();
	/**
	 * 获取满足条件的但不分页的数据
	 * @param <T>
	 * @param firstIndex
	 * @param viewPageRecords
	 * @param orderby
	 * @return
	 */
	public QueryResult<T> getScrollData(String wherejpql,Object[] params,LinkedHashMap<String, String> orderby);
	
}
