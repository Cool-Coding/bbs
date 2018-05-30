<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>更新版块</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
    <script language="javascript">
    <!--
	  //为String添加一个清楚字符串前后空格的方法
	  String.prototype.trim=function(){
	      return this.replace(/(^\s*)|(\s*$)/g,"");
	  }
      function validateForm(form){
        if(form.moderator.value.trim()=="") {
        alert("版主名不能为空!");
        return false;
        }
      }
    //--></script>
  </head>
  <body>
    <html:form action="/control/forum/manage" method="post" onsubmit="return validateForm(this)"><!-- 默认为post方式 -->
    <input type="hidden" name="method"  value="addModerator" />
    <!-- 版块ID -->
    <html:hidden property="id"/>
    <!-- 父版块ID (用于添加之后返回URL中的参数)-->
    <html:hidden property="parent.id"/>
     <div align="center">添加版主</div>
      <hr/>
      <div>
        <label for="name">版块名称:${forumform.name}</label>
      </div>
      <div>
       <label for="moderator">版主:</label>
         <input type="text" name="moderator" size="25"/>
         <!--<html:errors property="moderator"/>-->
         </div>
      <div align="center"><input type="submit" value="确认"/></div>
    </html:form>
  </body>
</html>
