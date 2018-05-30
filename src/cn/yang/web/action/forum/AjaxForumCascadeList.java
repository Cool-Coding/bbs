package cn.yang.web.action.forum;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;

import cn.yang.domain.Forum;
import cn.yang.service.ForumService;
import cn.yang.web.formbean.ForumForm;

@Controller("/ajax/forum/cascade/list")
public class AjaxForumCascadeList extends DispatchAction {
    
	@Resource(name="forumServiceImpl")
	private ForumService forumService;
	
	
	public ActionForward getForumCascadeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForumForm formbean=(ForumForm)form;
		
		Integer forumid=formbean.getId();
		if(forumid==null || forumid<=0){
			String wherejpql="o.visible=true and o.parent is null";
			List<Forum> forums=forumService.getScrollData(wherejpql,null,null).getResultlist();
			request.setAttribute("forums",forums);
		}else{
		Forum forum=forumService.find(forumid);
		Set<Forum> children=forum.getChildren();
		request.setAttribute("forums",children);
		request.setAttribute("currentForum",forum);
		Forum parent=forum.getParent();
		if(parent!=null) request.setAttribute("parentforum",parent.getId());
		}
		//System.out.println(request.getParameter("type"));
		request.setAttribute("type",request.getParameter("type"));
		return mapping.findForward("childforums");
	}

	
}
