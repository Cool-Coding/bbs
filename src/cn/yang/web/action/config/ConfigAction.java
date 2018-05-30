package cn.yang.web.action.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.yang.annotation.Permission;
import cn.yang.constant.ControlCenterConstants;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.action.base.ManageActionBase;
import cn.yang.web.formbean.ConfigForm;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 *2011-4-21
 * 
 */
@Controller("/manage/config")
@Permission(resource = "System", action = "Manage")
public class ConfigAction extends ManageActionBase {


	//显示配置页面
	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ConfigForm formbean=(ConfigForm)form;
		formbean.setAttachment_count(PropertiesUtil.get(ControlCenterConstants.ATTACHMENT_COUNT));
		formbean.setAttachment_delete_for_user(Boolean.parseBoolean(PropertiesUtil.get(ControlCenterConstants.ATTACHMENT_COUNT)));
		formbean.setAttachment_size(PropertiesUtil.get(ControlCenterConstants.ATTACHMENT_SIZE));
		formbean.setDisableAllChildren_forum(Boolean.parseBoolean(PropertiesUtil.get(ControlCenterConstants.DISABLE_ALLCHILDREN_FORUM)));
		formbean.setEnableAllChildren_forum(Boolean.parseBoolean(PropertiesUtil.get(ControlCenterConstants.ENABLE_ALLCHILDREN_FORUM)));
		formbean.setFront_replyCount_perPage(PropertiesUtil.get(ControlCenterConstants.FRONT_REPLYCOUNT_PERPAGE));
		formbean.setFront_score_per_reply(PropertiesUtil.get(ControlCenterConstants.FRONT_SCORE_PER_REPLY));
		formbean.setFront_score_per_theme(PropertiesUtil.get(ControlCenterConstants.FRONT_SCORE_PER_THEME));
		formbean.setFront_viewPageCount(PropertiesUtil.get(ControlCenterConstants.FRONT_VIEW_PAGE_COUNT));
		formbean.setFront_viewPageRecords(PropertiesUtil.get(ControlCenterConstants.FRONT_VIEW_PAGE_RECORDS));
		formbean.setReplyCount_perPage(PropertiesUtil.get(ControlCenterConstants.REPLYCOUNT_PERPAGE));
		formbean.setShow_invisible_reply(Boolean.parseBoolean(PropertiesUtil.get(ControlCenterConstants.SHOW_INVISIBLE_REPLY)));
		formbean.setTheme_delete_for_attachment_delete(Boolean.parseBoolean(PropertiesUtil.get(ControlCenterConstants.THEME_DELETE_FOR_ATTACHMENT_DELETE)));
		formbean.setViewPageCount(PropertiesUtil.get(ControlCenterConstants.VIEW_PAGE_COUNT));
		formbean.setViewPageRecords(PropertiesUtil.get(ControlCenterConstants.VIEW_PAGE_RECORDS));
		formbean.setViewPageRecordsCols_simpleForum(PropertiesUtil.get(ControlCenterConstants.VIEW_SIMPLEFORUM_PAGE_RECORDS_COLS));
		formbean.setAvatar_max_width(PropertiesUtil.get(ControlCenterConstants.AVATAR_MAX_WIDTH));
		formbean.setAvatar_max_height(PropertiesUtil.get(ControlCenterConstants.AVATAR_MAX_HEIGHT));
		
		return mapping.findForward("configUI");
	}
	
	//保存配置
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ConfigForm formbean=(ConfigForm)form;
		
		//禁用父版块时是否禁用所有子版块
		String disableAllChildren_forum=String.valueOf(formbean.isDisableAllChildren_forum());
		if(disableAllChildren_forum != null ) PropertiesUtil.save(ControlCenterConstants.DISABLE_ALLCHILDREN_FORUM, disableAllChildren_forum);
		//启用父版块时是否启用所有子版块
		String enableAllChildren_forum=String.valueOf(formbean.isEnableAllChildren_forum());
		if(enableAllChildren_forum != null ) PropertiesUtil.save(ControlCenterConstants.ENABLE_ALLCHILDREN_FORUM, enableAllChildren_forum);
		//简单版块列表的列数
		String viewPageRecordsCols_simpleForum=formbean.getViewPageRecordsCols_simpleForum();
		if(viewPageRecordsCols_simpleForum != null ) PropertiesUtil.save(ControlCenterConstants.VIEW_SIMPLEFORUM_PAGE_RECORDS_COLS, viewPageRecordsCols_simpleForum);
		//每页所显示的记录数(版块、帖子列表)
		String viewPageRecords=formbean.getViewPageRecords();
		if(viewPageRecords != null ) PropertiesUtil.save(ControlCenterConstants.VIEW_PAGE_RECORDS, viewPageRecords);	
		// 回帖列表页面显示的回帖数
		String replyCount_perPage=formbean.getReplyCount_perPage();
		if(replyCount_perPage != null ) PropertiesUtil.save(ControlCenterConstants.REPLYCOUNT_PERPAGE, replyCount_perPage);		
		//每页显示页码数
		String viewPageCount=formbean.getViewPageCount();
		if(viewPageCount != null ) PropertiesUtil.save(ControlCenterConstants.VIEW_PAGE_COUNT, viewPageCount);		
		//显示回帖时是否将不可见的回帖显示出来
		String show_invisible_reply=String.valueOf(formbean.isShow_invisible_reply());
		if(show_invisible_reply != null ) PropertiesUtil.save(ControlCenterConstants.SHOW_INVISIBLE_REPLY, show_invisible_reply);		
		
		//前台
		//每页所显示的记录数(版块、帖子列表)
		String front_viewPageRecords=formbean.getFront_viewPageRecords();
		if(front_viewPageRecords != null ) PropertiesUtil.save(ControlCenterConstants.FRONT_VIEW_PAGE_RECORDS, front_viewPageRecords);		
		//每页显示页码数
		String front_viewPageCount=formbean.getFront_viewPageCount();
		if(front_viewPageCount != null ) PropertiesUtil.save(ControlCenterConstants.FRONT_VIEW_PAGE_COUNT, front_viewPageCount);		
		//回帖列表页面显示的回帖数
		String front_replyCount_perPage=formbean.getFront_replyCount_perPage();
		if(front_replyCount_perPage != null ) PropertiesUtil.save(ControlCenterConstants.FRONT_REPLYCOUNT_PERPAGE, front_replyCount_perPage);		
		//会员每发表一个帖子增加的分数
		String front_score_per_theme=formbean.getFront_score_per_theme();
		if(front_score_per_theme != null ) PropertiesUtil.save(ControlCenterConstants.FRONT_SCORE_PER_THEME, front_score_per_theme);		
		//会员每发表一个回帖增加的分数
		String front_score_per_reply=formbean.getFront_score_per_reply();
		if(front_score_per_reply != null ) PropertiesUtil.save(ControlCenterConstants.FRONT_SCORE_PER_REPLY, front_score_per_reply);		
		
		//通用配置
		//会员图象的最大宽度
		String avatar_max_width=formbean.getAvatar_max_width();
		if(avatar_max_width != null ) PropertiesUtil.save(ControlCenterConstants.AVATAR_MAX_WIDTH, avatar_max_width);		
		//会员图象的最大高度
		String avatar_max_height=formbean.getAvatar_max_height();
		if(avatar_max_height != null ) PropertiesUtil.save(ControlCenterConstants.AVATAR_MAX_HEIGHT, avatar_max_height);		
		//附件允许上传的大小
		String attachment_size=formbean.getAttachment_size();
		if(attachment_size != null ) PropertiesUtil.save(ControlCenterConstants.ATTACHMENT_SIZE, attachment_size);		
		//允许用户上传附件的个数
		String attachment_count=formbean.getAttachment_count();
		if(attachment_count != null ) PropertiesUtil.save(ControlCenterConstants.ATTACHMENT_COUNT, attachment_count);		
		//删除帖子时是否也删除相应的附件
		String theme_delete_for_attachment_delete=String.valueOf(formbean.isTheme_delete_for_attachment_delete());
		if(theme_delete_for_attachment_delete != null ) PropertiesUtil.save(ControlCenterConstants.THEME_DELETE_FOR_ATTACHMENT_DELETE, theme_delete_for_attachment_delete);		
		//用户上传附件之后，删除时是否进行物理删除
		String attachment_delete_for_user=String.valueOf(formbean.isAttachment_delete_for_user());
		if(attachment_delete_for_user != null ) PropertiesUtil.save(ControlCenterConstants.ATTACHMENT_DELETE_FOR_USER, attachment_delete_for_user);		
		
		PropertiesUtil.close();
		
		request.setAttribute("message","保存配置成功!");
		request.setAttribute("urladdress","/manage/config?method=show");
		return mapping.findForward("message");
	}
}
