<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<style>
<!--
#navigator{
   color:#00a;
   font-family:"微软雅黑,幼圆";
   font-size:13px;
   margin-top:5px;
  }
-->
</style>
	<!-- 导航条 -->
	<c:set var="out" value=""/>
	<c:forEach items="${parents}" var="menu" varStatus="loop"><!-- 注意items属性的设置，必须有{}-->
	<c:if test="${loop.count==1}"><c:set var="out" value="&nbsp;->&nbsp;${theme.title}"/></c:if>
    <c:set var="out" value="&nbsp;->&nbsp;<a href='${basePath}/get/forum/theme.do?forumId=${menu.id}'>${menu.name}</a>${out}"/>
	</c:forEach>
  <div id="navigator">
	<span style="margin:0;padding:0;float:left;">
	<c:if test="${out!=null && out!=''}"><a href="${basePath}/index.do">论坛首页</a></c:if>
	<c:if test="${out==null || out==''}">论坛首页</c:if>
	<c:out value="${out}" escapeXml="false"/>
	</span>
	<br/>
   <span style="float:left;margin-left:15px;">
          子版块:&nbsp;&nbsp;
   <a href="<html:rewrite action='/get/forum/theme?forumId=${currentForum.parent.id}'/> ">全部</a>&nbsp;&nbsp;
     <c:forEach items="${currentForum.parent.children}" var="child" varStatus="loop">
       <a href="<html:rewrite action='/get/forum/theme?forumId=${child.id}'/> ">${child.name}</a>&nbsp;&nbsp;
     </c:forEach>  
   </span>
 </div>