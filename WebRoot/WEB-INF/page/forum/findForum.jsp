<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加版块</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	
  </head>
  <body>
    <form action="<html:rewrite action='/control/forum/list'/>" method="post">
    <input type="hidden" name="query"  value="true"/>
     <div align="center">查找版块</div>
      <hr/>
      <div>
        <label for="name">版块名称:</label><input name="name" type="text"/>     
      </div>
      <div>
        <label for="name">版&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主:</label><input name="moderator" type="text"/>    
      </div>
      <div align="center"><input type="submit" value="查找"/></div>
    </form>
  </body>
</html>
