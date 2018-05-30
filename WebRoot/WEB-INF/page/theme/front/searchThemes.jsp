<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
request.setAttribute("basePath",basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>帖子分页列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  <script src="<%=basePath%>/js/jquery-1.4.4.min.js" language="javascript"></script>
  <style type="text/css">
  *{
      font-family:"宋体";
  }
   table{
    width:100%;
    clear:both;
    /*border:solid 1px #006699;*/
    text-align:center;
    font-size:14px;
   }
   
   th{
   background-color:#EFF6FD;
   color:#777;
   }
   td{
   /*background-color:#aaaabb;*/
   border:solid 1px #ccc;
   border-top:none;
   height:30px;
   }
   
   .td1{
   border-right:none;
   }
   .td2{
   border-right:none;
   border-left:none;
   color:#474747;
   }
   .td3{
   border-left:none;
   color:#474747;
   }
   #navigator{
    color:#00a;
    font-family:"微软雅黑,幼圆";
    font-size:14px;
    margin:5px;
    margin-bottom:0;
   }
   a{
   color:#00a;
   }
   a:hover{
   color:#a00;
   }
   	.outDiv{
	width:1000px;
	height:auto;
	margin-left:auto;
	margin-right:auto;
	}
   .fenye{
    font-size:12px;
    float:right;
    margin-right:20px;
    margin-top:7px;
    }

  #postTheme{
  margin-top:5px;
  float:left;
  }
  form{
  clear:both;
  margin-top:10px;
  }
 .selected{
  background-color:#E8F3FD;
  }
  
  #alterTheme{
  float:left;
  display:none;
  font-size:15px;
  text-align:left;
  margin:0;
  padding:0;
  }
  
  #moveTheme{
	font-style:italic;
	color:#ccc;
  }
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
<jsp:include page="/WEB-INF/page/share/front_top.jsp"></jsp:include>

  <html:form action="/get/forum/theme">
  
   <!-- 隐藏字段，页数 -->
   <input type="hidden" name="page"/>
   <input type="hidden" name="method"/>
   
        <!-- 查询帖子 -->
     <span id="searchTheme">
       <input type="hidden" name="query" value="true"/>
       <!-- 根据下拉列表中的分类进行查询 (0表示按标题查询，1表示按作者查询)-->
       <html:select property="category">
        <html:option value="0">标题</html:option>
        <html:option value="1">作者</html:option>
       </html:select>
       <html:text property="keywords"/>
       <input type="submit" value="查询"/>
     </span>
<div align="left">
<a href="${basePath}/index.do">论坛首页</a> ->&nbsp;<span style="color:#0a2;">搜索帖子</span>
</div>
<!-- 分页 -->
  <div class="fenye">
     <%@include file="/WEB-INF/page/share/simple-fenye.jsp"%>
  </div>
   <table cellspacing="0" cellpadding="0" align="center">
   <thead>
      <tr>
       <th width="5%">&nbsp;</th>
       <th width="30%">主题</th> 
       <th width="5%">回复</th>
       <th width="20%">版块</th>
       <th width="15%">作者</th>
       <th width="5%">阅读</th>
       <th width="20%">最后更新</th>
      </tr>
   </thead>
   <tbody>
	<c:forEach items="${pageView.records}" var="entity">
	 	<tr>
	    <td class="td1">
	    <span style="display:none;">
	    <input type="checkbox" name="themeIds" value="${entity.id}"/>
	    </span>
	    <c:if test="${entity.type.name=='普通帖'}">
	    <bbs:isInDay date="${entity.lastReply.createTime}" type="0">
         <img src="${basePath}/img/folder_hot.gif" title="今天帖子${entity.title}有新的回帖" />
	    </bbs:isInDay>
	    <bbs:isNotInDay date="${entity.lastReply.createTime}" type="0">
         <img src="${basePath}/img/folder_common.gif" title="今天帖子${entity.title}没有新的回帖"/>
	    </bbs:isNotInDay>
	    <c:if test="${empty entity.lastReply}">
	    <img src="${basePath}/img/folder_common.gif" title="今天帖子${entity.title}没有新的回帖"/>
	    </c:if>
	    </c:if>
	    <c:if test="${entity.type.name=='公告'}">
	    <img src="${basePath}/img/ann_icon.gif"/>
	    </c:if>
	    </td>
        <td style="text-align:left;" class="td2">
	    <a href="<html:rewrite action='/theme/manage?method=showContent&themeId=${entity.id}'/>" title="点击查看帖子详细内容">${entity.title}</a></td>
		<td class="td2">${fn:length(entity.replies)}</td>
		<td class="td2">${entity.forum.name}
		<c:if test="${fn:length(currentForum.children)>0}">
		<br/>
		<!-- 版主 -->
		<span style="font-size:13px;color:#474747;">
		版主:
		   <c:if test="${fn:length(entity.forum.users)<=0}">空缺</c:if>
		<c:forEach items="${entity.forum.users}" var="user">
		    ${user.loginName}&nbsp;
		</c:forEach>
		</span>
		 </c:if>
		</td>
		<td class="td2">${entity.user.loginName}<br/>
		<span style="font-size:11px;">
		${entity.createTime}
		</span></td>
	    <td class="td2">${entity.readCount}</td>
		<td class="td3">
		<c:if test="${! empty entity.lastReply}">
		<html:link action="/reply/manage?method=showLastReply&themeId=${entity.id}" title="查看最后发表的回复">
		${entity.lastReply.createTime}
		</html:link>
		</c:if>
		<br/><span style="font-size:11px;"><html:link action="/user?method=showUserUI&loginName=${entity.lastReply.user.loginName}" target="_blank">${entity.lastReply.user.nickname}</html:link></span>
		</td>
	  </tr>
	</c:forEach>
	<c:if test="${fn:length(pageView.records)<=0}">
	<tr>
	 <td colspan="7"><span style="font-size:14px;">没有查询到满足条件的帖子</span></td>
	</tr>
	</c:if>
   </tbody>
 </table>
   <div class="fenye" style="margin-bottom:10px;">
     <%@include file="/WEB-INF/page/share/simple-fenye.jsp"%>
  </div>
 </html:form>
<jsp:include page="/WEB-INF/page/share/front_footer.jsp"></jsp:include>
 </div>
</div>
  </body>
</html>
