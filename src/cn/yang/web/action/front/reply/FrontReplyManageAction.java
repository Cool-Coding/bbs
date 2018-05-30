package cn.yang.web.action.front.reply;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;

import cn.yang.annotation.Permission;
import cn.yang.constant.ControlCenterConstants;
import cn.yang.domain.Forum;
import cn.yang.domain.Reply;
import cn.yang.domain.Theme;
import cn.yang.domain.User;
import cn.yang.service.ForumService;
import cn.yang.service.ReplyService;
import cn.yang.service.ThemeService;
import cn.yang.service.UserService;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.action.base.ManageActionBase;
import cn.yang.web.formbean.ReplyForm;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 *2011-4-6
 * 
 */
@Controller("/reply/manage")
public class FrontReplyManageAction extends ManageActionBase {

	@Resource(name="replyServiceImpl")
	private ReplyService replyService;
	
	@Resource(name="forumServiceImpl")
	private ForumService forumService;
	
    @Resource(name="themeServiceImpl") 
	private ThemeService themeService;
    
	@Resource(name="userServiceImpl")
	private UserService userService;

	/**
	 * 显示回复页面
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @throws Exception
	 */
    @Permission(resource = "Reply", action = "Create")
	public ActionForward showPostUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ReplyForm formbean=(ReplyForm)form;
		Integer themeId=formbean.getThemeId();
		if(themeId==null || themeId<=0) return null;
		
/*		Integer forumId=formbean.getForumId();//得到回复所在帖子所在版块的ID
		if(forumId==null || forumId<=0) return null;*/
		
		//得到回复所在帖子
		Theme theme=themeService.find(themeId);
		
		  /*导航*/
		  /*得到当前帖子所在版块的所有父版块*/
		Forum currentForum=null;
		List<Forum> parents=null;
	    parents=forumService.getAllParentForum(theme.getForum().getId());
		currentForum=parents.get(0);
		 //将得到的上级版块放于request作用域中，以供页面访问
		request.setAttribute("parents",parents);
		request.setAttribute("currentForum", currentForum);
		  //结束
		
		request.setAttribute("theme", theme);
		return mapping.findForward("postUI");
  }	
	/**
	 * 发表回复
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @throws Exception
	 */
	@Permission(resource = "Reply", action = "Create")
	public ActionForward post(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ReplyForm formbean=(ReplyForm)form;
		String content=formbean.getContent();
		
		if(content==null || "".equals(content)) return null;
		
		
		Integer themeId=formbean.getThemeId();
		if(themeId==null || themeId<=0) return null;
		Reply reply=new Reply();
		User user=userService.find(formbean.getAuthor());
		reply.setUser(user);
		reply.setContent(content.trim());
		reply.setTheme(new Theme(themeId));
		replyService.save(reply);
		//更新用户信息
        userService.addScoreAndReplyCount(user, request);
        
/*		request.setAttribute("message","回复成功!");
		request.setAttribute("urladdress","/theme/manage?method=showContent&themeId="+themeId);*/
        
		return showLastReply(mapping,form,request,response);
  }	
	
	/**
	 * 显示编辑页面
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @throws Exception
	 */
    @Permission(resource = "Reply", action = "Update")
	public ActionForward showEditUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ReplyForm formbean=(ReplyForm)form;
		Integer replyId=formbean.getReplyId();
		if(replyId==null || replyId<=0) return null;
	
		//得到回帖
		Reply reply=replyService.find(replyId);
		
		//得到回复所在帖子
		Theme theme=reply.getTheme();

/*		  导航
		  得到当前帖子所在版块的所有父版块
		Forum currentForum=null;
		List<Forum> parents=null;
	    parents=forumService.getAllParentForum(forumId);
		currentForum=parents.get(0);
		 //将得到的上级版块放于request作用域中，以供页面访问
		request.setAttribute("parents",parents);
		request.setAttribute("currentForum", currentForum);
		  //结束
*/		
		  /*导航*/
		  /*得到当前帖子所在版块的所有父版块*/
		Forum currentForum=null;
		List<Forum> parents=null;
	    parents=forumService.getAllParentForum(theme.getForum().getId());
		currentForum=parents.get(0);
		 //将得到的上级版块放于request作用域中，以供页面访问
		request.setAttribute("parents",parents);
		request.setAttribute("currentForum", currentForum);
		  //结束
		
		request.setAttribute("theme", theme);
		request.setAttribute("reply", reply);
		
		return mapping.findForward("editUI");
  }	
    
	/**
	 * 编辑提交回复
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @throws Exception
	 */
	@Permission(resource = "Reply", action = "Update")
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ReplyForm formbean=(ReplyForm)form;
		String content=formbean.getContent();
		if(content==null || "".equals(content)) return null;
		Integer replyId=formbean.getReplyId();
		if(replyId==null || replyId<=0) return null;
		//得到回帖
		Reply reply=replyService.find(replyId);
		reply.setContent(content.trim());
		replyService.update(reply);
        
		Integer themeId=reply.getTheme().getId();
		request.setAttribute("newReply_Page",formbean.getPage());
		request.setAttribute("themeId",themeId);
		
		return mapping.findForward("themeList");
		
 }	
	/**
	 * 显示最后回复页面
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @throws Exception
	 */
    //@Permission(resource = "Reply", action = "Retrieval")
	public ActionForward showLastReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ReplyForm formbean=(ReplyForm)form;
	
		
		Integer themeId=formbean.getThemeId();
		if(themeId==null || themeId<=0) return null;
		
		//得到发帖子后，这个帖子在第几页，并传到前台
		String wherejpql="o.theme.id=? and o.visible=true";
		List<Object> params=new ArrayList<Object>();
		params.add(themeId);
		
		List<Reply> replys=replyService.getScrollData(wherejpql,params.toArray(),null).getResultlist();
		int totalRecord=replys.size();
		int viewPageRecords=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.FRONT_VIEW_PAGE_RECORDS));
		int totalPage=(int) (totalRecord%viewPageRecords==0 ? totalRecord/viewPageRecords : totalRecord/viewPageRecords+1);
		
		request.setAttribute("newReply_Page",totalPage);
		request.setAttribute("themeId",themeId);
		return mapping.findForward("themeList");
  }

	/**
	 * 删除回帖
	 *
	 */
	@Permission(resource = "Reply", action = "Delete")
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReplyForm formbean=(ReplyForm)form;
        Integer replyId=formbean.getReplyId();		
    	replyService.delete(replyId);
    	response.setContentType("text/html;charset=utf-8");
    	response.getWriter().write("删除成功!");
		return null;
	}
	
	/**
	 * 设置回帖内容只有登陆才可见
	 *
	 */
	@Permission(resource = "Reply", action = "Update")
	public ActionForward onlyLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReplyForm formbean=(ReplyForm)form;
        Integer replyId=formbean.getReplyId();		
		
    	replyService.onlyLogin(new Integer[]{replyId},true);
    	
    	response.setContentType("text/html;charset=utf-8");
    	response.getWriter().write("设置成功!");
		return null;
	}
	
	/**
	 * 设置回帖内容不必登陆才可见
	 *
	 */
	@Permission(resource = "Reply", action = "Update")
	public ActionForward notLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReplyForm formbean=(ReplyForm)form;
        Integer replyId=formbean.getReplyId();		
		
    	replyService.onlyLogin(new Integer[]{replyId},false);
		
    	response.setContentType("text/html;charset=utf-8");
    	response.getWriter().write("设置成功!");
		return null;
	}
	/**
	 * 设置回帖前台不可见
	 *
	 */
	@Permission(resource = "Reply", action = "Update")
	public ActionForward hide(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReplyForm formbean=(ReplyForm)form;
        Integer replyId=formbean.getReplyId();		
		
    	replyService.seenByFront(new Integer[]{replyId},false);
		
    	response.setContentType("text/html;charset=utf-8");
    	response.getWriter().write("设置成功!");
		return null;
	}
	/**
	 * 设置回帖前台可见
	 *
	 */
	@Permission(resource = "Reply", action = "Update")
	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReplyForm formbean=(ReplyForm)form;
        Integer replyId=formbean.getReplyId();		
		
    	replyService.seenByFront(new Integer[]{replyId},true);
		
    	response.setContentType("text/html;charset=utf-8");
    	response.getWriter().write("设置成功!");
		return null;
	}
}
