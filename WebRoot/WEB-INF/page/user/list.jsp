<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:html="http://www.w3.org/1999/xhtml">
<head>
    <title>角色列表</title>
    <meta http-equiv="ContentType" content="text/html;charset=utf-8">
    <%@include file="/WEB-INF/page/share/commons.jsp" %>
    <script type="text/javascript">
    	$(function(){
	    	paginationOptions = {
	    		type: "form",
	    		form: document.forms[0] 
	    		// type: "url",
	    		// url: "?method=list"
	    	};
    	});
    </script>
</head>
<body>

<%--显示错误--%>
<div class="errorMessages"><html:errors suffix="html.br"/></div>

<%--过滤参数--%>
<html:form action="/manage/user">
<html:hidden property="method" value="list"/>
<html:hidden property="page" value="1"/>
<table class="filter_form">
	<tr>
		<td>登录名<html:text property="loginName"></html:text></td>
		<td>昵称<html:text property="nickname"></html:text></td>
		<td>所属角色
			<html:select property="roleId">
				<html:option value="">所有</html:option>
				<html:optionsCollection name="request_roleList" label="name" value="id"/>
			</html:select>
		</td>
		<td>锁定状态
			<html:select property="locked">
				<html:option value="">所有</html:option>
				<html:option value="true">已锁定</html:option>
				<html:option value="false">未锁定</html:option>
			</html:select>
		</td>
		<td><html:submit>过滤</html:submit></td>
	</tr>
</table>
</html:form>

<%--显示数据--%>
<table class="list">
    <%--表头--%>
    <tr class="head">
        <th width="100px">登录名</th>
        <th width="100px">昵称</th>
        <th width="100px">角色</th>
        <!-- <th width="100px">是否登录</th> -->
        <th width="100px">是否锁定</th>
        <th width="100px">相关操作</th>
    </tr>

    <%--显示数据列表开始--%>
    <c:forEach items="${request_pageView.records}" var="item" varStatus="status">
    <tr class="item">
        <td>${item.loginName }</td>
        <td>${item.nickname }</td>
        <td><c:forEach items="${item.roles}" var="role" varStatus="status">
        		<span style="width: 50px; float: left;">${role.name }</span> 
        	</c:forEach>
        	<html:link action="/manage/user?method=selectRolesUI&loginName=${item.loginName}&page=${param.page}" 
        		style="float: right;">修改</html:link>
    	</td>
        <td class="${item.locked ? 'red' : 'normal' }">${item.locked }</td>
        <td><html:link action="/manage/user?method=lock&loginName=${item.loginName}&page=${param.page}" 
        		onclick="return confirmDel('确定要锁定此用户吗？')">锁定</html:link>
            <html:link action="/manage/user?method=unlock&loginName=${item.loginName}&page=${param.page}" 
            	onclick="return confirmDel('确定要解除锁定吗？')">解锁</html:link>
        </td>
    </tr>
    </c:forEach>
    <%--显示数据列表结束--%>
  
    <tr class="pageview">
    	<td colspan="5">
    		<%@ include file="/WEB-INF/page/share/pagination.jsp" %>
    		<!--
    		<span style="float:right"><html:link action="/user?method=regUserUI" target="_blank">注册</html:link></span>
    	-->
    	</td>
    </tr>
</table>

</body>
</html>