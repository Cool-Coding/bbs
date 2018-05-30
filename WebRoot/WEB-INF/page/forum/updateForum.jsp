<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>更新版块</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<%
	String path = request.getContextPath();
	String base= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
    <script src="<%=base%>/js/jquery-1.4.4.min.js" language="javascript"></script>
    <script language="javascript">
    <!--
    function deleteModertor(obj,moderator){
      //$("input[type='hidden'][name='moderator_name'][value='"+moderator+"']").remove();
      $(obj).parent().remove();
    }
    
      //为String添加一个清楚字符串前后空格的方法
	  String.prototype.trim=function(){
	      return this.replace(/(^\s*)|(\s*$)/g,"");
	  }
    function validateForm(form){
     if(form.name.value.trim()==""){
      alert("版块名称不能为空!");
      return false;
     }
     var weight=form.weight.value.trim();
     if(weight==""){
      alert("优先级不能为空!");
      return false;
     }
    }
    function onlyNumbers(e)
	{
	var keynum
	var keychar
	var numcheck
	
	if(window.event) // IE
	  {
	  keynum = e.keyCode
	  }
	else if(e.which) // Netscape/Firefox/Opera
	  {
	  keynum = e.which
	  }
	if(keynum==8)return true;
	keychar = String.fromCharCode(keynum)
	
	//alert(keychar);
	numcheck = /\d/
	return numcheck.test(keychar)
	}
    //--></script>
  </head>
  <body>
    <html:form action="/control/forum/manage" method="post" onsubmit="return validateForm(this)"><!-- 默认为post方式 -->
    <input type="hidden" name="method"  value="updateForum"/>
    <!-- 版块ID -->
    <html:hidden property="id"/>
    <!-- 父版块ID -->
    <html:hidden property="parent.id"/>
     <div align="center">更新版块</div>
      <hr/>
      <div>
        <label for="name">版块名称:</label><html:text property="name"></html:text>     
      </div>
      <div>
        <label for="visible">可见:</label>
        <html:select property="visible">
          <html:option value="true">是</html:option>
          <html:option value="false">否</html:option>
        </html:select>
      </div>
      <div>
       <label for="parent.name">所属版块</label>
       <html:text property="parent.name" disabled="true"/><input type="button" value="修改" id="changeForum" onclick='javascript:window.open("<html:rewrite action='/control/forum/list'/>?simpleForumList=true&id=${param.id}","板块列表","1440px","200px")'/>
      </div>
      <div>
       <label for="parent.name">优先级:</label>
       <html:text property="weight" onkeypress="return onlyNumbers(event)"/>
      </div>
      <div>
       <label>版主:</label>
       <c:if test="${fn:length(forumform.moderators)<=0}"><span style="color:#d00;">空缺</span></c:if> 
      <span style="font-size:12px">
       <c:forEach items="${forumform.moderators}" var="user">
       <span>
         <input type="hidden" name="moderator_name" value="${user.loginName}"/>
		 ${user.loginName}(<a href="#" onclick="javascript:deleteModertor(this,'${user.loginName}')">删除</a>)
	   </span>
		  </c:forEach>
	   </span>
      </div>
      <div align="center"><input type="submit" value="确认"/></div>
    </html:form>
  </body>
</html>
