<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>显示帖子</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/themeListjsp.js"></script>
	
	<style type="text/css">
	 
	 table{
	 width:100%;
	 }
	 td{
	 background-color:#F4FBFE;
	 }
	 .user,.attachment{
	 background-color:#9ECDE4;
	 }
	 #fastToReply{
	 width:100%;
	 height:200px;
	 margin-top:50px;
	 }
	 #motion{
	 width:15%;
	 float:left;
	 margin-left:100px;
	 height:100%;
	 border:solid 2px #ccc;
	 background-color:#ccc;
	 }
	 
	 #replyContent{
	 width:73%;
	 float:right;
	 height:100%;
	 }
	 textArea{
	 padding:0;
	 margin:0;
	 }
	 #replyContent textArea{
	 width:65%;
	 height:100%
	 }
	 
	 #confirm{
	 text-align:center;
	 clear:both;
	 }
	</style>
	
	<script type="text/javascript">
	 <!--
	    $(function(){
	       $("tr td:first").width("10%");
	    });
	 //-->
	</script>
  </head>
  
  <body>
   <table cellspacing="1">
     <tr>
      <td><label title="用户名">用户名</label></td>
      <td class="user"><label title="${theme.user.loginName}">${theme.user.loginName}</label></td>
     </tr>
     <tr>
      <td><label for="title" title="帖子标题">标题：</label></td>
      <td><label>${theme.title}</label></td>
     </tr>
     <tr>
      <td><label for="isTop" title="状态">置顶：</label></td>
      <td><label>
               <c:if test="${theme.top}">是</c:if>
               <c:if test="${!theme.top}">否</c:if>
           </label>
     </td>
     </tr>
     <tr>
      <td><label for="forum" title="所属版块">所属版块：</label></td>
      <td><label>${themeform.forumName}</label>
      </td>
     </tr>
      <tr height="200px">
      <td valign="top"><label title="帖子内容">内容：</label></td>
      <td valign="top"><label>${theme.content}</label>
	 </td>
      </tr>
      <tr id="de_second">
        <td valign="top"><label title="附件">附件:</label></td>
        <td class="attachment" valign="top">
       <jsp:include page="/WEB-INF/page/uploadfile/attachmentList_show.jsp"></jsp:include>
        </td>
      </tr>
  </table>
 <div>
  <div style="margin-top:10px;">
  <a href="<html:rewrite action='/control/theme/manage?method=editThemeUI&themeId=${theme.id}'/>">修改帖子</a>
  <a id="showReply" href="#showreply" action="<html:rewrite action='/control/reply/list?themeId=${theme.id}'/>">显示回复</a>
  <a id="postReply" href="<html:rewrite action='/control/reply/manage?method=showReplyUI&themeId=${theme.id}'/>">发表回复</a>
  </div>
  <div id="replies" style="display:none">
  </div>
  <div id="post_reply" style="display:none"></div>
 </div>
  <script type="text/javascript">
  <!--   
        //此方法没有调用
        /*function change(){
            var frame=window.parent.$("#content");
            var replies=$("#replies");
            alert(replies.height());
   		    frame.height(frameHeight+replies.height());
		    //replies.show("slow");
		    }*/
  //-->
  </script>
  <!-- 快速回复 -->
<!-- 
  <div id="fastToReply">
    <div id="motion"></div>
    <div id="replyContent">
      <textarea></textarea>
    </div>
    <div id="confirm">
   <input type="button" value="回复" action="<html:rewrite action='/control/reply/manage?method=save&author=${SESSION_LOGGED_ON_USER.loginName}&themeId=${theme.id}'/>"/>
    </div>
  </div>
 -->
 
  </body>
</html>
