<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>BBS版块分页列表显示</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
<%
String path = request.getContextPath();
String base= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("base",base);
%>
    <link href="<%=base%>/css/bbs.css" rel="stylesheet" type="text/css" />
    <script src="<%=base%>/js/jquery-1.4.4.min.js" language="javascript"></script>
    <script src="<%=base%>/js/forumListjsp.js" language="javascript"></script>

    <!--弹出框-->
    <link type='text/css' href='<%=base%>/css/basic.css' rel='stylesheet' media='screen' />
    <script type='text/javascript' src='<%=base%>/js/jquery.simplemodal.js'></script>

    <script language="javascript">
    <!--
     //到指定分页页面
	 function topage(page){
	  if(page>=1){
	    form=document.forms[0];
	    form.page.value=page;
	    form.submit();
	  }
	  return false;
	 }
	 //启用or禁用版块
	 function visibleManager(enable){
	   form=document.forms[0];
	   if(!validateForm(form)){
	      return false; 
	   }
	   form.action="<html:rewrite action='/control/forum/manage'/>";
	   form.method.value=enable;
	   form.submit();
	 }
	 function validateForm(form){
	  var check=form.forumIds;
	  var checked=0;
	  if(check.length){
		  for(var i=0;i<check.length;i++){
		    if(check[i].checked){
		      checked++;
		    }
		  }
	  }else{
	    if(check.checked)checked++;
	  }
	  if(checked==0){
	  alert("请至少选择一个版块进行操作!");
	  return false;
	  }
	  return true;
	 }
    //--></script>
  </head>
  <body>
  <html:form action='/control/forum/list' method="post">
    <!-- 分页 -->
    <input type="hidden" name="page"/>
    <!-- 禁用，启用 -->
    <input type="hidden" name="method"/>
    <!-- 查询 -->
    <html:hidden property="query"/>
    <html:hidden property="name"/>
    <!-- 父版块ID-->
    <html:hidden property="parent.id"/>
    <div id="fenye">
     <%@include file="/WEB-INF/page/share/fenye.jsp"%>
	</div>
	<!-- 导航条 -->
	<c:set var="out" value=""/>
	<c:forEach items="${parents}" var="menu" varStatus="loop"><!-- 注意items属性的设置，必须有{}-->
	<c:if test="${loop.count==1}"> 
	<c:set var="out" value="-> <span style='color:green;'>${menu.name}</span>"/>
	</c:if>
	<c:if test="${loop.count>1}"> 
    <c:set var="out" value='-> <a href="${base}/control/forum/list.do?parent.id=${menu.id}">${menu.name}</a>${out}'/>
	</c:if>
	</c:forEach>
	<div style="background-color:#A2CFE6;;margin:0;padding:0;font-size:14px">
	<c:if test="${out!=null && out!=''}"><a href="${base}/control/forum/list.do">顶级版块</a></c:if>
	<c:if test="${out==null || out==''}"><span style='color:green;'>顶级版块</span></c:if>
	<c:out value="${out}" escapeXml="false"/>
	</div>
	
	<!--#######################################版块列表开始#########################################-->
	<div id="forumList">
	<table cellpadding="0" cellspacing="0">
	<thead>
	<tr>
	  <th width="5%" style="font-weight:normal"> <input type="checkbox" name="selectAll"/></th>
	  <th width="20%">版块名</th>
	  <th width="10%">可用状态</th>
	  <th width="8%">优先级</th>
	  <th width="15%">父版块</th>
	  <th width="12%">版主</th>
	  <th width="20%">创建子版块</th>
	  <th width="10%">编辑</th>
	</tr>
	</thead>
	<tbody>
	<!--#######################################Loop开始#########################################-->
	<c:forEach items="${pageView.records}" var="entity">
	 	<tr>
	    <td><input type="checkbox" name="forumIds" value="${entity.id}"/></td>
		<td align="left"><c:if test="${fn:length(entity.children)>0 && forumform.query!='true'}"><a href="<html:rewrite action='/control/forum/list?parent.id=${entity.id}'/>">${entity.name}</a>(${fn:length(entity.children)}个子版块)</c:if>
		    <c:if test="${fn:length(entity.children)<=0 || forumform.query=='true'}">${entity.name}</c:if>
		</td>
		<td><c:if test="${entity.visible}"><span class="enable">可用</span></c:if><c:if test="${!entity.visible}"><span class="disable">禁用</span></c:if><!--可以用一幅图片代表)--></td>
		<td>${entity.weight}</td>
		<td style="color:#069;"><c:if test="${!empty entity.parent}">${entity.parent.name}</c:if><c:if test="${empty entity.parent}">顶级版块</c:if></td>
		<td style="font-size:13px;">
		<c:if test="${fn:length(entity.users)<=0}"><span style="color:#d00">空缺</span></c:if>
		  <c:forEach items="${entity.users}" var="user">
		    <span style="color:green;">${user.loginName}</span>&nbsp;
		  </c:forEach>
		  <br/>
		 <span><a href="#" onclick="javascript:popDialog('<html:rewrite action='/control/forum/manage?method=addModeratorUI&id=${entity.id}'/>')">添加版主</a></span>
		</td>
		<td><a href="#" onclick="javascript:popDialog('<html:rewrite action='/control/forum/manage?method=addForumUI&parent.id=${entity.id}'/>')">创建子版块</a><!--可以用图片代表--></td>
		<td><img alt="编辑" src="<%=base%>/img/edit.gif" onmouseover="javascript:this.style.cursor='pointer'" onclick="javascript:popDialog('<html:rewrite action="/control/forum/manage?method=updateForumUI&id=${entity.id}&parent.id=${entity.parent.id}"/>')"></td>
	  </tr>
	</c:forEach>

	<!--#######################################Loop结束#########################################-->
	  <!--########################################功能按键#########################################-->
	<tr id="lastTr">
	    <td colspan="3" align="left">
	    <a name="selectAll" href="#1">全选</a>-<a name="selectOpp" href="#2">反选</a>-<a name="selectNone" href="#3">不选</a>
	    </td>
	    <td colspan="5">
	    <!-- 此四个按钮样式定义在forumListjsp.js中 -->
	    <input type="button" value="添加" id="add" action='<html:rewrite action="/control/forum/manage?method=addForumUI&parent.id=${forumform.parent.id}"/>' <c:if test="${forumform.query=='true'}">disabled="disabled"</c:if>/>
	    <input type="button" value="查询" id="find" action='<html:rewrite action="/control/forum/manage?method=findForumUI"/>'/>
	    <input type="button" value="禁用" id="disable" onclick="javascript:visibleManager('disable')"/>
	    <input type="button" value="启用" id="enable" onclick="javascript:visibleManager('enable')"/>
	    </td>
	</tr>
	</tbody>
	</table>
	</div>
	<!--########################################版块列表结束#########################################-->
	<div id="ForumManagerDialog" style="display:none">
	</div>
</html:form>
</body>
</html>
