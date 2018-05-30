<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:html="http://www.w3.org/1999/xhtml">
<head>
    <title>保存角色信息</title>
   <%@include file="/WEB-INF/page/share/commons.jsp" %>
    <script type="text/javascript">
        function selectAll(){
        	$("input[name=systemPrivilegeIdList]").attr("checked", "checked");
        }
        function unselectAll(){
        	$("input[name=systemPrivilegeIdList]").removeAttr("checked");
        }
        function inverseSelect(){
        	var $checkedBoxes = $("input[name='systemPrivilegeIdList']");
        	$checkedBoxes.each(function(){
        	 $(this).attr("checked",!$(this).attr("checked"));
        	});
        }
    </script>
</head>
<body>

<%--显示错误--%>
<div class="errorMessages"><html:errors suffix="html.br"/></div>

<%--显示表单--%>
<html:form action="/manage/role" styleClass="validate" focus="name">
    <html:hidden property="method" value="${roleform.id eq null ?  'add':  'edit'}"/>
    <html:hidden property="id"/>
    <table class="form">
    	<tr><td class="label">名称</td>
			<td class="field"><html:text property="name" styleClass="input1 required"/></td>
    	</tr>
    	<tr><td class="label">说明</td>
			<td class="field"><html:textarea property="description"/></td>
    	</tr>
    	<tr><td class="label">拥有权限</td>
			<td class="field">
				<c:forEach items="${request_systemPrivilegeList}" var="sp"><span style="width: 80px;">
					<html:multibox property="systemPrivilegeIdList" value="${sp.id}"/>${sp.name}</span>
				</c:forEach>
				<div style="blue">
					<a href="javascript: selectAll()">全选</a>
					<a href="javascript: unselectAll()">取消全选</a>
					<a href="javascript: inverseSelect()">反选</a>
				</div>
			</td>
    	</tr>
    	<tr class="func_list"><td colspan="2"><html:submit value="提交"/></td></tr>
    </table>
</html:form>

</body>
</html>