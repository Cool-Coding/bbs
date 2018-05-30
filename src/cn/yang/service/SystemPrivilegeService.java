package cn.yang.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import cn.yang.dao.DAO;
import cn.yang.domain.SystemPrivilege;

public interface SystemPrivilegeService extends DAO<SystemPrivilege> {

	/**
	 * 获取指定id的系统权限
	 * @param systemPrivilegeIdList
	 * @return
	 */
	List<SystemPrivilege> getSystemPrivilegeByIdList(Serializable[] systemPrivilegeIdList);
}
