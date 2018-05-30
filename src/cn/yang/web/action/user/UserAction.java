package cn.yang.web.action.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.springframework.stereotype.Controller;

import cn.yang.constant.ControlCenterConstants;
import cn.yang.constant.WebConstants;
import cn.yang.domain.Role;
import cn.yang.domain.User;
import cn.yang.service.RoleService;
import cn.yang.service.UserService;
import cn.yang.util.PropertiesUtil;
import cn.yang.util.PutImg;
import cn.yang.web.action.base.ActionBase;
import cn.yang.web.formbean.UserForm;
import cn.yang.web.util.MemberAuthenticationUtils;
import cn.yang.web.util.MemberAutoLoginUtils;
import cn.yang.web.util.ReturnPathUtils;



@Controller("/user")
public class UserAction extends ActionBase {
   
	@Resource(name="userServiceImpl")
	private UserService userService;
	

	/** 修改页面 */
	public ActionForward editUserUI(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String returnPath = ReturnPathUtils.getReturnPathDefaultUseRefererHeaderValue(request);
		request.setAttribute("request_returnPath", returnPath);
		
		UserForm formbean=(UserForm)form;
        String loginName=formbean.getLoginName();
        //解决汉字乱码问题
        loginName=new String(loginName.getBytes("ISO8859-1"),"UTF-8");
        
        User user=userService.find(loginName);
        
        formbean.setLoginName(user.getLoginName());
        formbean.setNickname(user.getNickname());
        formbean.setGender(user.getGender());
        formbean.setEmail(user.getEmail());
        formbean.setSignature(user.getSignature());
		
		return mapping.findForward("editUI");
	}
	
	/** 显示用户资料页面 */
	public ActionForward showUserUI(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String returnPath = ReturnPathUtils.getReturnPathDefaultUseRefererHeaderValue(request);
		request.setAttribute("request_returnPath", returnPath);
		
		UserForm formbean=(UserForm)form;
        String loginName=formbean.getLoginName();
        //解决汉字乱码问题
        loginName=new String(loginName.getBytes("ISO8859-1"),"UTF-8");
        
        User user=userService.find(loginName);
        
        formbean.setGender(user.getGender());
        request.setAttribute("user", user);
		
		return mapping.findForward("userUI");
	}

	/** 修改*/
	@SuppressWarnings("deprecation")
	public ActionForward editUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (validateFailed(form, "forEdit", mapping, request)) { // 验证表单
			return mapping.findForward("regUserUI");
		}
		UserForm actionForm = (UserForm) form;

		// 1，添加用户
		User user = userService.find(actionForm.getLoginName());
		user.setNickname(actionForm.getNickname());
		user.setSignature(actionForm.getSignature());
		user.setEmail(actionForm.getEmail());
		user.setGender(actionForm.getGender());
		String rank=actionForm.getRank();
		if(StringUtils.isNotBlank(rank)) user.setRank(rank);
		//userService.update(user); // 保存用户信息
		if (actionForm.getAvatarResource() != null && actionForm.getAvatarResource().getFileSize() > 0) {
			//FormFile file=actionForm.getAvatarResource();
			File file=UserActionHelper.handleAvatar(request, actionForm.getAvatarResource());
			//判断上传的是不是图片
			if(!isImage(request,file.getName())) return mapping.findForward("error");
			InputStream  input=new FileInputStream(file);
			new PutImg().putimg(user.getLoginName(), input);
			input.close();
			//上传后删除文件
			file.delete();
		}
		
		HttpSession session = request.getSession();
		session.setAttribute(WebConstants.SESSION_LOGGED_ON_USER, user);

		request.setAttribute("message","修改成功!");
		request.setAttribute("urladdress","/index");
		return mapping.findForward("message");

}

	/** 检测loginName是否已被使用(ajax) */
	public ActionForward checkLoginName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String loginName = request.getParameter("loginName");
		Boolean result = userService.checkLoginName(loginName);

		// 把结果（true/false）写给浏览器
		response.setContentType("text/plain");
		response.getWriter().write(result.toString());
		return null;
	}

	/** 检测nickname是否已被使用(ajax) */
	public ActionForward checkNickname(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String loginName = request.getParameter("nickname");
		Boolean result = userService.checkNickname(loginName);

		// 把结果（true/false）写给浏览器
		response.setContentType("text/plain");
		response.getWriter().write(result.toString());
		return null;
	}
	/** 注册页面 */
	public ActionForward regUserUI(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String returnPath = ReturnPathUtils.getReturnPathDefaultUseRefererHeaderValue(request);
		request.setAttribute("request_returnPath", returnPath);
		
/*		UserForm formbean=(UserForm)form;
		formbean.setLoginName(null);
		formbean.setNickname(null);
		formbean.setPassword(null);*/
		
		return mapping.findForward("regUserUI");
	}
	/** 注册 */
	@SuppressWarnings("deprecation")
	public ActionForward regUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (validateFailed(form, "forRegister", mapping, request)) { // 验证表单
			return mapping.findForward("regUserUI");
		}
		UserForm actionForm = (UserForm) form;

		ActionErrors errors = new ActionErrors();
		// 检测登录名是否可用
		if (!userService.checkLoginName(actionForm.getLoginName())) {
			errors.add("loginName", new ActionMessage("登录名【" + actionForm.getLoginName() + "】已被使用，请更换", false));
		}

		// 检测邮箱是否可用
		if (!userService.checkEmail(actionForm.getEmail())) {
			errors.add("email", new ActionMessage("邮箱【" + actionForm.getEmail() + "】已被使用，请更换", false));
		}
		// 
		if (errors.size() > 0) {
			addErrors(request, errors);
			return mapping.findForward("regUserUI");
		}

		// 1，添加用户
		User user = new User();
		BeanUtils.copyProperties(user, actionForm);

		userService.save(user); // 保存用户信息
		if (actionForm.getAvatarResource() != null && actionForm.getAvatarResource().getFileSize() > 0) {
			//byte[] bytes=UserActionHelper.handleAvatar(request, actionForm.getAvatarResource());
			
			//FormFile file=actionForm.getAvatarResource();
			File file=UserActionHelper.handleAvatar(request, actionForm.getAvatarResource());
			//判断上传的是不是图片
			if(!isImage(request,file.getName())){
			    request.setAttribute("message","请上传png, jpg, gif或者bmp格式的图片!");
				request.setAttribute("urladdress","/index");
				return mapping.findForward("message");
			}
			InputStream  input=new FileInputStream(file);
			new PutImg().putimg(user.getLoginName(), input);
			input.close();
			//上传后删除文件
			file.delete();
		}else{
			String realpath=request.getSession().getServletContext().getRealPath("/img/z_05.jpg");
			File file=new File(realpath);
			InputStream input=new FileInputStream(file);
			new PutImg().putimg(user.getLoginName(), input);
		}


		// 2，登录用户
		MemberAuthenticationUtils.login(request, user);

		// 3，返回到注册前访问的页面
		if (ReturnPathUtils.rediectPreviousPage(request, response)) {
			return null;
		} else {
			return mapping.findForward("index");
		}
	}
/**
 * 	判断上传的是不是图片
 * @param request
 * @param filename
 * @return
 */
private boolean  isImage(HttpServletRequest request,String filename){

if(StringUtils.isNotBlank(filename)){
	//得到文件扩展名如".rar"
	int index=filename.lastIndexOf(".");
	String extension="";
	if(index!=-1){
	extension=filename.substring(index);
	}
	//判断附件的后缀名
	String[] regexp={"jpg","jpeg","bmp","png","gif"};
	boolean allow=false;
	for(String reg:regexp){
		if(extension.toLowerCase().matches("\\."+reg+"$")){
			allow=true;
			break;
		}
	}
	
	if(!allow){
	   return false;
	}
}
return true;
}
	/**
	 * 修改密码页面
	 */
	public ActionForward changePasswordUI(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 只有登录后才可以使用
		if (!MemberAuthenticationUtils.isLoggedOn(request)) {
			return mapping.findForward("index");
		}
		return mapping.findForward("changePasswordUI");
	}

	/**
	 * 修改密码
	 */
	public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 1, 只有登录后才可以使用
		if (!MemberAuthenticationUtils.isLoggedOn(request)) {
			return mapping.findForward("index");
		}

		// 2, 验证表单
		if (validateFailed(form, "forChangePassword", mapping, request)) {
			return mapping.findForward("changePasswordUI");
		}

		// 3, 要是本人登录后才可以修改，所以修改Session中的用户
		UserForm actionForm = (UserForm) form;
		User userInSession = MemberAuthenticationUtils.getLoggedOnUser(request);

		User user = userService.changePassword(userInSession.getLoginName(), actionForm.getPassword());
		MemberAuthenticationUtils.login(request, user);// 更换Session中的旧的状态的User
		return mapping.findForward("index");
	}

	/**
	 * 登陆表单
	 */
	public ActionForward loginUI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String returnPath = ReturnPathUtils.getReturnPathDefaultUseRefererHeaderValue(request);
		request.setAttribute("request_returnPath", returnPath);
		return mapping.findForward("loginUI");
	}

	/**
	 * 登陆
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String returnPath = request.getParameter(WebConstants.PARAMETER_RETURN_PATH);
		request.setAttribute("request_returnPath", returnPath);
		
		if (validateFailed(form, "forLogin", mapping, request)) { // 验证表单
			return mapping.findForward("loginUI");
		}

		UserForm actionForm = (UserForm) form;
		User user = userService.findByLoginNameAndPassword(actionForm.getLoginName(), actionForm.getPassword());
		// 登陆失败1：用户名或密码不正确
		if (user == null) {
			addErrorMessage(request, "loginName", "用户名或密码不正确", false);
			return mapping.findForward("loginUI");
		}

		// 登陆失败2：用户被锁定
		if (user.isLocked()) {
			addErrorMessage(request, "loginName", "用户已被锁定，不能登陆。请与管理理员联系", false);
			return mapping.findForward("loginUI");
		}

		// 1，用户名密码验证通过，登陆用户
		MemberAuthenticationUtils.login(request, user);

		// 2，如果选择了自动登录，就启动自动登录
		if (actionForm.isAutoLogin()) {
			// 1, 发送Cookie并更新User中的autoLoginAuthKey
			// int autoLoginDays = actionForm.getAutoLoginDays();
			// FIXME 默认自动登录时间为30天
			int autoLoginDays =Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.USER_AUTO_LOGIN_DAYS));
			MemberAutoLoginUtils.addAutoLoginCookieAndSetUserAutoLoginAuthKey(user, autoLoginDays, response);
			// 2, 保存到服务器端(数据库中)
			userService.update(user);
		}

		// 3，返回到登录前访问的页面
		if (ReturnPathUtils.rediectPreviousPage(request, response)) {
			return null;
		} else {
			return mapping.findForward("index");
		}
	}

	/**
	 * 注销
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1，删除自动登录的Cookie
		User user = MemberAuthenticationUtils.getLoggedOnUser(request);
		MemberAutoLoginUtils.delAutoLoginCookieAndClearUserAutoLoginAuthKey(response, user);
		// 2，注销
		MemberAuthenticationUtils.logout(request);

		// 3，返回到注册前访问的页面
		String returnPath = ReturnPathUtils.getReturnPathDefaultUseRefererHeaderValue(request);
		if (ReturnPathUtils.rediectPreviousPage(request, response, returnPath)) {
			return null;
		} else {
			return mapping.findForward("index");
		}
	}

	/**
	 * 查看用户头像
	 */
	public ActionForward showAvatar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserForm actionForm = (UserForm) form;
		String loginName=actionForm.getLoginName();
        //解决汉字乱码问题
        loginName=new String(loginName.getBytes("ISO8859-1"),"UTF-8");
		User user = userService.find(loginName);

        if (user.getAvatar() != null) { // 有的用户没有头像
			response.setContentType("image/jpeg");
			byte[] bytes=user.getAvatar();
	       // System.out.println(imagestr);
			response.getOutputStream().write(bytes);
			
			response.getOutputStream().flush();
		}
		return null;
	}

}
