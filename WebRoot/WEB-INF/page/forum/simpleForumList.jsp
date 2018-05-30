<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>BBS版块分级列表显示</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
   <%
   String base=request.getContextPath();
   request.setAttribute("base",base);
   %>
    <script src="<%=base%>/js/jquery-1.4.4.min.js" language="javascript"></script>
    <script language="javascript">
    <!--
    function ascertain(){
       var form=document.forms[0];
       var parentForm=opener.document.forms[1];
       if(parentForm){
       //parentForm.parentId.value=form.checkId.value;
       //parentForm["parent.name"].value=form.checkName.value;
       $(parentForm).children("input[name='parent.id']").attr("value",form.checkId.value);
       //$(parentForm).children("input[name='parent.name']").attr("value",form.checkName.value);//此时找不到name为parent.name的元素
       opener.document.getElementsByName("parent.name")[0].value=form.checkName.value;
       }
       window.close();
    }
    function preCheck(id,name){
     var form=document.forms[0];
     form.checkId.value=id;
     form.checkName.value=name;
    }
    //--></script>
  </head>
  <body>
  <html:form action='/control/forum/list' method="post">
    <!-- 父版块ID-->
    <html:hidden property="parent.id"/>
    <input type="hidden" name="checkId"/>
    <input type="hidden" name="checkName"/>
	<!--#######################################版块列表开始#########################################-->
	<div>
	<c:set var="out" value=""/>
	<c:forEach items="${parents}" var="menu" varStatus="loop"><!-- 注意items属性的设置，必须有{}-->
	<c:if test="${loop.count==1}"> 
	<c:set var="out" value="&gt;&gt;${menu.name}"/>
	</c:if>
	<c:if test="${loop.count>1}"> 
    <c:set var="out" value='&gt;&gt;<a href="${base}/control/forum/list.do?simpleForumList=true&parent.id=${menu.id}&id=${param.id}">${menu.name}</a>${out}'/>
	</c:if>
	</c:forEach>
	<div style="background-color:#9ECDE4;margin:0;padding:0">
	<c:if test="${out!=null && out!=''}"><a href="${base}/control/forum/list.do?simpleForumList=true&id=${param.id}&other=true">顶级版块</a></c:if>
	<c:if test="${out==null || out==''}">顶级版块</c:if>
	<c:out value="${out}" escapeXml="false"/>
	</div>
	
	
	<table cellpadding="0" cellspacing="3" border="0">
	<tbody style="border-spacing:0">
	<!--#######################################Loop开始#########################################-->
	<tr>
	 <td colspan="${recordsCols}">
	   <input type="radio" name="checkForum" onclick="javascript:preCheck('','顶级版块')"/>顶级版块<hr/>
	 </td>
	</tr>
	 <c:forEach items="${pageView.records}" var="entity" varStatus="vs">
	  <c:if test="${vs.index%recordsCols==0}"><tr></c:if>
	  
	  <c:if test="${entity.id!=param.id}">
      <td>
          <input type="radio" name="checkForum" onclick="javascript:preCheck('${entity.id}','${entity.name}')"/>
          <c:if test="${fn:length(entity.children)>0}"><a href="<html:rewrite action='/control/forum/list?simpleForumList=true&parent.id=${entity.id}&id=${param.id}'/>">${entity.name}</a></c:if>
          <c:if test="${fn:length(entity.children)==0}">${entity.name}</c:if>
      </td>
      </c:if>
      
      <!-- 若当前版块为所进行编辑的版块 -->
      <c:if test="${entity.id==param.id}">
      <td>${entity.name}</td>
      </c:if>
     <c:if test="${vs.index%recordsCols==recordsCols-1}">
        </tr>
     </c:if>
	</c:forEach>
     <tr align="center">
       <td colspan="${recordsCols}"><hr/><input type="button" value="确定" onclick="ascertain()"/>
       </td>
     </tr>
    </tbody>
	</table>
	</div>
</html:form>
</body>
</html>
