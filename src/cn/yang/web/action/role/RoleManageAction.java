package cn.yang.web.action.role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.yang.annotation.Permission;
import cn.yang.domain.Role;
import cn.yang.domain.SystemPrivilege;
import cn.yang.service.RoleService;
import cn.yang.service.SystemPrivilegeService;
import cn.yang.web.action.base.ManageActionBase;
import cn.yang.web.formbean.RoleForm;
import cn.yang.web.util.RequestUtils;


@Controller("/manage/role")
@Permission(resource = "System", action = "Manage")
public class RoleManageAction extends ManageActionBase {

	@Resource(name="roleServiceImpl")
	private RoleService roleService;
	
	@Resource(name="systemPrivilegeServiceImpl")
	private SystemPrivilegeService systemPrivilegeService;
	
	/** 列表 */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Role> roleList = roleService.getScrollData().getResultlist();
		request.setAttribute("request_list", roleList);
		return mapping.findForward("list");
	}

	/**
	 * 添加页面
	 */
	public ActionForward addUI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<SystemPrivilege> systemPrivilegeList = systemPrivilegeService.getScrollData().getResultlist();
		request.setAttribute("request_systemPrivilegeList", systemPrivilegeList);
		return mapping.findForward("saveUI");
	}

	/**
	 * 添加
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (validateFailed(form, mapping, request)) { // validate form bean
			return mapping.findForward("saveUI");
		}

		RoleForm actionForm = (RoleForm) form;
		Role role = new Role();
        Set<SystemPrivilege> privileges=new HashSet<SystemPrivilege>();
		List<SystemPrivilege> systemPrivileges = systemPrivilegeService.getSystemPrivilegeByIdList(actionForm.getSystemPrivilegeIdList());
		role.setName(actionForm.getName());
		role.setDescription(actionForm.getDescription());
		privileges.addAll(systemPrivileges);
		
		role.setSystemPrivileges(privileges);

		roleService.save(role);
		return mapping.findForward("toList");
	}

	/**
	 * 修改页面
	 */
	public ActionForward editUI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1, systemPrivilegeList
		List<SystemPrivilege> systemPrivilegeList =systemPrivilegeService.getScrollData().getResultlist();
		request.setAttribute("request_systemPrivilegeList", systemPrivilegeList);

		// 2, actionForm
		RoleForm actionForm = (RoleForm) form;
		Integer id=actionForm.getId();
		if(id==null ||  id<=0) return null;
		Role role = roleService.find(id);

		actionForm.setName(role.getName());
		actionForm.setDescription(role.getDescription());
		// systemPrivileges --> systemPrivilegeIdList
		Integer[] systemPrivilegeIdList = new Integer[role.getSystemPrivileges().size()];
		int index = 0;
		for (SystemPrivilege sp : role.getSystemPrivileges()) {
			systemPrivilegeIdList[index++] = sp.getId();
		}
		actionForm.setSystemPrivilegeIdList(systemPrivilegeIdList);

		return mapping.findForward("saveUI");
	}

	/**
	 * 修改
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if (validateFailed(form, mapping, request)) { // validate form bean
			return mapping.findForward("saveUI");
		}

		RoleForm actionForm = (RoleForm) form;
		Integer id=actionForm.getId();
		if(id==null ||  id<=0) return null;
		Role role = roleService.find(id);

		List<SystemPrivilege> systemPrivileges = systemPrivilegeService.getSystemPrivilegeByIdList(actionForm.getSystemPrivilegeIdList());
		role.setName(actionForm.getName());
		role.setDescription(actionForm.getName());
        Set<SystemPrivilege> privileges=new HashSet<SystemPrivilege>();
		privileges.addAll(systemPrivileges);
		
		role.setSystemPrivileges(privileges);

		roleService.update(role);
		return mapping.findForward("toList");
	}

	/**
	 * 删除
	 */
	public ActionForward del(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		RoleForm actionForm=(RoleForm)form;
		Integer id=actionForm.getId();
		if(id==null ||  id<=0) return null;
		try {
			roleService.delete(id);
		} catch (Exception e) { // 如有异常，代表分类非空（含有版面），不可被删除
			addErrorMessage(request, "delete", e.getMessage(), false);
			ActionForward af = mapping.findForward("toList");
			return new ActionForward(af.getPath(), false); // 要转发
		}
		return mapping.findForward("toList");
	}

	/** 设置是否做为新注册用户的默认角色 */
	public ActionForward setDefault(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		RoleForm actionForm=(RoleForm)form;
		Integer id=actionForm.getId();
		if(id==null ||  id<=0) return null;
		boolean defaultForNewUser = RequestUtils.getBoolParam(request, "default");
		Role role = roleService.find(id);

		role.setDefaultForNewUser(defaultForNewUser);
		roleService.update(role);
		return mapping.findForward("toList");
	}
}
