<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath",basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>显示帖子</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript">
	<!--
	   //得到回帖列表
      /*function getReplyList(){
         var path="<html:rewrite action='/reply/list?themeId=${theme.id}'/>";
         $("#replyList").load(path);
       }*/
	$(function(){
	    //alert(${newReply_Page});
	    var path="<html:rewrite action='/reply/list?themeId=${theme.id}&page=${newReply_Page}'/>";
	    $("#replyList").load(path);        
	  });
	//得到第page页的回帖列表
	function topage(page){
		if(page && page>0){
		    var path="<html:rewrite action='/reply/list?themeId=${theme.id}'/>";
		    $("#replyList").load(path,{page:page});
		}
	}
    //-->
	</script>
	<style type="text/css">
	 .outDiv{
	width:1000px;
	height:100px;
	margin-left:auto;
	margin-right:auto;
	font-family:"Tahoma", "宋体";
	}
	
	/*.head{
	text-align:left;
	height:100px;
	border:solid 1px #ccc;
	}
    .bottom{
	text-align:left;
	height:100px;
	border:solid 1px #ccc;
	margin-top:10px;
	}
	*/
	
	table{
	 width:100%;
	 border:solid 2px #069;
	}
	span{
	 color:#069;
	 font-size:13px;
	 padding:0;
	 margin:3px;
	}
 
	th span{
	font-size:16px;
	}
	.user_message{
	width:200px;
	height:auto;
	float:left;
	border:solid 1px #ccc;
	margin-top:10px;
	}
	
	td{
	background-color:#EFEFEF;
	vertical-align:top;
	}
	td ul{
	list-style:none;
	text-align:left;
	margin:0;
	padding:0;
	padding-left:5px;
	font-size:14px;
	color:#059;
	}
	
	.theme_detail{
	width:1000px;
	min-height:200px;
	float:left;
	border:solid 1px #ccc;
	margin-left:16px;
	margin-top:10px;
	word-wrap:break-word;
	}
   #postReply{
   float:right;
   }
   a{
   color:#00a;
   }
   a:hover{
   color:#a00;
   }
	
	</style>
    </head>
  
  <body>
 <div align="center">
     <div class="outDiv">
   <jsp:include page="/WEB-INF/page/share/front_top.jsp"></jsp:include>
	<!-- 导航条 -->
  <jsp:include page="/WEB-INF/page/share/navigator.jsp"></jsp:include>
  <div id="postReply">
	   <a href="<html:rewrite action='/reply/manage?method=showPostUI&themeId=${theme.id}'/>" ><img alt="发表回复" src="<%=basePath%>img/reply.gif" style="border:0;cursor:pointer"/></a>
  </div>
		<table cellspacing="1">
		  <thead>
		    <tr>
		      <th colspan="2" style="background-color:#DEE3E7">
		             <span>主题:${theme.title}</span>
		       </th>
		    </tr>
		    <tr>
		       <th width="25%" style="background-color:#477AA5;">作者</th>
		       <th width="73%" style="background-color:#477AA5">正文</th>
		    </tr>
		  </thead>
	  <tbody>
	    <tr>
	    <td style="background-color:#DEE3E7;">
        <img src="<html:rewrite action='/user?method=showAvatar&loginName=${theme.user.loginName}'/>"/>
        <br/>
		<ul>
			<li>昵称：${theme.user.nickname}</li>
			<li>积分：${theme.user.score}</li>
			<li>等级：${theme.user.rank}</li>
			<li>发帖数:${theme.user.themeCount}</li>
			<li>回帖数:${theme.user.replyCount}</li>
			<li style="word-wrap:break-word;white-space:normal;word-break:break-all;">个人描述：${theme.user.signature}</li>
		</ul>
	    </td>
	    <td style="vertical-align:top">
	    <span style="float:left">发表时间:${theme.createTime}</span>
	    <span style="float:right;">浏览:${theme.readCount}次 </span>
	    <hr style="clear:both;"/>
	     ${theme.content}
	     
	     <c:if test="${fn:length(theme.attachments)>0 && SESSION_LOGGED_ON_USER eq null }">
	     <hr style="margin-top:15px;border:1px dotted #ccc;"/>
          <span style="border:1px dotted #ccc;line-height:100px;">此帖包含有附件,但只有登陆后才能查看,请<html:link action="/user?method=loginUI">登陆</html:link>或<html:link action="/user?method=regUserUI" target="_blank">注册</html:link>!</span>
         </c:if>
	    <bbs:permission resource="Attachment" action="Retrieval" >
	     <hr style="margin-top:15px;border:1px dotted #ccc;"/>
	     <c:if test="${fn:length(theme.attachments)>0}">
	             <span>附件:</span>
	     </c:if>
	     <div style="font-size:12px;margin-left:20px;">
	     <jsp:include page="/WEB-INF/page/uploadfile/attachmentList_show.jsp"></jsp:include>
	     </div>
	     </bbs:permission>
	    </td>
	    </tr>
	  </tbody>
		</table>
		
	   <div id="replyList"></div>
        <jsp:include page="/WEB-INF/page/share/front_footer.jsp"></jsp:include>
	</div>
</div>
  </body>
</html>
