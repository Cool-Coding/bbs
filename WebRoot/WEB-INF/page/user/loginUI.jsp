<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>登录用户</title>
    <%@ include file="/WEB-INF/page/share/commons.jsp" %>
    <link href="${basePath}/css/member_loginUI.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<div class="topt">
    <div style="padding-top: 28px;" class="sm">论坛</div>
    <div class="clearit"></div>
</div>
<%--显示错误--%>
<div class="errorMessages" style="margin-left: 230px;"><html:errors suffix="html.br"/></div>
		
<%--显示表单--%>
<div style="border-bottom: 0pt none;" class="topt">
    <div style="margin: 30px auto; width: 820px;">
        <div style="border-right: 1px solid rgb(229, 229, 229); width: 400px; float: left;">
            <p style="background: transparent url(${basePath}/style/images/z_04.jpg) no-repeat scroll 0% 0%; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;"
               class="toppic"><img height="42" width="42" src="${basePath}/img/z_05.jpg"/></p>

            <div style="width: 320px;" class="userinfor">
                <h3>登录</h3>
                <%--登录表单--%>
                <html:form action="/user?method=login" styleClass="validate" focus="loginName">
                	<html:hidden property="PARAMETER_RETURN_PATH" value="${request_returnPath}"/>
                    <table cellspacing="0" cellpadding="0" border="0" class="table">
                        <tbody>
                        <tr>
                            <td>用户名：</td>
                            <td width="197"><html:text styleClass="inputk required" property="loginName" tabindex="10"/></td>
                        </tr>
                        <tr>
                            <td style="height: 10px;">&nbsp;</td>
                            <td style="height: 10px;">
                                还没帐号吗？<html:link action="/user?method=regUserUI">立即申请</html:link></td>
                        </tr>
                        <tr>
                            <td align="right">密　码：</td>
                            <td><html:password styleClass="inputk required" property="password" tabindex="11" redisplay="false"/></td>
                        </tr>
                        <tr>
                            <td> </td>
                            <td><html:checkbox property="autoLogin" value="true" tabindex="12"/>
                                自动登录
                            </td>
                        </tr>
                        <tr>
                            <td> </td>
                            <td><input type="submit" value="登录" class="buttonys" tabindex="13"/></td>
                        </tr>
                        </tbody>
                    </table>
                </html:form>
            </div>
        </div>
    </div>
</div>

<c:if test="${param.src eq 'forum_show_top'}">
<script type="text/javascript">
	if(window.frameElement != null){
		// 如果是从其他页面中嵌套的登录页面（iframe等），失败后应让整个窗口显示登录页面
		// 方法1, 这样不会把POST的参数传递过去
		// parent.document.location.href = document.location.href;
		// 方法2, 使用这个方法。转到登录页面，再加上显示一个错识消息（只有失败后才转到这里来）
		alert("用户名或密码不正确!");
		parent.document.location.href = "<html:rewrite action='/user?method=loginUI'/>";
	}
</script>
</c:if>

<!-- =====================底部=================== -->
<%@ include file="/WEB-INF/page/share/footer.jsp"%>

</body>
</html>