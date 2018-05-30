package cn.yang.web.action.reply;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;

import cn.yang.annotation.Permission;
import cn.yang.constant.ControlCenterConstants;
import cn.yang.domain.Reply;
import cn.yang.domain.Theme;
import cn.yang.domain.User;
import cn.yang.service.ReplyService;
import cn.yang.service.UserService;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.action.base.ManageActionBase;
import cn.yang.web.formbean.ReplyForm;

@Controller("/control/reply/manage")
@Permission(resource = "System", action = "Manage")
public class ReplyManageAction extends ManageActionBase {

	@Resource(name="replyServiceImpl")
	private ReplyService replyService;
	
	@Resource(name="userServiceImpl")
	private UserService userService;
	
	
	
	
	/**
	 * 回帖页面
	 */
	public ActionForward showReplyUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
          return mapping.findForward("saveUI");
	}
	/**
	 * 保存回帖
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReplyForm formbean=(ReplyForm)form;
		String content=formbean.getContent();
		
		if(content==null || "".equals(content)) return null;
		
		//处理内容中的"<"和">"号
/*		content=content.replaceAll("<","&lt;");
		content=content.replaceAll(">","&gt;");*/
		
		Integer themeId=formbean.getThemeId();
		
		Reply reply=new Reply();
		String author=formbean.getAuthor();
		if(StringUtils.isBlank(author)) return null;
		User user=userService.find(author);
		reply.setUser(user);
		reply.setContent(content.trim());
		reply.setTheme(new Theme(themeId));

		replyService.save(reply);
		
		//得到发帖子后，这个帖子在第几页，并传到前台
		/*	List<Reply> replys=replyService.getScrollData().getResultlist();
		int totalRecord=replys.size();
		int viewPageRecords=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.FRONT_VIEW_PAGE_RECORDS));
		int totalPage=(int) (totalRecord%viewPageRecords==0?totalRecord/viewPageRecords:totalRecord/viewPageRecords+1);
		
		response.getWriter().write(totalPage);*/
		//更新用户信息
        userService.addScoreAndReplyCount(user, request);
        
		request.setAttribute("message","发表回复成功!");
		request.setAttribute("urladdress","/control/theme/manage?method=showThemeUI&themeId="+themeId);
		return mapping.findForward("message");
	}
	
	
	/**
	 * 编辑回帖页面
	 */
	public ActionForward showEditUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		  ReplyForm formbean=(ReplyForm)form;
		  Integer id=formbean.getReplyId();
		  Reply reply=replyService.find(id);
		  request.setAttribute("reply",reply);
          return mapping.findForward("editUI");
	}
	/**
	 * 编辑回帖
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReplyForm formbean=(ReplyForm)form;
		String content=formbean.getContent();
		
		if(content==null || "".equals(content)) return null;
		
		//处理内容中的"<"和">"号
/*		content=content.replaceAll("<","&lt;");
		content=content.replaceAll(">","&gt;");*/
		
		
		Integer replyId=formbean.getReplyId();
		
		Reply reply=replyService.find(replyId);
		reply.setContent(content.trim());
		replyService.update(reply);
		Integer themeId=reply.getTheme().getId();
		request.setAttribute("message","修改成功!");
		request.setAttribute("urladdress","/control/theme/manage?method=showThemeUI&themeId="+themeId);
		return mapping.findForward("message");
	}
	/**
	 * 删除回帖
	 *
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReplyForm formbean=(ReplyForm)form;
        Integer replyId=formbean.getReplyId();		
    	replyService.delete(replyId);
		
		return null;
	}
	
	/**
	 * 设置回帖内容只有登陆才可见
	 *
	 */
	public ActionForward onlyLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReplyForm formbean=(ReplyForm)form;
        Integer replyId=formbean.getReplyId();		
		
    	replyService.onlyLogin(new Integer[]{replyId},true);
		
		return null;
	}
	
	/**
	 * 设置回帖内容不必登陆才可见
	 *
	 */
	public ActionForward notLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReplyForm formbean=(ReplyForm)form;
        Integer replyId=formbean.getReplyId();		
		
    	replyService.onlyLogin(new Integer[]{replyId},false);
		
		return null;
	}
	/**
	 * 设置回帖前台不可见
	 *
	 */
	public ActionForward hide(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReplyForm formbean=(ReplyForm)form;
        Integer replyId=formbean.getReplyId();		
		
    	replyService.seenByFront(new Integer[]{replyId},false);
		
		return null;
	}
	/**
	 * 设置回帖前台可见
	 *
	 */
	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReplyForm formbean=(ReplyForm)form;
        Integer replyId=formbean.getReplyId();		
		
    	replyService.seenByFront(new Integer[]{replyId},true);
		
		return null;
	}
}
