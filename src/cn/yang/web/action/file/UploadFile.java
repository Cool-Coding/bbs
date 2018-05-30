package cn.yang.web.action.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.stereotype.Controller;

import cn.yang.constant.ControlCenterConstants;
import cn.yang.domain.Attachment;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.formbean.FileForm;

@Controller("/upload/file")
/**
 * 此Action的作用就是负责接收附件并将附件保存到物理硬盘上，而无需将其保存到表中，
 * 将保存工作交到附件所属的对象保存。
 */
public class UploadFile extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FileForm formbean=(FileForm)form;
		FormFile file=formbean.getAttachment();
		//得到上传文件名并生成新文件名
		if(file!=null){
		String filename=file.getFileName();
		
		//得到文件扩展名如".rar"
		int index=filename.lastIndexOf(".");
		String extension="";
		if(index!=-1){
		extension=filename.substring(index);
		}
		//判断附件的后缀名
		String[] regexp={"jpg","jpeg","bmp","png","gif","rar","zip", "tar", "gz", "jar", "war", "bz2", "7z", "pdf","swf"};
		boolean allow=false;
		for(String reg:regexp){
			if(extension.toLowerCase().matches("\\."+reg+"$")){
				allow=true;
				break;
			}
		}
		
		if(!allow){
		   request.setAttribute("message","如果您上传图片，请上传png, jpg, gif或者bmp格式的图片\n如果您上传附件，请先压缩再上传");
		   return mapping.findForward("error");
		}
		//判断附件的大小
		String size_attach=PropertiesUtil.get(ControlCenterConstants.ATTACHMENT_SIZE);
		if(file.getFileSize()>Integer.parseInt(size_attach)*1048576){
	    request.setAttribute("message","您上传的附件过大，请重新上传!");
	    return mapping.findForward("error");
		}
		//得到新文件名
		String newfilename=UUID.randomUUID()+extension;
		//得到附件的字节输入流
		InputStream filein=file.getInputStream();
		//得到保存路径
		String path=request.getSession().getServletContext().getRealPath("/upload/"+formbean.getOwner());
		File saveFilePath=new File(path);
		if(!saveFilePath.exists()){//如果保存的目标路径不存在，则创建
			saveFilePath.mkdirs();
		}
		//得到最终要保存的文件对象
		File saveFile=new File(saveFilePath.getPath(),newfilename);
		//保存
		OutputStream fileout=new FileOutputStream(saveFile);
		int by=-1;
		while((by=filein.read())!=-1){
			fileout.write(by);
		}
		//保存完毕，关系输入输出流
		filein.close();
		fileout.close();
		
		Attachment attach=new Attachment();
		attach.setRealname(filename);
		attach.setByname(newfilename);
		Float size=file.getFileSize()/1024.0f;
		attach.setSize((int)(size*100)/100f);
		request.setAttribute("attachment", attach);
		return mapping.findForward("result");
		}else{
		    request.setAttribute("message","上传出错，请重新上传!");
		    return mapping.findForward("error");	
		}
	}
}
