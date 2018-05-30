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
    <html:form action="/control/forum/manage" method="post"><!--<form>默认为get方式，此时字符编码不起作用 -->
    <input type="hidden" name="method"  value="addForum"/>
    <!-- 子版块列表 -->
    <html:hidden property="parent.id"/>
     <div align="center">添加版块</div>
      <hr/>
      <div>
        <label for="name">版块名称:</label><input name="name" type="text"/>     
      </div>
      <div>
        <label for="visible">可见:</label>
        <select name="visible">
        <option selected="selected" value="true">是</option>
         <option value="false">否</option>
        </select>
      </div>
      <div align="center"><input type="submit" value="确定"/></div>
    </html:form>
  </body>
</html>
