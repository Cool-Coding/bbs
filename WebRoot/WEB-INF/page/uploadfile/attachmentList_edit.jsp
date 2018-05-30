<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>附件列表(可编辑)</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
	<style type="text/css">
	body{
	margin:0;
    padding:0;
    font-size:12px;
    /*background-color:#9ECDE4;*/
	}
	.name{
	font-size:13px;
	color:#006600;
	}
	.size{
	font-size:12px;
	font-weight:bold;
	}
	</style>
	<script type="text/javascript">
	 <!--
     $(function(){
	     //删除附件
	     $(".delete").children("img").css("cursor","pointer").click(function(){
	     //在页面上删除这个附件
	     $(this).parent().siblings(".name").css("text-decoration","line-through");
	     $(this).parent().hide();
	     $(this).parent().siblings(".remark").hide();
	     //移除与此附件相关的隐藏字段
	     var byname=$(this).attr("file");
	     $("#attachment_hidden").children("[value*='"+byname+"']").remove();
	     
	     var remain=parseInt($("#remained_upload_count").val())+1;
	     if(remain==1) $("#addAttachment").show();
	     $("#remained_upload_count").val(remain);
	     
	     //向服务器发出请求，删除附件
	     $.get($(this).attr("action"),{"owner":"theme","byname":byname});
	     //每删除一个附件，将附件所在框架的高度增加20
	     var frame=window.parent.$("#content");
	     frame.height(frame.height()+20);
	     });
	     //评论
	     $(".remark").children("input[type='button']").click(function(){
	            var remark=$(this).siblings('input[type="text"]').val();
	          	if((remark=remark.replace(/(^\s*)|(\s*$)/g,""))!="") {
	          	//判断是否已有该附件的评论，若有则替换已有的评论
	          	var exist=false;
	          	$("#attachment_hidden").find("input[name='remarks']").each(function(){
	          	  if($(this).val().indexOf($(this).attr("file"))!=-1){
	          	     exist=true;
	          	     $(this).val($(this).attr("file")+"*"+remark);
	          	  }
	          	});
	          	//如果不存在此附件的评论，则添加
	          	if(!exist) $("#attachment_hidden").append("<input type='hidden' name='remarks' value='"+$(this).attr("file")+"*"+remark+"'/>");
	            $(this).hide().siblings("input").hide();
	          	}
	            else alert("备注不能为空!");
	     });
	     $(".remark").children("input").hide();
	     $(".remark").children("img").css("cursor","pointer").click(function(){
	       $(this).siblings("input").toggle();
	     });
	   }); 
	 //-->
	</script>
  
  </head>
 
  <body>
   <c:forEach items="${theme.attachments}" var="attachment">
     <div><span class="name">${attachment.realname}</span><span class="size">(${attachment.size}KB)</span><span class="delete"><img title="删除附件" src="<%=basePath%>/img/close.png" file="${attachment.byname}" action="<html:rewrite action='/delete/file'/>"/></span><span class="remark"><img title="添加备注" src="<%=basePath%>/img/remark.png"/><input type="text" maxlength="128" value="${attachment.remark}"/><input type="button"  value="提交" file="${attachment.byname}"/></span></div>  
   </c:forEach>
  </body>
</html>
