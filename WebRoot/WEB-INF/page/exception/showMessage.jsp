<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/commons.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>出错了</title>
	</head>

	<body style="text-align: center;">

		<div style="margin: 50px; color: blue">
			对不起, 您所访问的页面出错!<br>
			系统将在5秒钟后返回上一页
		</div>

		<div id="countDown" style="font-size: 30px; color: blue; margin: 20px">
			&nbsp;
		</div>
		
		<a href="#" onclick="javascript:window.history.go(-1);">返回</a> | 
		<html:link action="/index" target="_blank">转到首页</html:link> | 
		<a href="#" onclick="window.clearInterval(countDownTimer)">暂停倒计时</a>

		<table width="30px" style="color: white">
			<tr>
				<td colspan="1">
				<a href="#" onclick="window.clearInterval(countDownTimer)"
					style="color: white">暂停倒计时</a>
				</td>
			</tr>
			<tr>
				<td>message</td>
				<td>${exceptionLog.summary }</td>
			</tr>
			<tr valign="top">
				<td>detailMessage</td>
				<td>${detailMessage }</td>
			</tr>
		</table>

		<script type="text/javascript">
			var cdNode = document.getElementById("countDown");
			var count = 5;
			cdNode.innerHTML = count;
			function countDown(){
				if(--count < 0){
					window.history.go(-1);
				}else{
					cdNode.innerHTML = count;
				}
			}
			var countDownTimer = setInterval(countDown, 1000);
		</script>
	</body>
</html>
