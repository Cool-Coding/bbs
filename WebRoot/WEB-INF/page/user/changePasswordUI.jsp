<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>修改密码</title>
    <%@ include file="/WEB-INF/page/share/commons.jsp" %>
    <link href="${basePath}/css/member_regUserUI.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
    	$(function(){
    	    $("input[name=password]").attr("minlength", "2");
    		$("input[name=password]").attr("maxlength", "64");
    		$("input[name=password2]").attr("equalTo", "input[name=password]");
    	});
    </script>
</head>
<body>

<%--上面--%>
<div class="topt">
    <div style="padding-top: 28px;" class="sm">论坛</div>
    <div class="clearit"></div>
</div>

<%--显示表单--%>
<html:form action="/user?method=changePassword" styleClass="validate" focus="password">
	<!--  
	<html:hidden property="loginName"/>
	-->
    <div style="border-bottom: 0pt none;" class="topt">
		<%--显示错误--%>
		<div class="errorMessages"><html:errors suffix="html.br"/></div>

        <table cellspacing="0" cellpadding="0" border="0" width="80%" class="table">
            <tbody>
            <tr><td colspan="3" style="font-weight: bold; font-size: 14px;">
            		修改密码: ${SESSION_LOGGED_ON_USER.loginName }
            	</td>
            </tr>
            <tr>
                <td width="80px;">输入密码：</td>
                <td><html:password tabindex="3" redisplay="false" styleClass="inputk required min" property="password"/></td>
                <td id="passport3" colspan="2"><div style="width: 95px;" class="infor"><span></span>2-64 个字符。</div></td>
            </tr>
            <tr>
                <td>重复密码：</td>
                <td><html:password tabindex="4" redisplay="false" styleClass="inputk required equalTo" property="password2"/></td>
                <td id="passport4" colspan="2">
                    <div style="width: 170px;" class="infor"><span></span>重复输入一次上面的密码。</div>
                </td>
            </tr>
            <tr>
                <td> </td>
                <td>
                    <button class="buttonys" value="提交" id="button" name="button" type="submit">提交</button>
                </td>
                <td align="right" colspan="2"> </td>
            </tr>
            <tr>
                <td> </td>
                <td> </td>
                <td colspan="2"> </td>
            </tr>
            </tbody>
        </table>
    </div>
</html:form>


<!-- =====================底部=================== -->
<%@ include file="/WEB-INF/page/share/footer.jsp"%>

</body>
</html>
