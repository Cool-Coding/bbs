<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
  <head>
    <title>出错</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<style type="text/css">
	 body{
	 margin:0;
	 padding:0;
     background-color:#9ECDE4;
	 }
	</style>
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/uploadResult.js"></script>
    </head>
  
  <body>
   <div style="color:red;font-size:12px;">${message}<a href="javascript:history.go(-1);">返回</a></div>
  </body>
</html>
