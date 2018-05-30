<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <title>BBS帖子分页列表显示</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
   <%
   String base=request.getContextPath();
   %>
    <link href="<%=base%>/css/bbs.css" rel="stylesheet" type="text/css" />
    <script src="<%=base%>/js/jquery-1.4.4.min.js" language="javascript"></script>
    <script src="<%=base%>/js/forumListjsp.js" language="javascript"></script>
    <script src="<%=base%>/js/themeListjsp.js" language="javascript"></script>

    <!--弹出框-->
    <link type='text/css' href='<%=base%>/css/basic.css' rel='stylesheet' media='screen' />
    <script type='text/javascript' src='<%=base%>/js/jquery.simplemodal.js'></script>
    <style type="text/css">
       tr{
		font-size:15px;
		}
    </style>
    <script language="javascript">
    <!--
     //启用或禁用帖子
	 function visibleManager(enable){
	   form=document.forms[0];
	   if(!validateForm(form)){
	      return false; 
	   }
	   form.action="<html:rewrite action='/control/theme/manage'/>";
	   form.method.value=enable;
	   form.submit();
	 } 
	 //对每行前的复选框进行 "已选" 判断
	  function validateForm(form){
	  var check=form.themeIds;
	  var checked=0;
	  if(!check) return false;
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
	  alert("请至少选择一个帖子进行操作!");
	  return false;
	  }
	  return true;
	 }
     //向服务器请求对帖子的管理
     function requestToServer(params,method){
     //组拼参数字符串
      var vars="?";
       for(var item in params){
          vars+=item+"="+params[item]+"&";
       }
     vars=vars.substring(0,vars.length-1);
     var form=document.forms[0];
     form.action="<html:rewrite action='/control/theme/manage'/>"+(vars=="?"?"":vars);
     form.method.value=method;
     form.submit();       
     }
     //到指定分页页面
	 function topage(page){
	  if(page>=1){
	    form=document.forms[0];
	    form.page.value=page;
	    form.submit();
	  }
	  return false;
	 }
	 //启用或禁用版块
	 function themeManager(enable){
	   form=document.forms[0];
	   if(!validateForm(form)){
	      return false; 
	   }
	   form.action="<html:rewrite action='/control/theme/manage'/>";
	   form.method.value=enable;
	   form.submit();
	 }
    //--></script>
  </head>
  <body>
  <html:form action='/control/theme/list' method="post">
    <!-- 分页 -->
    <input type="hidden" name="page"/>
    
    <c:if test="${!themeform.query}">
    <!--要查看的帖子所属的版块ID -->
    <html:hidden property="forumId"/> 
    </c:if>

    <!-- 请求参数之一method -->
    <input type="hidden" name="method"/>
    <div id="fenye">
     <%@include file="/WEB-INF/page/share/fenye.jsp"%>
	</div>
	<div style="background-color:#9ECDE4;height:23px;">
	<span style="float:left">
    <!--下面的是根据版块名称进行相应版块下的帖子列表显示 -->
    <input type="text" class="forumName" action="<html:rewrite action='/ajax/forum/cascade/list?method=getForumCascadeList'/>" value="${currentForum.name}"/> <input type="submit" value="查看"/>
    </span>	
	<div id="forumCascadeList"></div>
     <!-- 查询帖子 -->
     <span id="searchTheme">
       <html:hidden property="query"/>
       <!-- 根据下拉列表中的分类进行查询 (0表示按标题查询，1表示按作者查询)-->
       <html:select property="category">
        <html:option value="0">标题</html:option>
        <html:option value="1">作者</html:option>
       </html:select>
       <html:text property="keywords"/>
       <input type="button" value="查询"/>
     </span>
     </div>
     
	<!--#######################################版块列表开始#########################################-->
	<div id="themeList">
	<table cellpadding="0" cellspacing="0">
	<thead>
	  <th width="2%"><input type="checkbox" name="selectAll"/></th>
	  <th width="20%">标题</th>
	  <th width="8%">作者</th>
	  <th width="10%">创建时间</th>
	  <th width="7%">类型</th>
	  <th width="15%">所属版块</th>
	  <th width="8%">阅读量</th>
	  <th width="5%">可见</th>
	  <th width="5%">推荐</th>
	  <th width="5%">置顶</th>
	  <th width="10%">更新时间</th>
	  <th width="5%">编辑</th>
	</thead>
	<tbody>
	<!--#######################################Loop开始#########################################-->
	<c:forEach items="${pageView.records}" var="entity">
	 	<tr>
	    <td><input type="checkbox" name="themeIds" value="${entity.id}"/></td>
	    <td align="left"><a href="<html:rewrite action='/control/theme/manage?method=showThemeUI&themeId=${entity.id}'/>" title="点击查看帖子详细内容">${entity.title}</a></td>
		<td>${entity.user.loginName}</td>
		<td>${entity.createTime}</td>
		<td>${entity.type.name}</td>
		<td>${entity.forum.name}</td>
		<td>${entity.readCount}(次)</td>
		<td><c:if test="${entity.visible}"><span class="enable">是</span></c:if><c:if test="${!entity.visible}"><span class="disable">否</span></c:if></td>
		<td><c:if test="${entity.commend}"><span class="enable">是</span></c:if><c:if test="${!entity.commend}"><span class="disable">否</span></c:if></td>
		<td><c:if test="${entity.top}"><span class="enable">是</span></c:if><c:if test="${!entity.top}"><span class="disable">否</span></c:if></td>
		<td>${entity.updateTime}</td>
		<td><img alt="编辑" title="编辑帖子" src="<%=base%>/img/edit.gif" onmouseover="javascript:this.style.cursor='pointer'" onclick="javascript:requestToServer({'themeId':'${entity.id}'},'editThemeUI')"></td>
	  </tr>
	</c:forEach>

	<!--#######################################Loop结束#########################################-->
	  <!--########################################功能按键#########################################-->
	<tr id="lastTr">
	    <td colspan="3" align="left">
	   <a name="selectAll" href="#1">全选</a>-<a name="selectOpp" href="#2">反选</a>-<a name="selectNone" href="#3">不选</a>
	    </td>
	    <td colspan="9">
	    <input type="text" class="forumName" id="moveTheme" action="<html:rewrite action='/ajax/forum/cascade/list?method=getForumCascadeList&type=move'/>" value="移动到……" readonly="readonly"/>
	    <!-- 此四个按钮样式定义在forumListjsp.js中 -->
	    <c:if test="${!themeform.query}"><input type="button" value="添加" id="add" onclick="javascript:requestToServer({'forumId':'${themeform.forumId}'},'addThemeUI')" /></c:if>
	    <input type="button" value="删除" id="find" onclick="javascript:themeManager('delete')"'/>
	    <input type="button" value="禁用" id="disable" onclick="javascript:themeManager('disable')"/>
	    <input type="button" value="启用" id="enable" onclick="javascript:themeManager('enable')"/>
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
