<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.io.File,java.util.Random,cn.yang.util.GetUsersCount" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<%
String contextpath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextpath;
%>
<%
GetUsersCount userUtil=new GetUsersCount();
int userCount=userUtil.getUsersCount();

 %>
<TITLE>校园论坛-大学生的温馨家园</TITLE>
<META content=zh-cn http-equiv=Content-Language>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=keywords content=BBS>
<META name=description content=BBS>
<META name=author content=杨光永>
<LINK rel=stylesheet type=text/css 
href="<%=basePath%>/css/preindex.css">
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
<%
 String path=request.getSession().getServletContext().getRealPath("/img/index");
 File file=new File(path);
 if(file.exists()){
   String[] files=file.list();
   int filecount=files.length;
   if(files!=null && filecount>0){
   Random rand=new Random();
   int index=rand.nextInt(filecount);
   String fileName=files[index];
   request.setAttribute("imageName",fileName);
   }
 }
%>
<script type="text/javascript">
$(function(){
 $("input").focus(function(){
  $(this).addClass("border");
 }).focusout(function(){
  $(this).removeClass("border");
 });
});

</script>
</head>

  <body>
<DIV id=wrap>
<DIV id=preImg>
<A href="<%=basePath%>/index.do"><IMG 
alt=点击进入校园BBS论坛 
src="<%=basePath%>/img/index/${imageName}"></A></DIV>
<DIV id=login>
<FORM id=f_login method=post action="<%=basePath%>/user.do?method=login"><SPAN>用户名:</SPAN><INPUT id=id 
onmouseover="this.focus();" size=10 name="loginName" type="text"/><SPAN>密码:</SPAN><INPUT id=pwd onmouseover=this.focus() size=10 type=password name="password" />
<INPUT id=b_login class=submit value=登录 type=submit><INPUT id=b_guest class=submit value=游客 type=button onclick="window.open('<%=basePath%>/index.do','_self')"><INPUT id=b_reg class=submit value=注册 type=button onclick="window.open('<%=basePath%>/user.do?method=regUserUI','_self')"></FORM></DIV>
<DIV id=b_adv>
</DIV>
</DIV>
<DIV id=footer>
<P class=footer_message>当前论坛上总共有注册用户<SPAN 
class=c-user><%=userCount%></SPAN>人<BR>powered by 杨光永<br/>&copy;2011.all rights 
reserved</P></DIV>
  </body>
</html>
