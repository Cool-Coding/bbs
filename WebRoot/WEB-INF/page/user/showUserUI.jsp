<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户个人资料</title>
    <%@ include file="/WEB-INF/page/share/commons.jsp" %>
    <link href="${basePath}/css/member_regUserUI.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div align="center">
<%--上面--%>
<div class="topt">
    <div style="padding-top: 28px;" class="sm">GotoIT论坛</div>
    <div class="clearit"></div>
</div>

<%--显示表单--%>
<html:form action="/user">
    <div style="border-bottom: 0pt none;" class="topt">
        <h2 style="padding: 20px 0pt 6px;" class="f14">${user.loginName}的个人资料:</h2>
        <div class="blank16"></div>
        

        <table cellspacing="0" cellpadding="0" border="0" width="50%" class="table">
            <tbody>
             <tr>
                <td colspan="2"><img src="<html:rewrite action='/user?method=showAvatar&loginName=${user.loginName}'/>"/> </td>
            </tr>
            <tr>
                <td>登 录 名：</td>
                <td>${user.loginName}</td>
            </tr>
            <tr>
                <td>昵　　称：</td>
                <td>${user.nickname}</td>

            </tr>
            <tr>
                <td>积　　分：</td>
                <td>${user.score}</td>
            </tr>
            <tr>
                <td>发帖数：</td>
                <td>${user.themeCount}</td>
            </tr>
            <tr>
                <td>回帖数：</td>
                <td>${user.replyCount}</td>
            </tr>
            <tr>
                <td>等           级：</td>
                <td>${user.rank}</td>
            </tr>
            <tr>
                <td>性    别：</td>
                <td>
                    <html:radio value="MALE" tabindex="7" property="gender" disabled="true"/> 男 
                    <html:radio value="FEMALE" property="gender" disabled="true"/> 女
                </td>
            </tr>
            <tr>
                <td>电子邮件：</td>
                <td>${user.email}</td>
            </tr>
            <tr>
                <td>注册时间：</td>
                <td>${user.registrationTime}</td>
            </tr>
            <tr>
                <td>上次访问时间：</td>
                <td>${user.lastVisitTime}</td>
            </tr>
             <tr>
                <td>个性签名：</td>
                <td style="word-wrap:break-word;word-break:break-all;">${user.signature}</td>
            </tr>
            </tbody>
        </table>
    </div>
</html:form>


<!-- =====================底部=================== -->
<%@ include file="/WEB-INF/page/share/footer.jsp"%>
</div>
</body>
</html>
