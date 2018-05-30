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
  <!-- 使用其中的全选、反选功能 -->
  <script src="<%=basePath%>/js/forumListjsp.js" language="javascript"></script>
  <!-- 使用到其中的移动功能 -->
  <script src="<%=basePath%>/js/themeListjsp.js" language="javascript"></script>
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
    /*
   .head{
	text-align:left;
	height:100px;
	border:solid 1px #ccc;
	}
	
   .bottom{
	clear:both;
	text-align:left;
	height:100px;
	border:solid 1px #ccc;
	}
	*/

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
     
     function requestToServer(forumId){
     if(forumId && forumId>0){
       var form=document.forms[0];
       form.action="<html:rewrite action='/theme/manage'/>";
       form.method.value="addContentUI";
       form.forumId.value=forumId;
       form.submit();
       }
       return false;
     }
    
     //向服务器请求对帖子的管理
     function requestToServer2(params,method){
     //组拼参数字符串
      var vars="?";
       for(var item in params){
          vars+=item+"="+params[item]+"&";
       }
     vars=vars.substring(0,vars.length-1);
     var form=document.forms[0];
     
     //判断是否复选框被选择
     if((method=="delete"|| method=="disable") && !validateForm(form)) return false;
     
     form.action="<html:rewrite action='/theme/manage'/>"+(vars=="?"?"":vars);
     form.method.value=method;
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
	
  //帖子编辑
  $("#alterThemeOrder").click(function(){
   $("table tbody tr td[class='td1'] span").toggle();
   $("table tbody tr td[class='td3'] span:has(img)").toggle();
   $("#alterTheme").toggle();
  }); 
});
     //-->
     </script>
  </head>
  
  <body>
 <div style="text-align:center">
 <div class="outDiv">
<jsp:include page="/WEB-INF/page/share/front_top.jsp"></jsp:include>

	<!-- 导航条 -->
	<c:set var="out" value=""/>
	<c:forEach items="${parents}" var="menu" varStatus="loop"><!-- 注意items属性的设置，必须有{}-->
	<c:if test="${loop.count==1}"> 
	<c:set var="out" value="&nbsp;->&nbsp;${menu.name}"/>
	</c:if>
	<c:if test="${loop.count>1}"> 
    <c:set var="out" value="&nbsp;->&nbsp;<a href='${basePath}/get/forum/theme.do?forumId=${menu.id}'>${menu.name}</a>${out}"/>
	</c:if>
	</c:forEach>
  <div id="navigator">
	<div style="margin:0;padding:0;float:left;">
	<c:if test="${out!=null && out!=''}"><a href="${basePath}/index.do">论坛首页</a></c:if>
	<c:if test="${out==null || out==''}">论坛首页</c:if>
	<c:out value="${out}" escapeXml="false"/>
	</div>
	<br/>
   <div style="float:left;margin-left:15px;">
          子版块:&nbsp;&nbsp;
   <c:if test="${fn:length(currentForum.children)>0}">
   <a href="<html:rewrite action='/get/forum/theme?forumId=${currentForum.id}'/> ">全部</a>&nbsp;&nbsp;
     <c:forEach items="${currentForum.children}" var="child" varStatus="loop">
          <a href="<html:rewrite action='/get/forum/theme?forumId=${child.id}'/> ">${child.name}</a>&nbsp;&nbsp;
     </c:forEach>       
   </c:if>
   <c:if test="${fn:length(currentForum.children)==0}">
   <a href="<html:rewrite action='/get/forum/theme?forumId=${currentForum.parent.id}'/> ">全部</a>&nbsp;&nbsp;
     <c:forEach items="${currentForum.parent.children}" var="child" varStatus="loop">
       <c:if test="${child.id==currentForum.id}">
         <span style="color:#fa0">${child.name}</span>&nbsp;&nbsp;
       </c:if>
       <c:if test="${child.id!=currentForum.id}">
       <a href="<html:rewrite action='/get/forum/theme?forumId=${child.id}'/> ">${child.name}</a>&nbsp;&nbsp;
       </c:if>
     </c:forEach>  
   </c:if>
   </div>
</div>

<!-- 版主 -->
  <hr style="clear:both;border:1px solid #ccc;"/>
	<span style="font-size:13px;color:#474747;float:left;font-style:italic;">
	版主:
	   <c:if test="${fn:length(currentForum.users)<=0}">空缺</c:if>
	<c:forEach items="${currentForum.users}" var="user">
	    ${user.loginName}&nbsp;
	</c:forEach>
	</span>

  <form action="<html:rewrite action='/get/forum/theme'/>" method="post"">
  
   <!-- 隐藏字段，页数 -->
   <input type="hidden" name="page"/>
   <input type="hidden" name="method"/>
   <input type="hidden" name="forumId" value="${themeform.forumId}"/>
  <div id="postTheme">
   <c:if test="${fn:length(currentForum.children)>0}">
	   <img alt="发表帖子" src="<%=basePath%>/img/post.gif" style="cursor:pointer" onclick="javascript:requestToServer('${currentForum.id}')"/>

   </c:if>
   <c:if test="${fn:length(currentForum.children)==0}">
	   <img alt="发表帖子" src="<%=basePath%>/img/post.gif" style="cursor:pointer" onclick="javascript:requestToServer('${currentForum.parent.id}')"/>
   </c:if>
  <bbs:permission action="Update" resource="Theme">
    <bbs:moderatorIdentify forum="${currentForum}">
     <c:if test="${fn:length(pageView.records)>0}">
       <span id="alterThemeOrder"><a href="#">编辑帖子</a></span>
     </c:if>
    </bbs:moderatorIdentify>
  </bbs:permission>
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
		<td class="td2"><html:link action="/user?method=showUserUI&loginName=${entity.user.loginName}" target="_blank">${entity.user.nickname}</html:link><br/>
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
		<bbs:permission action="Update" resource="Theme">
		<span style="display:none;float:right;">
		<img alt="编辑" title="编辑帖子" src="<%=basePath%>/img/edit.gif" style="cursor:pointer;float:right;" onclick="javascript:requestToServer2({'themeId':'${entity.id}'},'editThemeUI')">
		</span>
		</bbs:permission>
		</td>
	  </tr>
	</c:forEach>
	<c:if test="${fn:length(pageView.records)<=0}">
	<tr>
	 <td colspan="7"><span style="font-size:14px;">无帖子可浏览，是否要<a href="javascript:requestToServer('${currentForum.parent.id}')">发表新帖</a>?</span></td>
	</tr>
	</c:if>
   </tbody>
  </table>
 
    <!-- 编辑帖子 -->
  <div id="alterTheme">
    <span style="float:left;font-size:14px;"><a name="selectAll" href="#1">全选</a>-<a name="selectOpp" href="#2">反选</a>-<a name="selectNone" href="#3">不选</a></span>
    <br/>
    <bbs:permission action="Delete" resource="Theme">
    <span onclick="javascript:requestToServer2(null,'delete');" style="cursor:pointer;" >删除</span>
    </bbs:permission>
    <bbs:permission action="Update" resource="Theme">
    <span onclick="javascript:requestToServer2(null,'disable');" style="cursor:pointer;" >隐藏</span>
    </bbs:permission>
    <bbs:permission action="Move" resource="Theme">
    <input type="text" class="forumName" id="moveTheme" action="<html:rewrite action='/ajax/forum/cascade/list?method=getForumCascadeList&type=move'/>" value="移动到……" readonly="readonly"/>
    </bbs:permission>
     <div id="forumCascadeList"></div>
  </div>
  
   <div class="fenye" style="margin-bottom:10px;">
     <%@include file="/WEB-INF/page/share/simple-fenye.jsp"%>
  </div>
 </form>
<jsp:include page="/WEB-INF/page/share/front_footer.jsp"></jsp:include>
 </div>
</div>
  </body>
</html>
