<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:html="http://www.w3.org/1999/xhtml">
<head>
    <title>角色列表</title>
    <%@include file="/WEB-INF/page/share/commons.jsp" %>
</head>
<body>

<%--显示错误--%>
<div class="errorMessages"><html:errors suffix="html.br"/></div>

<%--显示数据--%>
<table class="list">
    <%--表头--%>
    <tr class="head">
        <th width="100px">名称</th>
        <th width="100px">描述</th>
        <th>系统权限</th>
        <th width="180px">相关操作</th>
    </tr>

    <%--显示数据列表开始--%>
    <c:forEach items="${request_list}" var="item" varStatus="status">
    <tr class="item">
        <td>${item.name } <c:if test="${item.defaultForNewUser}">（新注册用户默认角色）</c:if> </td>
        <td>${item.description }</td>
        <td><c:forEach items="${item.systemPrivileges}" var="sp" varStatus="status">
        		<span style="width: 60px;">${sp.name }</span>
        	</c:forEach>
    	</td>
        <td><html:link action="/manage/role?method=setDefault&default=true&id=${item.id}" 
        		onclick="return confirmDel('确定要设为新注册用户的默认角色吗？')">设为默认</html:link>
        	<html:link action="/manage/role?method=setDefault&default=false&id=${item.id}"
        		onclick="return confirmDel('确定要取消此默认角色吗？')">取消默认</html:link>
        	<html:link action="/manage/role?method=editUI&id=${item.id}">修改</html:link>
            <html:link action="/manage/role?method=del&id=${item.id}" onclick="return confirmDel()">删除</html:link>
        </td>
    </tr>
    </c:forEach>
    <%--显示数据列表结束--%>
    
	<tr class="func_list"><td align="right" colspan="4"><html:link action="/manage/role?method=addUI">添加</html:link></td></tr>
</table>
</body>
</html>