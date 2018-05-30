package cn.yang.web.action.front.forum;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.yang.bean.ThemeContainer;
import cn.yang.domain.Forum;
import cn.yang.service.ForumService;
import cn.yang.service.ThemeService;
import cn.yang.web.formbean.ForumForm;

@Controller("/ajax/forum/theme/message")
public class AjaxGetThemeMessage extends Action {

	@Resource(name="forumServiceImpl")
	private ForumService forumService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForumForm formbean=(ForumForm)form;
		Integer forumId=formbean.getId();
		Set<Forum> forums=forumService.getLastChildForums(forumId);
		ThemeContainer container=forumService.getThemeCountOfLastForms(forums);
		
		request.setAttribute("container", container);
		return mapping.findForward("themeMessage");
	}
}
