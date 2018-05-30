package cn.yang.web.action.forum;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;

import cn.yang.annotation.Permission;
import cn.yang.bean.QueryResult;
import cn.yang.constant.ControlCenterConstants;
import cn.yang.domain.Forum;
import cn.yang.domain.Role;
import cn.yang.domain.Theme;
import cn.yang.domain.User;
import cn.yang.service.ForumService;
import cn.yang.service.UserService;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.action.base.ManageActionBase;
import cn.yang.web.bean.PageView;
import cn.yang.web.formbean.ForumForm;
@Controller("/control/forum/manage")
@Permission(resource = "System", action = "Manage")
public class ForumManageAction extends ManageActionBase {

	@Resource(name="forumServiceImpl")
	private ForumService forumService;
	
	@Resource(name="userServiceImpl")
	private UserService userService;
	/**
	 * 显示添加版块页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addForumUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return mapping.findForward("addUI");
	}
	/**
	 * 添加版块
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addForum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForumForm formbean=(ForumForm)form;
		if(formbean.getName()!=null && !"".equals(formbean.getName().trim())){
		
		Forum  forum=new Forum();
		forum.setName(formbean.getName());
		forum.setVisible(formbean.getVisible());
		//若是子版块
        if(formbean.getParent().getId()!=null && formbean.getParent().getId()>0){
        	Forum parent=forumService.find(formbean.getParent().getId());
            Set<Theme> themes=parent.getThemes();
            if(themes!=null && themes.size()>0){
        		request.setAttribute("message","父级版块"+parent.getName()+"下存在帖子<br/>请将帖子移出父版块再添加子版块!");
        		request.setAttribute("urladdress","/control/forum/list?parent.id="+formbean.getParent().getId());
        		return mapping.findForward("message");
            }
        	forum.setParent(parent);//由多的一方来维护关系
        }
        forumService.save(forum);
		request.setAttribute("message","添加成功!");
		}else
		request.setAttribute("message","添加失败!");
		request.setAttribute("urladdress","/control/forum/list?parent.id="+formbean.getParent().getId());
		return mapping.findForward("message");
	}
	/**
	 * 显示查找版块页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findForumUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return mapping.findForward("findUI");
	}
	/**
	 * 启用版块
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward enable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForumForm formbean=(ForumForm)form;
		if(formbean.getForumIds()!=null){
			String flag_enable=PropertiesUtil.get(ControlCenterConstants.ENABLE_ALLCHILDREN_FORUM);
			/*检查是否配置了启用父版块时启用其所有子版块功能*/
			if("true".equals(flag_enable)){
				/*所有选择版块所有子版块的ID*/
				Set<Serializable> allForumIds=new HashSet<Serializable>();
				Serializable[] ids= formbean.getForumIds();
				/*添加子版块ID到集合中*/
				for(Serializable id:ids){
					Set<Serializable> childIds=forumService.getAllChildForumIds(Integer.parseInt(id.toString()));
				    if(childIds.size()>0){
				    	allForumIds.addAll(childIds);
				    }
				}
				forumService.visibleManager(true,allForumIds.toArray(new Serializable[]{}));
			}else{
				forumService.visibleManager(true,formbean.getForumIds());
			}
		request.setAttribute("message","启用成功!");
		}else{
		request.setAttribute("message","请至少选择一个板块!");
		}
		request.setAttribute("urladdress","/control/forum/list?parent.id="+formbean.getParent().getId());
		return mapping.findForward("message");
	}
	/**
	 * 禁用版块(并禁用其下所有子版块)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * notice:ForumForm中forumIds，我使用的是Serializable[]来接收，此时若强制转为Integer型则会出错如(Integer)id,
	 * 此处id为Serializable，页面报告如下错误:
	 * can't cast java.lang.String to java.lang.Integer,若使用Integer.parseInt(id.toString())则可以。
	 * 若此处不转型，则到DAO型中会报错，因为此时页面传递过来的id为String类型，而实体Bean的id为Integer.
	 */
	public ActionForward disable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForumForm formbean=(ForumForm)form;
		if(formbean.getForumIds()!=null){
		String flag_disable=PropertiesUtil.get(ControlCenterConstants.DISABLE_ALLCHILDREN_FORUM);
		/*检查是否配置了禁用父版块时禁用其所有子版块功能*/
		if("true".equals(flag_disable)){
			/*所有选择版块所有子版块的ID*/
			Set<Serializable> allForumIds=new HashSet<Serializable>();
			Serializable[] ids= formbean.getForumIds();
			/*添加子版块ID到集合中*/
			for(Serializable id:ids){
				Set<Serializable> childIds=forumService.getAllChildForumIds(Integer.parseInt(id.toString()));
			    if(childIds.size()>0){
			    	allForumIds.addAll(childIds);
			    }
			}
			forumService.visibleManager(false,allForumIds.toArray(new Serializable[]{}));
		}else{
			forumService.visibleManager(false,formbean.getForumIds());
		}
		request.setAttribute("message","禁用成功!");
		}else{
			request.setAttribute("message","请至少选择一个版块!");
		}
		request.setAttribute("urladdress","/control/forum/list?parent.id="+formbean.getParent().getId());
		return mapping.findForward("message");
	}
	/**
	 * 显示更新版块信息页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateForumUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForumForm formbean=(ForumForm)form;
		if(formbean.getId()!=null && formbean.getId()>0){
		Forum forum=forumService.find(formbean.getId());
		formbean.setName(forum.getName());
		formbean.setWeight(forum.getWeight());
		formbean.setModerators(forum.getUsers());
		
		if(forum.getParent()!=null)formbean.setParent(forum.getParent());
		else formbean.setParent(new Forum("顶级版块"));
		formbean.setVisible(forum.getVisible());
		}		
		return mapping.findForward("updateUI");
	}
	/**
	 * 更新版块信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateForum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForumForm formbean=(ForumForm)form;
		if(formbean.getName()!=null && !"".equals(formbean.getName().trim())){
		Forum  forum=forumService.find(formbean.getId());
		Forum parent=null;
		if(formbean.getParent().getId()!=null && formbean.getParent().getId()>0){
			parent=forumService.find(formbean.getParent().getId());
			forum.setParent(parent);
		}else{
			forum.setParent(null);
		}
		Integer weight=formbean.getWeight();
		weight=(weight==null || weight<=0) ? 1 : weight;
		
		forum.setName(formbean.getName());
		forum.setVisible(formbean.getVisible());
		forum.setWeight(weight);
		
		//版主
		String[] moderators=formbean.getModerator_name();
		if(moderators!=null && moderators.length>0){
		List<User> users=userService.getUsersByLoginNames(moderators);
		Set<User> set_users=new HashSet<User>();
		set_users.addAll(users);
		forum.setUsers(set_users);
		}else{
			forum.setUsers(null);
		}
		
		//若选择的是禁用，则检测是否配置了禁用其子版块的功能
		if(!formbean.getVisible()) {
			formbean.setForumIds(new Serializable[]{formbean.getId()});
			disable(mapping, formbean, request, response);
		}
		forumService.update(forum);
		request.setAttribute("message","修改成功!");
		}else
		request.setAttribute("message","修改失败!");
		request.setAttribute("urladdress","/control/forum/list?parent.id="+formbean.getParent().getId());
		return mapping.findForward("message");
	}
	
	/**
	 * 显示增加版主页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addModeratorUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForumForm formbean=(ForumForm)form;
		if(formbean.getId()!=null && formbean.getId()>0){
		Forum forum=forumService.find(formbean.getId());
		formbean.setName(forum.getName());
		if(forum.getParent()!=null)formbean.setParent(forum.getParent());
		else formbean.setParent(new Forum("顶级版块"));
		formbean.setVisible(forum.getVisible());
		}		
		return mapping.findForward("addModeratorUI");
	}
	/**
	 * 添加版主
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addModerator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ForumForm formbean=(ForumForm)form;
		String moderator=formbean.getModerator();
		if(moderator==null || "".equals(moderator.trim())){
/*			ActionErrors errors = new ActionErrors();
			errors.add("moderator", new ActionMessage("版主不能为空!", false));
			addErrors(request, errors);
			return mapping.findForward("addModeratorUI");*/
			request.setAttribute("message","版主不能为空!!");
		}
		User user=userService.find(moderator.trim());
		Forum  forum=forumService.find(formbean.getId());
		
		if(user!=null){
        forum.addModerator(user);
		forumService.update(forum);
		request.setAttribute("message","添加成功!<br/><span style='color:red'>notice:确保此用户已具有相应的权限!</span>");
		}else{
			request.setAttribute("message","不存在此用户,请注册后再添加!");
		}
		request.setAttribute("urladdress","/control/forum/list?parent.id="+formbean.getParent().getId());
		return mapping.findForward("message");
	}
}
