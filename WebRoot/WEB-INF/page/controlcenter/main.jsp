<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>网站后台管理系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

    <link href="<%=basePath%>/css/bbs.css" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript" src="<%=basePath%>/js/getScript.js"></script>
   	<script type="text/javascript">
	<!--
	  getScript("<%=basePath%>/js/jquery-1.4.4.min.js");//先加载JQuery
	  getScript("<%=basePath%>/js/mainjsp.js");
	  //-->
	</script>
    <script language="javascript"><!--
      function getPage(urladdress){
       document.getElementById("content").src=urladdress;
       }
	 
/*	 function getPageHeight(){
	    frame1=document.getElementById("content");
	    frame1.height=frame1.contentWindow.document.documentElement.scrollHeight;
	 }*/
     //-->
     
  </script>
  <style type="text/css">
   .selected{
    background:transparent url('<%=basePath%>/img/background.jpg') no-repeat scroll 0 0;
   }
    .hover{
    background-color:#D1E9FF;
   }
  </style>
  </head>
  <body>
  <div align="center" >
  <div style="width:1000px;">
    <!--#######################################页眉#################################################-->
	<div id="pageTop">
	<jsp:include page="/WEB-INF/page/share/front_top.jsp"></jsp:include>
	</div>
	<div id="middle">
	<!--======================================左边导航栏=============================================-->
		<div style="border:1px solid #A1BBDC;">
	      <ul id="navigation">
	        <!-- getPage方法中参数用引号引起来 -->
	         <li onclick="javascript:getPage('<html:rewrite action='/control/forum/list'/>')">版块管理</li>
	         <li onclick="javascript:getPage('<html:rewrite action='/control/theme/list'/>')">帖子管理</li>
	         <li onclick="javascript:getPage('<html:rewrite action='/manage/user?method=list'/>')">用户管理</li>
	         <li onclick="javascript:getPage('<html:rewrite action='/manage/role?method=list'/>')">角色管理</li>
	         <li onclick="javascript:getPage('<html:rewrite action='/manage/config?method=show'/>')">系统配置</li>
	         <li onclick="window.location.href='<%=basePath%>/index.do'">转到首页</li>
	      </ul>
		</div>
		<!-- ======================================主体内容============================================== -->
	<div>
	   <iframe id="content" frameborder="0" scrolling="no" marginwidth="0" marginheight="0"></iframe>
	</div>
	</div>
	<!--########################################页尾#################################################-->
	<div id="pageBottom">
 <jsp:include page="/WEB-INF/page/share/front_footer.jsp"></jsp:include>
  </div>
</div>
</div>
  </body>
</html>
