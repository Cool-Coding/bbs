<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>附件列表(只显示)</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
	<style type="text/css">
	body{
	margin:0;
    padding:0;
    /*font-size:12px;*/
    /*background-color:#9ECDE4;*/
	}
	.name,.remark{
	font-size:13px;
	color:#006600;
	}
	.size{
	font-size:12px;
	font-weight:bold;
	}
	</style>
	<script type="text/javascript">
	 <!--
	 //-->
	</script>
  
  </head>
 
  <body>
   <c:forEach items="${theme.attachments}" var="attachment" varStatus="loop">
     <div><span class="name">${attachment.realname}</span><span class="size">(${attachment.size}KB)</span>
     <span class="remark"><c:if test="${!empty attachment.remark}">(备注:${attachment.remark})</c:if></span>
     <span class="download"><a href="<html:rewrite action='/theme/manage?method=downLoadAttachment&attachmentId=${attachment.id}'/>"><img title="下载" src="<%=basePath%>/img/btn_sticky.png" border="0"/></a>
     <br/>
     <span style="font-size:13px;color:#474747;">下载次数:${attachment.downloadedCount}</span>
     </span></div>  
   </c:forEach>
  </body>
</html>
