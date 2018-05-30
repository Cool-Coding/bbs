<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>回帖列表分页显示</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/reply.css">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/replyListjsp.js"></script>
	
	<script type="text/javascript">
	 <!--
	   function requestPathToServer(method,id){
	     this.action="<html:rewrite action='/control/reply/manage?method="+method+"&replyId="+id+"'/>";
	   }
	 //-->
	</script>
  </head>
  
  <body>
	<div class="replyList">
	<input type="hidden" value="<html:rewrite action='/control/reply/manage'/>" id="requestPath"/>
	<c:forEach items="${pageView.records}" var="entity" varStatus="status"> 
			<div style="display:block;height:auto;clear:both;margin-bottom:10px;" class="reply*${entity.id}">
			    <div id="hr">
			    <span title="隐藏此回帖" class="reply_hide">隐藏</span>
			    <span class="reply_manage">设置 </span>
				<ul>
			        <li onmouseover="javascript:requestPathToServer.call(this,'delete','${entity.id}')">删除</li>
			        <li onclick="window.open('<html:rewrite action='/control/reply/manage?method=showEditUI&replyId=${entity.id}&page=${pageView.currentPage}'/>','_self')">修改</li>
			        <c:if test="${entity.onlyLogin}"><li onmouseover="javascript:requestPathToServer.call(this,'notLogin','${entity.id}')">不必登陆可见</li></c:if><c:if test="${!entity.onlyLogin}"><li onmouseover="javascript:requestPathToServer.call(this,'onlyLogin','${entity.id}')">登陆可见</li></c:if>
			        <c:if test="${entity.visible}"><li onmouseover="javascript:requestPathToServer.call(this,'hide','${entity.id}')">前台隐藏</li></c:if><c:if test="${!entity.visible}"><li onmouseover="javascript:requestPathToServer.call(this,'show','${entity.id}')">前台可见</li></c:if>
				</ul>
			    </div>
			    
			   <div>
				<div class="user_message">
					<img src="<html:rewrite action='/user?method=showAvatar&loginName=${entity.user.loginName}'/>"/><br/>
					<ul>
					<li>昵称：${entity.user.nickname}</li>
					<li>积分：${entity.user.score}</li>
					<li>等级：${entity.user.rank}</li>
					<li>发帖数:${entity.user.themeCount}</li>
					<li>回帖数:${entity.user.replyCount}</li>
					<li style="word-wrap:break-word;word-break:break-all;">个人描述：${entity.user.signature}</li>
					</ul>
				</div>

				<div class="theme_detail">
					 <div class="statusBar">
					   <span>${pageView.viewPageRecords*(replyform.page-1)+status.count}楼</span>
					   <span>发表时间:${entity.createTime}</span>
					 </div>
					 <div class="theme_content">${entity.content}</div>
				</div>
			</div>
		   </div>
	</c:forEach>
	
	 <script type="text/javascript">
		<!--
		//调整内容框的最低高度为200px，此处注释了因为可以使用属性min-height来设置回帖内容的最小高度
		$(".theme_detail").each(function(){
		var content=$(this);
		setTimeout(function(){
	    var contentHeight=content.height();
		content.height(contentHeight<200 ? 200+"px" : contentHeight+"px");
		},100);	
		});

		//-->	 
	</script>
	 <div id="fenye">
     <%@include file="/WEB-INF/page/share/simple-fenye.jsp" %>
     </div>
 </div>
  </body>
</html>
