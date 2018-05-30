package cn.yang.web.action.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.yang.annotation.Permission;
import cn.yang.bean.QueryResult;
import cn.yang.constant.ControlCenterConstants;
import cn.yang.domain.Role;
import cn.yang.domain.User;
import cn.yang.service.RoleService;
import cn.yang.service.UserService;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.action.base.ManageActionBase;
import cn.yang.web.bean.PageView;
import cn.yang.web.formbean.UserForm;
import cn.yang.web.util.RequestUtils;



@Controller("/manage/user")
@Permission(resource = "System", action = "Manage")
public class UserManageAction extends ManageActionBase {
    
	@Resource(name="roleServiceImpl")
	private RoleService roleService;
	@Resource(name="userServiceImpl")
	private UserService userService;
	/** 用户列表 */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1, roleList
		List<Role> roleList = roleService.getScrollData().getResultlist();
		request.setAttribute("request_roleList", roleList);

		// 2, pageView
		//每页显示的记录数(默认为10条)
		int viewPageRecords=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.VIEW_PAGE_RECORDS));
		/*分页数据存放PageView中*/
		PageView<User> pageView=null;
		//查询结果存放于QueryResult中
		QueryResult<User> users=null;
		
		UserForm formbean=(UserForm)form;
		//得到当前页码
		int currentPage=formbean.getPage();
		pageView=new PageView<User>(viewPageRecords,currentPage);
		//==============查询===================
		StringBuffer wherejpql=new StringBuffer("1=1");
		List<Object> params=new ArrayList<Object>();

			//按用户名称进行模糊查询
			if(formbean.getLoginName()!=null && StringUtils.isNotBlank(formbean.getLoginName())) {
				String loginName=formbean.getLoginName();
		        //解决汉字乱码问题
				//loginName=new String(loginName.getBytes("ISO8859-1"),"UTF-8");不能使用，因为此处使用的是form,post方式传递的loginName
				
				wherejpql.append(" and o.loginName like ?").append(params.size()+1);
				params.add("%"+loginName.trim()+"%");
			}
			
			//按用户呢称进行模糊查询
			if(formbean.getNickname()!=null && StringUtils.isNotBlank(formbean.getNickname())) {
				wherejpql.append(" and o.loginName like ?").append(params.size()+1);
				params.add("%"+formbean.getNickname().trim()+"%");
			}
		
			if(formbean.getLocked()!=null && StringUtils.isNotBlank(formbean.getLocked())){
			wherejpql.append(" and o.locked=?").append(params.size()+1);
			//按用户是否被锁进行查询
			boolean locked = RequestUtils.getBoolParam(request, "locked");
			params.add(locked);
			}
		 //按角色进行查询
        Integer roleId=formbean.getRoleId();
		if(roleId!=null && roleId>0){
		wherejpql.append(" and ?").append(params.size()+1).append(" member of o.roles");
		Role role=roleService.find(roleId);
		params.add(role);	
		}
		
		//==============排序=====================
		LinkedHashMap<String,String> orderby=new LinkedHashMap<String,String>();
		orderby.put("locked","desc");
		orderby.put("registrationTime","desc");
		
		users=userService.getScrollData(pageView.getFirstIndex(),pageView.getViewPageRecords(),wherejpql.toString(),params.toArray(),orderby);
		
		//根据角色进行查询
/*		String roleId=formbean.getRoleId();
		if(roleId!=null && !"".equals(roleId.trim())){
		Integer roleIdInt=Integer.parseInt(roleId);
		List<User> userRecords=users.getResultlist();
		List<User> tempUserRecords=new ArrayList<User>();
		for(User user:userRecords){
			Set<Role> roles=user.getRoles();
			for(Role role:roles){
				if(role.getId()==roleIdInt) {
					tempUserRecords.add(user);
					break;
				}
			}
		}
		users.setResultlist(tempUserRecords);
		}*/
		
		pageView.setQueryResult(users);
		

		request.setAttribute("request_pageView", pageView);
		return mapping.findForward("list");
	}

	/**
	 * 修改用户所属的角色页面
	 */
	public ActionForward selectRolesUI(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserForm actionForm = (UserForm) form;
        String loginName=actionForm.getLoginName();
        
        if(StringUtils.isBlank(loginName)) return null;
        
        //解决汉字乱码问题
        loginName=new String(loginName.getBytes("ISO8859-1"),"UTF-8");
        //选择角色页面hidden字段使用
        actionForm.setLoginName(loginName);
        
		// 1, 准备 user
		User user = userService.find(loginName);
		request.setAttribute("request_user", user);

		// 2, 准备 roleList
		List<Role> roleList = roleService.getScrollData().getResultlist();
		request.setAttribute("request_roleList", roleList);

		// 3, 准备 actionForm, 用于在页面中显示
		int index = 0;
		
		Integer[] roleIdList = new Integer[user.getRoles().size()];
		for (Role role : user.getRoles()) {
			roleIdList[index++] = role.getId();
		}
		actionForm.setRoleIdList(roleIdList);

		return mapping.findForward("selectRolesUI");
	}

	/** 修改用户所属的角色 */
	public ActionForward selectRoles(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserForm actionForm = (UserForm) form;
		List<Role> roleList = roleService.getByIdList(actionForm.getRoleIdList());

        String loginName=actionForm.getLoginName();
        if(StringUtils.isBlank(loginName)) return null;
        
    	User user = userService.find(loginName);

    	String rank=actionForm.getRank();
    	if(StringUtils.isNotBlank(rank)) user.setRank(rank);
    	
		user.setRoles(new HashSet<Role>(roleList));
		userService.update(user);

		ActionForward af = mapping.findForward("toList");
		String params = "&pageNum=" + getPageNum(request);
		return new ActionForward(af.getPath() + params, af.getRedirect());
	}

	/** 锁定用户 */
	public ActionForward lock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		String id = request.getParameter("loginName");
		
        //解决汉字乱码问题
		id=new String(id.getBytes("ISO8859-1"),"UTF-8");
		
		userService.lock(id);

		ActionForward af = mapping.findForward("toList");
		String params = "&pageNum=" + getPageNum(request) ;
		return new ActionForward(af.getPath() + params, af.getRedirect());
	}

	/** 解锁用户 */
	public ActionForward unlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("loginName");
		
        //解决汉字乱码问题
		id=new String(id.getBytes("ISO8859-1"),"UTF-8");
		
		userService.unlock(id);

		ActionForward af = mapping.findForward("toList");
		String params = "&pageNum=" + getPageNum(request);
		return new ActionForward(af.getPath() + params, af.getRedirect());
	}
}
