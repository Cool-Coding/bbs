package cn.yang.web.action.file;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.yang.annotation.Permission;
import cn.yang.constant.ControlCenterConstants;
import cn.yang.service.AttachmentService;
import cn.yang.util.FileManager;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.formbean.FileForm;


/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2011-1-2
 */
@Controller("/delete/file")
@Permission(resource = "Attachment", action = "Delete")
public class DeleteFile extends Action {
    
	@Resource(name="attachmentServiceImpl")
	private AttachmentService attachmentService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FileForm formbean=(FileForm)form;

		String owner=formbean.getOwner();
		String fileName=formbean.getByname();
		String path=request.getSession().getServletContext().getRealPath("/upload/"+owner+"/"+fileName);
		File file=new File(path);
		//如果物理硬盘上的删除附件为真
		if("true".equals(PropertiesUtil.get(ControlCenterConstants.ATTACHMENT_DELETE_FOR_USER))){
		if(file.exists()) FileManager.delete(file);
		}else{//否则将附件转移到一个diable文件夹中
			if(file.exists()){
				String newPath=request.getSession().getServletContext().getRealPath("/upload/"+owner+"/disable/"+fileName);
				File destFile=new File(newPath);
				FileManager.move(file, destFile);
			}
		}
		//删除附件表中相应的附件
		String wherejpql="o.byname=?1";
		Object[] params=new Object[]{fileName};
		attachmentService.delete(wherejpql, params);
		return mapping.findForward("message");
	}

	
}
