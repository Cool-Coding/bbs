<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>上传结果</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
	<style type="text/css">
	body{
	margin:0;
    padding:0;
    font-size:12px;
    background-color:#9ECDE4;
	}
	#name{
	font-size:13px;
	color:#006699;
	}
	#size{
	font-weight:bold;
	}
	</style>
	<script type="text/javascript">
	 <!--
	   window.parent.$("#attachment_hidden").append("<input type='hidden' name='attachments' value='${attachment.realname}*${attachment.byname}*${attachment.size}'/>"); 
        
        var count=parseInt(window.parent.$("#attachment_count").val())-1;//#attachment_count为正在上传的附件的个数
        window.parent.$("#attachment_count").val(count);
        if(count==0){
            var pagetitle=window.parent.$("title").html();//不能用window.parent.$("title:first").text();这对于IE不起作用
	        switch(pagetitle){
	         case "发表帖子": window.parent.$("input[name='postTheme']").val("发表帖子");break;
	         case "修改帖子": window.parent.$("input[name='postTheme']").val("确认修改");break;
	         case "回复帖子": window.parent.$("input[name='postTheme']").val("回复");break;
	         default: null;
	        }
	    }else{
	     window.parent.$("input[name='postTheme']").val(count+"个附件正在上传中，请上传完毕后再提交");
	    }

	   
	   $(function(){
	     //删除附件
	     $("#delete").children("img").css("cursor","pointer").click(function(){
	     //在页面上删除这个附件
	     $("#name").css("text-decoration","line-through");
	     $("#delete").hide();
	     $("#remark").hide();
	     //移除与此附件相关的所有的隐藏字段
	     window.parent.$("#attachment_hidden").children("value*='${attachment.byname}'").remove();
	     
	     var remain=parseInt(window.parent.$("#remained_upload_count").val())+1;//remained_upload_count为还可以上传附件的个数
	     if(remain==1) ;window.parent.$("#addAttachment").show();
	     window.parent.$("#remained_upload_count").val(remain);
	     //向服务器发出请求，删除附件
	     $.get($(this).attr("action"),{owner:"theme",byname:"${attachment.byname}"})
	     //每删除一个附件，将附件所在框架的高度增加20
	     var frame=window.parent.parent.$("#content");
	     frame.height(frame.height()+20);
	     });
	     //评论
	     $("#remark").children("input[type='button']").click(function(){
	            var remark=$(this).siblings('input[type="text"]').val();
	          	if((remark=remark.replace(/(^\s*)|(\s*$)/g,""))!="") {
	          	//判断是否已有该附件的评论，若有则替换已有的评论
	          	var exist=false;
	          	window.parent.$("#attachment_hidden").find("input[name='remarks']").each(function(){
	          	  if($(this).val().indexOf("${attachment.byname}")!=-1){
	          	     exist=true;
	          	     $(this).val("${attachment.byname}*"+remark);
	          	  }
	          	});
	          	//如果不存在此附件的评论，则添加
	          	if(!exist) window.parent.$("#attachment_hidden").append("<input type='hidden' name='remarks' value='${attachment.byname}*"+remark+"'/>");
	            $(this).hide().siblings("input").hide();
	          	}
	            else alert("备注不能为空!");
	     });
	     $("#remark").children("input").hide();
	     $("#remark").children("img").css("cursor","pointer").click(function(){
	       $(this).siblings("input").toggle();
	     });
	   }); 
	 //-->
	</script>
  
  </head>
 
  <body>
     <div><span id="name">${attachment.realname}</span><span id="size">(${attachment.size}KB)</span><span id="delete"><img title="删除附件" src="<%=basePath%>/img/close.png" action="<html:rewrite action='/delete/file'/>"/></span><span id="remark"><img title="添加备注" src="<%=basePath%>/img/remark.png"/><input type="text" maxlength="128"/><input type="button"  value="提交"/></span></div>
  </body>
</html>
