<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%@ page isELIgnored="false"%>
<html>
<head>
   <%
   String base=request.getContextPath();
   %>
    <!-- 消息框的样式 -->
    <link href="<%=base%>/css/message.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div align="center">
<table>
<tr>
	<td height="40"></td>
</tr>
<tr>
	<td align="center">
		<table width="305" border="0" cellpadding="0" cellspacing="0" align="center">
		  <tr>
			<td height="212" align="center" valign="middle" bgcolor="#95CBFD"><table width="295" border="0" cellpadding="0" bgcolor="#FFFFFF">
			  <tr>
				<td width="288" align="center" bgcolor="#C2E1FE"><table width="100" border="0" cellpadding="0" cellspacing="0">
					<tr>
					  <td>&nbsp;</td>
					</tr>
				  </table>
					<table width="273" border="0" cellpadding="0" cellspacing="10" bgcolor="#FFFFFF">
					  <tr>
						<td width="253" height="60" align="center" valign="bottom" class="font12">
							<p><c:out value="${message}" escapeXml="false"/>							
							</p>
						</td>
					  </tr>
					  <tr>
						<td height="80" align="center" valign="middle"><font size="2"><span class="content">
						 <input type="button" name="sure" value="确 定"
						 onclick="javascript:window.location.href='<html:rewrite action='${urladdress}'/>'">
						</span></font></td>
						</tr>
					</table>
					<table width="100" border="0" cellpadding="0" cellspacing="0">
					  <tr>
						<td>&nbsp;</td>
					  </tr>
				  </table></td>
			  </tr>
			</table></td>
		  </tr>
		</table>
	</td>
</tr>
<tr>
	<td height="40"></td>
</tr>
</table>
</div>
</body>
</html>
