<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>网站信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	.outDiv{
	width:1000px;
	height:auto;
	margin-left:auto;
	margin-right:auto;
	text-align:left;
	}
	
	ul{
	text-align:left;
	}
</style>
  </head>
  
  <body>
  <div style="text-align:center">
 <div class="outDiv">
    <!-- 页首 -->
   <jsp:include page="/WEB-INF/page/share/front_top.jsp"></jsp:include>
<div>
<p>此网站我是从2010年11月份开始到2011年5月份，已基本完成，达到预期的效果。</p>
<div>网站创新之处在于：
<ul>
 <li>网站要贴近现实生活，更具人性化。如回帖浏览、附件上传等。</li>
 <li>网站功能更具一般性，如多级版块、版主控制等。</li>
 <li>网站方便性，如前台导航功能。</li>
</ul>
</div>
<div>
预想的还没实现的技术：
<ul> <li>邮件系统</li> 
<li>站内通信</li>
 <li>页面DIV移动</li>
<li>多国语言支持</li>
 <li>页面静态化</li>
 </ul>
</div></div>
 <jsp:include page="/WEB-INF/page/share/footer.jsp"></jsp:include></div></div>
  </body>
</html>
