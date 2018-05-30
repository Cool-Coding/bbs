<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>版主</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
  </head>
  
  <body>
  <jsp:useBean id="forum" class="cn.yang.domain.Forum" scope="page"></jsp:useBean>
   <%
      
      request.setCharacterEncoding("utf-8");
      response.setCharacterEncoding("UTF-8");
      System.out.println(request.getParameter("forumName"));
      String str_forum=request.getParameter("forumName");
      //System.out.println(str_forum);
      
      /*System.out.println(str_forum.split(":")[1].split("\\s+")[0]);
      Integer id=Integer.parseInt(str_forum.split(":")[1].split("\\s+")[0]);
      forum.setId(id);
      request.setAttribute("forum",forum);
     */
    %>
	<span style="font-size:13px;color:#4747474;">
	版主:
	   <c:if test="${fn:length(forum.users)<=0}">空缺</c:if>
	<c:forEach items="${forum.users}" var="user">
	    ${user.loginName}&nbsp;
	</c:forEach>
	</span>
  </body>
</html>
