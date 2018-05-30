package cn.yang.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import cn.yang.dao.DAO;
import cn.yang.domain.Role;
import cn.yang.domain.SystemPrivilege;


public interface RoleService extends DAO<Role> {
   /**
    * 根据角色的ID数组得到角色列表集合
    * @param ids
    * @return
    */
	public List<Role>getByIdList(Serializable... ids);
   
	/**
	 * 得到用户注册时默认的角色列表
	 * @return
	 */
	public List<Role> getDefaultRoles();
}
