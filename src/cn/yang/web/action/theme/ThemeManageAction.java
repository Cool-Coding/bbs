package cn.yang.web.action.theme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;

import cn.yang.annotation.Permission;
import cn.yang.bbs.exception.ActionException;
import cn.yang.bean.ThemeType;
import cn.yang.constant.ControlCenterConstants;
import cn.yang.domain.Attachment;
import cn.yang.domain.Forum;
import cn.yang.domain.PostType;
import cn.yang.domain.Theme;
import cn.yang.domain.User;
import cn.yang.service.AttachmentService;
import cn.yang.service.ForumService;
import cn.yang.service.ThemeService;
import cn.yang.service.UserService;
import cn.yang.util.FileManager;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.action.base.ManageActionBase;
import cn.yang.web.formbean.ThemeForm;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-12-8
 */
@Controller("/control/theme/manage")
@Permission(resource = "System", action = "Manage")
public class ThemeManageAction extends ManageActionBase {
   
    @Resource(name="themeServiceImpl") 
	private ThemeService themeService;
    
	@Resource(name="forumServiceImpl")
	private ForumService forumService;
	
	@Resource(name="attachmentServiceImpl")
	private AttachmentService attachmentService;
	
	@Resource(name="userServiceImpl")
	private UserService userService;
/*	//定义一个公共变量，用于计算请求第几个附件
	private int attachment_coming=0;
	//帖子附件
	Set<Attachment> attachments=null;*/
	/**
	 * 显示添加帖子页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addThemeUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ThemeForm formbean=(ThemeForm)form;
        //==============得到允许上传附件的大小和数量====================
		String attach_size=PropertiesUtil.get(ControlCenterConstants.ATTACHMENT_SIZE);
		String attach_count=PropertiesUtil.get(ControlCenterConstants.ATTACHMENT_COUNT);
		request.setAttribute("attachment_size",attach_size);
		request.setAttribute("attachment_count",attach_count);
		
		PostType[] themeTypes=PostType.values();
		ThemeType[] types=new ThemeType[themeTypes.length];
		for(int i=0;i<themeTypes.length;i++){
			ThemeType type=new ThemeType();
			type.setName(themeTypes[i].getName());
			type.setValue(themeTypes[i].toString());
			types[i]=type;
		}
		formbean.setThemeTypes(types);
		
		return mapping.findForward("addUI");
	}
	/**
	 * 添加帖子
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ThemeForm formbean=(ThemeForm)form;
		String title=formbean.getTitle();
		String content=formbean.getContent();
		Integer forumId=formbean.getForumId();
    	//找出帖子所对应的版块
        Forum forum=forumService.find(forumId);
		//分别对帖子标题和内容进行非空判断
		if(title==null || "".equals(title.trim())){
			request.setAttribute("message","帖子标题不能为空!");
			request.setAttribute("urladdress","/control/theme/manage?method=addThemeUI");
			return mapping.findForward("message");
		}
		if(content==null || "".equals(content.trim())){
			request.setAttribute("message","帖子内容不能为空!");
			request.setAttribute("urladdress","/control/theme/manage?method=addThemeUI");
			return mapping.findForward("message");
		}
		if(forumId==null || forumId<=0) {
			request.setAttribute("message","没有版块，请添加版块之后再添加帖子!");
			request.setAttribute("urladdress","/control/theme/list");
			return mapping.findForward("message");
		}
		//保存帖子
		User user=userService.find(formbean.getAuthor());
		Theme theme=new Theme();
		theme.setUser(user);
		theme.setTitle(title);
		theme.setContent(content);
		theme.setTop(formbean.getIsTop());
		theme.setType(PostType.valueOf(formbean.getType()));
		

		theme.setForum(forum);
		addAttachments(formbean, theme);
		themeService.save(theme);
		
		//得到增加的分数
        userService.addScoreAndThemeCount(user, request);
		
		request.setAttribute("message","保存成功!");
		request.setAttribute("urladdress","/control/theme/list?forumId="+formbean.getForumId());
		return mapping.findForward("message");
	}
	//添加附件
	private void addAttachments(ThemeForm formbean, Theme theme) {
		
		//添加帖子附件
		String[] attachments=formbean.getAttachments();
		String[] remarks=formbean.getRemarks();
		
	    if(attachments!=null && attachments.length>0){
		for(String attachment:attachments){
			Attachment attach=new Attachment();
			String[] message=attachment.split("\\*");
			attach.setRealname(message[0]);
			attach.setByname(message[1]);
			attach.setSize(Float.parseFloat((message[2])));
			attach.setPath("/upload/theme/"+message[1]);
			//查找接受过来的评论中是否有属于此贴的
		    if(remarks!=null && remarks.length>0){
				for(String remark:remarks){
					if(remark.indexOf(message[1])!=-1){
					    String realRemark=remark.split("\\*")[1];
						attach.setRemark(realRemark);
						break;
					}
				}
		    }
		    attach.setTheme(theme);//由附件来维护与帖子的关系
			theme.addAttachment(attach);//当保存帖子的时候，附件也级联保存。
		}
	  }
	}
	/**
	 * 显示修改帖子页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editThemeUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		    ThemeForm formbean=(ThemeForm)form;
		    Integer id=formbean.getThemeId();
		    Theme theme=themeService.find(id);//根据帖子ID查询得到帖子实体
			Forum forum=theme.getForum();
			formbean.setForumId(forum.getId());
			formbean.setForumName(forum.getName());
			formbean.setType(theme.getType().toString());
			
			//将帖子保存到request作用域
			request.setAttribute("theme",theme);
			
			PostType[] themeTypes=PostType.values();
			ThemeType[] types=new ThemeType[themeTypes.length];
			for(int i=0;i<themeTypes.length;i++){
				ThemeType type=new ThemeType();
				type.setName(themeTypes[i].getName());
				type.setValue(themeTypes[i].toString());
				types[i]=type;
			}
			formbean.setThemeTypes(types);
			
	        //==============得到允许上传附件的大小和数量====================
			String attach_size=PropertiesUtil.get(ControlCenterConstants.ATTACHMENT_SIZE);
			String attach_count=PropertiesUtil.get(ControlCenterConstants.ATTACHMENT_COUNT);
			request.setAttribute("attachment_size",attach_size);
			request.setAttribute("attachment_count",attach_count);

		    return mapping.findForward("editUI");
	}
	/**
	 * 修改帖子
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ThemeForm formbean=(ThemeForm)form;
		String title=formbean.getTitle();
		String content=formbean.getContent();
		Integer forumId=formbean.getForumId();
		//分别对帖子标题和内容进行非空判断
		if(title==null || "".equals(title.trim())){
			request.setAttribute("message","帖子标题不能为空!");
			request.setAttribute("urladdress","/control/theme/manage?method=addThemeUI");
			return mapping.findForward("message");
		}
		if(content==null || "".equals(content.trim())){
			request.setAttribute("message","帖子内容不能为空!");
			request.setAttribute("urladdress","/control/theme/manage?method=addThemeUI");
			return mapping.findForward("message");
		}
		if(forumId==null || forumId<=0) {
			request.setAttribute("message","未知错误!请重新发表帖子");
			request.setAttribute("urladdress","/control/theme/list");
			return mapping.findForward("message");
		}
		//保存帖子
		Theme theme=themeService.find(formbean.getThemeId());
		theme.setUser(userService.find(formbean.getAuthor()));
		theme.setTitle(title);
		theme.setContent(content);
		theme.setTop(formbean.getIsTop());
		theme.setUpdateTime(new Date());
		theme.setType(PostType.valueOf(formbean.getType()));
		//找出帖子所对应的版块
        Forum forum=forumService.find(forumId);
		theme.setForum(forum);
		editAttachments(formbean, theme);
		themeService.update(theme);
		
		request.setAttribute("message","修改成功!");
		request.setAttribute("urladdress","/control/theme/list?forumId="+formbean.getForumId());
		return mapping.findForward("message");
	}
	//修改附件
	private void editAttachments(ThemeForm formbean, Theme theme) {
		
		String[] attachments=formbean.getAttachments();
		String[] remarks=formbean.getRemarks();
		
		//*******************************************//
		//添加帖子附件
	    if(attachments!=null && attachments.length>0){
		for(String attachment:attachments){
			Attachment attach=new Attachment();
			String[] message=attachment.split("\\*");
			attach.setRealname(message[0]);
			attach.setByname(message[1]);
			attach.setSize(Float.parseFloat((message[2])));
			attach.setPath("/upload/theme/"+message[1]);
			//查找接受过来的评论中是否有属于此贴的
		    if(remarks!=null && remarks.length>0){
				for(String remark:remarks){
					if(remark.indexOf(message[1])!=-1){
					    String realRemark=remark.split("\\*")[1];
						attach.setRemark(realRemark);
						break;
					}
				}
		    }
		    attach.setTheme(theme);//由附件来维护与帖子的关系
		    attachmentService.save(attach);//此处记得要首先将附件保存到表中
			theme.addAttachment(attach);//当保存帖子的时候，附件也级联保存。
		}
      }
	    
	  //*******************************************//
		//取得已成在的帖子附件，修改附件评论
		Set<Attachment> attachs=theme.getAttachments();
		String byname=null;
		for(Attachment attachment:attachs){
	        byname=attachment.getByname();
		   //查找接受过来的评论中是否有属于此贴的
	       if(remarks!=null && remarks.length>0){
			for(String remark:remarks){
				if(remark.indexOf(byname)!=-1){
				    String realRemark=remark.split("\\*")[1];
					attachment.setRemark(realRemark);
					attachmentService.update(attachment);
					break;
				}
			}
	    }
	}
   //*******************************************// 
		
	}
	/**
	 * 显示帖子内容页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showThemeUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		    ThemeForm formbean=(ThemeForm)form;
		    Integer themeId=formbean.getThemeId(); 
		    //如果不是由前台点击帖子标题链接得到，则检查是否request作用域中存在themeId.
			if(themeId==null || themeId<=0){
				themeId=(Integer)request.getAttribute("themeId");
			}
		    Theme theme=themeService.find(themeId);//根据帖子ID查询得到帖子实体
			Forum forum=theme.getForum();
			formbean.setForumName(forum.getName());
			
			//将帖子保存到request作用域
			request.setAttribute("theme",theme);

		    return mapping.findForward("showUI");
	}
	/**
	 * 启用帖子
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
        ThemeForm formbean=(ThemeForm)form;
        Integer[] ids=formbean.getThemeIds();
        if(ids!=null && ids.length>0){
        themeService.visibleManager(true,ids);
        request.setAttribute("message","启用成功!");
        }else{
           request.setAttribute("message","请至少选择一个帖子进行操作!");
        }
        request.setAttribute("urladdress","/control/theme/list?forumId="+formbean.getForumId());
		return mapping.findForward("message");
	}
	/**
	 * 禁用帖子
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward disable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        ThemeForm formbean=(ThemeForm)form;
        Integer[] ids=formbean.getThemeIds();
        if(ids!=null && ids.length>0){
        themeService.visibleManager(false,ids);
		request.setAttribute("message","禁用成功!");
        }else{
    	request.setAttribute("message","请至少选择一个帖子进行操作!");
        }
        request.setAttribute("urladdress","/control/theme/list?forumId="+formbean.getForumId());
		return mapping.findForward("message");
	}
	/**
	 * 批量删除帖子
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        ThemeForm formbean=(ThemeForm)form;
        Integer[] ids=formbean.getThemeIds();
        if(ids!=null && ids.length>0){
        deleteAttachments(request, ids);
        themeService.delete(ids);
         //删除附件
		request.setAttribute("message","删除成功!");
        }else{
    	request.setAttribute("message","请至少选择一个帖子进行操作!");
        }
        request.setAttribute("urladdress","/control/theme/list?forumId="+formbean.getForumId());
		return mapping.findForward("message");
	}
	private void deleteAttachments(HttpServletRequest request, Integer[] ids)
			throws FileNotFoundException, IOException {
		for(Integer id:ids){
			//得到此id号的帖子
			Theme theme=themeService.find(id);
			
			//得到此帖子的附件
			Set<Attachment> attachments=theme.getAttachments();
			//如果此帖子有附件
			if(attachments!=null && attachments.size()>0){
			  String path="";
			  File file=null;
			  for(Attachment attachment:attachments){
				path=request.getSession().getServletContext().getRealPath("/upload/theme/"+attachment.getByname());
				file=new File(path);
				//是否真正地删除附件
				String realdelete=PropertiesUtil.get(ControlCenterConstants.THEME_DELETE_FOR_ATTACHMENT_DELETE);
			    if(file.exists()){
				if("false".equals(realdelete)){				
						//得到输出流
						String destpath=request.getSession().getServletContext().getRealPath("/upload/theme/disable/"+attachment.getByname());
						File output=new File(destpath);
                        FileManager.move(file, output);
				}else if("true".equals(realdelete)){
					    FileManager.delete(file);
				}
			   }
			  }
			}
        }
	}
	/**
	 * 批量更改帖子为指定的版块
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward moveThemesToForum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
        ThemeForm formbean=(ThemeForm)form;
        Integer[] ids=formbean.getThemeIds();
        Integer forumId=formbean.getForumId();
        if(forumId!=null && forumId>0 && ids!=null && ids.length>0){
        themeService.moveThemesToForum(ids,forumId);
		request.setAttribute("message","移动帖子成功!");
        }else{
    	request.setAttribute("message","请至少选择一个帖子进行操作!");
        }
        request.setAttribute("urladdress","/control/theme/list?forumId="+formbean.getForumId());
		return mapping.findForward("message");
	}
}
