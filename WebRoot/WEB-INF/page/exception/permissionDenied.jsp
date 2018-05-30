<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>权限不足</title>
		<%@ include file="/WEB-INF/page/share/commons.jsp"%>
	</head>

	<body style="text-align: center;">

		<div style="margin: 50px;">
			对不起, 您权限不足!<br>
			系统将在5秒钟后返回上一页
		</div>

		<div id="countDown" style="font-size: 30px; color: blue; margin: 20px">
			&nbsp;
		</div>
		
		
		<a href="#" onclick="javascript:window.history.go(-1);">返回</a>
		<html:link action="/index" target="_blank">转到首页</html:link>

		<script type="text/javascript">
		var goHomePage = ${param.src eq 'login' ? 'true' : 'false'};
		var homePageUrl = "${basePath}/index.do";
		
		var cdNode = document.getElementById("countDown");
		var count = 5;
		cdNode.innerHTML = count;
		function countDown(){
			if(--count < 0){
				if(goHomePage){
					window.document.location.href = homePageUrl;
				}else{
					window.history.go(-1);
				}
			}else{
				cdNode.innerHTML = count;
			} 
		}
        setInterval(countDown, 1000);
	</script>
	</body>
</html>
