<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>附件模块</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	
	<style type="text/css">
     #attachment_ul ul{
	 font-size:12px;
	 border:1px dotted #F4FBFE;
	 padding:0;
	 width:500px;
	 margin-left:0;
	 }
	 #attachment_ul li{
	 list-style-position:inside;
	 padding-left:10px;
	 }
	</style>
    <script type="text/javascript">
	 <!--
	  $(function(){
	   $("#addAttachment").click(function(){
	     var remain=parseInt($("#remained_upload_count").val())-1;
	     if(remain>=0){
	     var file=$("div.attachment:last");
	     var clonefile=file.clone();
         //复制上传文件所在的div.
	     clonefile.insertAfter(file);
	     $("#remained_upload_count").val(remain);
	     //如果页面上已经有四个上传控件，则每再加一个上传控件，附件的行宽增加20
	     //if(${attachment_count}-remain>4) {
	              var frame=window.parent.$("#content");
	              frame.height(frame.height()+18);
	       //       }
	     if(remain==0) $(this).hide();//如果已经有三个附件了则移除增加附件按钮。
	     }
	   });
	  });
	  //-->
	  </script> 
  </head>
  
  <body>
     <!--保存附件信息格式为"真实名称_别名_大小"和"别名_评论" -->
    <div id="attachment_hidden">
    <!-- 正在上传的附件数 -->
    <input type="hidden" id="attachment_count" value="0"/>
    <input type="hidden" id="remained_upload_count" value="${attachment_count-1}"/>
    
    </div>
     <div class="attachment">
     <iframe frameborder="0" scrolling="no" resizable="no" allowtransparency="true" cellspacing="0" border="0" style="border: 0px none; height: 23px;width:100%;" src="<html:rewrite action='/getpage/upload'/>"></iframe>
     </div>
     <input type="button" value="增加附件" id="addAttachment"/>
     <div id="attachment_ul">
        <ul>
		  提示信息：
		  <li>上传文件请压缩后再上传，允许zip, rar, gz, tar, bz2, 7z, jar, war格式的压缩文件</li>
		  <li>上传图片推荐使用png, jpg, gif等类型</li>
		  <li>文件大小不能超过${attachment_size}MB</li>
		  <li>最多允许上传${attachment_count}个附件</li>
		</ul>
     </div>
  </body>
</html>
