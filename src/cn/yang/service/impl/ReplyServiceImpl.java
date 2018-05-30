package cn.yang.service.impl;

import java.io.Serializable;

import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yang.dao.DAOSupport;
import cn.yang.domain.Reply;
import cn.yang.service.ReplyService;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 *2011-3-7
 */

@Service
@Transactional
public class ReplyServiceImpl extends DAOSupport<Reply> implements ReplyService {

	@Override
	public void onlyLogin(Integer[] ids,Boolean visible) {
	setIsOrNotVisible(ids,"onlyLogin",visible);
	}

	@Override
	public void seenByFront(Integer[] ids,Boolean visible) {
		setIsOrNotVisible(ids,"visible",visible);		
	}

	/**
	 * 设置回帖中的某个字段可见与不可见
	 * @param id 实体id
	 * @param name 字段名
	 * @param visible 可见或不可见
	 */
	private void setIsOrNotVisible(Integer[] ids,String name, Boolean visible) {

		if(name==null || "".equals(name.trim())) return;
		if(visible==null) return;
		
		StringBuffer idRange=new StringBuffer("");
		for(Serializable id:ids){
			idRange.append(id).append(",");
		}
		idRange.deleteCharAt(idRange.length()-1);
		
       	String jpql="update Article o set o."+name+"=? where id in("+idRange.toString()+")";
       	
       	Query query=em.createQuery(jpql);
       	query.setParameter(1, visible);
       	
       	query.executeUpdate();		
	}

	
}
