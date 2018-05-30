<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'upload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<style type="text/css">
	body{
		 background-color:#9ECDE4;
	}
	body,form{
	margin:0;
    padding:0;
	}
     .spinner{
     font-size:12px;
	 display:none;
	 }
	</style>
	<script type="text/javascript" src="<%=basePath%>/js/getScript.js"></script>
   	<script type="text/javascript">
	<!--
	  getScript("<%=basePath%>/js/jquery-1.4.4.min.js");//先加载JQuery
	//-->
	</script>
    <script type="text/javascript">
	 <!--
	  var File={
	   upload:function(fileName){
          //上传文件的格式
          var extension=new Array('jpg','jpeg','bmp','png','gif','rar','zip', 'tar', 'gz', 'jar', 'war', 'bz2', '7z', 'pdf', 'swf');
          var allow=false;
          for(var i in extension){
             if(new RegExp("\\."+extension[i]+"$","i").test(fileName)) {
             allow=true;
             }
          }
          if(!allow){
           alert("如果您上传图片，请上传png, jpg, gif或者bmp格式的图片\n如果您上传附件，请先压缩再上传"); 
           return false;
          }
	      $(".spinner").show();//显示上传中提示
	      var count=parseInt(window.parent.$("#attachment_count").val())+1;//得到正在上传附件的数目，并加一
	      window.parent.$("input[name='postTheme']").val(count+"个附件正在上传中，请上传完毕后再提交");
	      window.parent.$("#attachment_count").val(count);
	      $("form:first").submit();
	   }
	  };
	  function ObjectManager(method,obj){
	     File[method](obj);
	  }
	 //-->
	</script>   
  </head>

  <body>
  <form action="<html:rewrite action='/upload/file'/>" method="post" enctype="multipart/form-data">
   <input type='hidden' name='owner' value='theme'/>
  <input type="file" name="attachment" size='30' onchange="javascript:ObjectManager('upload',this.value)"/>
  <span class="spinner">上传中...<img src="<%=basePath%>/img/spinner.gif"/></span>
  </form>
  </body>
</html>
