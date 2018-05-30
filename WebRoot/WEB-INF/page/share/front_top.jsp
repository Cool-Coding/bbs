<%@ page pageEncoding="utf-8" language="java" %>
<%@ page import="java.io.File,java.util.Random" %>
<%
String contextpath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextpath;
%>
<%
 String path=request.getSession().getServletContext().getRealPath("/img/banner");
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
<style type="text/css">
<!--
 .head{
clear:both;
/*border:solid 1px #ccc;*/
text-align:center;
position:relative;overflow:auto;height:auto;
/*background: transparent url('<%=basePath%>/img/banner/${imageName}') no-repeat scroll 0 0;*/
}
-->
</style>

<div class="head" id="head">
<img src="<%=basePath%>/img/banner/${imageName}" width="1000"/>
</div>
