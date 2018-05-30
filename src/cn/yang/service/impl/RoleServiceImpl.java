package cn.yang.service.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yang.dao.DAOSupport;
import cn.yang.domain.Role;
import cn.yang.domain.SystemPrivilege;
import cn.yang.domain.User;
import cn.yang.service.RoleService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class RoleServiceImpl extends DAOSupport<Role> implements RoleService {

	@Override
	public void delete(Serializable id) {
		Role role=find(id);
		Set<User> users=role.getUsers();
		
		if(users!=null && users.size()>0){
			throw new RuntimeException("有用户属于此角色，不能被删除");
		}
        super.delete(id);
	}

	@Override
	public List<Role> getByIdList(Serializable... ids) {
		StringBuffer idRange=new StringBuffer("");
		for(Serializable id:ids){
			idRange.append(id).append(",");
		}
		idRange.deleteCharAt(idRange.length()-1);
       	String jpql="select o  from Role o where id in("+idRange.toString()+")";
       	
       	Query query=em.createQuery(jpql);
       	
		return query.getResultList();
	}

	@Override
	public List<Role> getDefaultRoles() {
       	String jpql="select o  from Role o where o.defaultForNewUser=true";
       	Query query=em.createQuery(jpql);
		return query.getResultList();	
	}
}
