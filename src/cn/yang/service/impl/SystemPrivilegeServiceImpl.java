package cn.yang.service.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.yang.bean.QueryResult;
import cn.yang.dao.DAOSupport;
import cn.yang.domain.SystemPrivilege;
import cn.yang.service.SystemPrivilegeService;

@Service
@Transactional
public class SystemPrivilegeServiceImpl extends DAOSupport<SystemPrivilege> implements
		SystemPrivilegeService {
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<SystemPrivilege> getSystemPrivilegeByIdList(Serializable[] systemPrivilegeIdList) {
		StringBuffer idRange=new StringBuffer("");
		for(Serializable id:systemPrivilegeIdList){
			idRange.append(id).append(",");
		}
		idRange.deleteCharAt(idRange.length()-1);
       	String jpql="select o  from SystemPrivilege o where id in("+idRange.toString()+")";
       	
       	Query query=em.createQuery(jpql);
       	
		return query.getResultList();
	}

}
