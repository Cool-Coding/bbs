<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setCharacterEncoding("utf-8");
response.setContentType("text/html;charset=utf-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>首页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/forums.css">
	<script src="<%=basePath%>/js/jquery-1.4.4.min.js" language="javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/xmlhttp.js"></script>
	<style type="text/css">
	</style>
   <script type="text/javascript">
    <!--
     function topage(page){
     var form=document.forms[0];
      if(page>=1){
       form.page.value=page;
       form.submit();
      }
     }
     
     function requestToServer(obj,id){
		send_request(function(data){
            obj.appendChild(document.createTextNode(data));
            }, 
            "<html:rewrite action='/ajax/forum/theme/message'/>?id="+id,
            true
            );
     }
     
     $(function(){
     //鼠标经过时table奇偶行颜色变化
	/**当页面载入是就注册了这个事件，所以$("table tbody tr:not(:last)").hover和
	 * $("table tbody tr[class!='selected']:not(:last)").hover在此处没有区别
	 */
	$("table tbody tr").hover(function(){
	        $(this).addClass("selected");
	},function(){
			$(this).removeClass("selected");
	});
     });
    //-->
   </script>
   
  </head>
  <body>
  <div style="text-align:center">
       <div class="outDiv">
   <!-- 页首 -->
   <jsp:include page="/WEB-INF/page/share/front_top.jsp"></jsp:include>
	
	<div id="bbsnav">
    <ul>
   	<%--未登录用户--%>
    <c:if test="${SESSION_LOGGED_ON_USER eq null}">
        <li><html:link action="/user?method=regUserUI" target="_blank">注册</html:link></li>
        <li class="line"/>
        <li><html:link action="/user?method=loginUI">登录</html:link></li>
        <li class="line"/>
    </c:if>
    <%--登录用户--%>
    <c:if test="${SESSION_LOGGED_ON_USER ne null}">
        <li><html:link action="/user?method=logout">注销</html:link></li>
        <li class="line"/>
        <li><html:link action="/user?method=changePasswordUI" 
            	onclick="return confirmDel('确定要修改密码吗？')">修改密码</html:link></li>
        <li class="line"/>
        <li><html:link action="/user?method=editUserUI&loginName=${SESSION_LOGGED_ON_USER.loginName}" 
            	onclick="return confirmDel('确定要修改密码吗？')">修改个人资料</html:link></li>
        <li class="line"/>
    </c:if>
        <li><html:link action="/theme/manage?method=queryUI" target="_blank">站内主题搜索</html:link></li>
        <bbs:permission resource="System" action="Manage" >
        	<li class="line"/>
        	<li><html:link action="/control/center">系统管理</html:link></li>
        </bbs:permission>
        <c:if test="${SESSION_LOGGED_ON_USER ne null}">
        <li style="color:blue;float:right;margin-right:5px;"><html:link action="/user?method=showUserUI&loginName=${SESSION_LOGGED_ON_USER.loginName}" target="_blank">${SESSION_LOGGED_ON_USER.nickname}</html:link>&nbsp;欢迎您</li>
        </c:if>
    </ul>
</div>
	 <form action="<html:rewrite action='/index'/>" method="post">
	    
	    <!-- 隐藏字段，页数 -->
	    <input type="hidden" name="page"/>
		<div class="function">
		<c:forEach items="${pageView.records}" var="entity">
			<table cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<td colspan="4" class="function_title">${entity.name}
					</td>
				</tr>
				<tr>
				    <th class="th1"  width="7%">&nbsp;</th>
					<th class="th2"  width="55%">版块</th>
					<th class="th2"  width="13%">新帖/帖子总数</th>
					<th class="th3"  width="30%">最新帖子</th>
				</tr>
			</thead>
			<tbody>
		      <c:forEach items="${entity.children}" var="childEntity">
		       <c:if test="${childEntity.visible}">
				<tr>
				    <td class="td1">
				   	    <bbs:isInDay date="${childEntity.lastTheme.createTime}" type="0">
				         <img src="<%=basePath%>/img/forum_new.gif" title="今天版块${childEntity.name}下有新的帖子" />
					    </bbs:isInDay>
					    <bbs:isNotInDay date="${childEntity.lastTheme.createTime}" type="0">
				         <img src="<%=basePath%>/img/forum.gif" title="今天${childEntity.name}下没有新的帖子"/>
					    </bbs:isNotInDay>
					    <c:if test="${empty childEntity.lastTheme}">
					    <img src="<%=basePath%>/img/forum.gif" title="今天${childEntity.name}下没有新的帖子"/>
					    </c:if>
				    </td>
					<td class="td2"><a href="<html:rewrite action='/get/forum/theme?forumId=${childEntity.id}' /> ">${childEntity.name}</a>
					<br/>
					子版块：
					<c:forEach items="${childEntity.children}" var="grandChildEntity" varStatus="status">
					<c:if test="${grandChildEntity.visible}">
					<c:if test="${status.count==1}"><a href="<html:rewrite action='/get/forum/theme?forumId=${grandChildEntity.id}' /> ">${grandChildEntity.name}</a></c:if>
					<c:if test="${status.count!=1}">&nbsp;<a href="<html:rewrite action='/get/forum/theme?forumId=${grandChildEntity.id}' /> ">${grandChildEntity.name}</a></c:if>
					</c:if>
					</c:forEach>
					<!-- 版主 -->
					<br/>
					<span style="font-size:13px;color:#474747;">
					版主:
					   <c:if test="${fn:length(childEntity.users)<=0}">空缺</c:if>
					<c:forEach items="${childEntity.users}" var="user">
					    ${user.loginName}&nbsp;
					</c:forEach>
					</span>
					</td>
					<td class="td2" align="center">
					<span style="color:green;font-size:1em;">${fn:length(childEntity.currentDay_Themes)}</span>/<span style="font-size:1em;">${childEntity.allThemesCount}</span></td>
					<td class="td3">
					<html:link action="/theme/manage?method=showContent&themeId=${childEntity.lastTheme.id}">
					${childEntity.lastTheme.title}					
					</html:link>
					<br/>
					<span style="font-size:12px;font-style:italic;">
					<c:if test="${!empty childEntity.lastTheme}">
					by:<html:link action="/user?method=showUserUI&loginName=${childEntity.lastTheme.user.loginName}" target="_blank">${childEntity.lastTheme.user.nickname}</html:link> 
					<br/>
					${childEntity.lastTheme.createTime}
					</c:if>
					</span>
					</td>
				</tr>
	     </c:if>
		</c:forEach>

		</tbody>
		</table>
		</c:forEach>
       <div class="fenye">
       <%@include file="/WEB-INF/page/share/simple-fenye.jsp"%>
	   </div>
		</div>
	</form>
	<!-- 页尾 -->
   <jsp:include page="/WEB-INF/page/share/front_footer.jsp"></jsp:include>
	</div>
</div>
  </body>
</html>
