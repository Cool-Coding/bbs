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
    <title>发表回复</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=basePath%>/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
	<style type="text/css">
	 label{
	  font-size:14px;
	 }
	 table{
	 width:100%;
	 background-color:#F4FBFE;
	 }
	 .user,.attachment,.submit{
	 background-color:#9ECDE4;
	 }
	 .outDiv{
	width:1050px;
	height:auto;
	margin-left:auto;
	margin-right:auto;
	}
   #navigator{
    color:#00a;
    font-family:"微软雅黑,幼圆";
    font-size:13px;
    margin:5px;
    margin-bottom:30px;
    clear:both;
   }
   a{
   color:#00a;
   }
   a:hover{
   color:#a00;
   }
   form{
   clear:both;
   }
	</style>
	<script type="text/javascript">
	 <!--
	  //为String添加一个清楚字符串前后空格的方法
	  String.prototype.trim=function(){
	      return this.replace(/(^\s*)|(\s*$)/g,"");
	  }
	  //表单的域 name：元素的name,value：为空时的提示语
	  function Field(name,value){
	   this.name=name;
	   this.value=value;
	  }
	  //校验表单是否相关的域为空
	  function validateForm(form){
	    var fields=new Array(new Field("content","回复内容不能为空!"));
	    for(var field in fields){
	      var name=fields[field].name;
	      var value=eval("form."+name).value.trim();
	      if(name!="content" && value=="")
	      {
		      alert(fields[field].value);
		      return false;
	      }
	      var contentbody=$("#cke_contents_content").children("iframe").contents();
	      if(name=="content" && contentbody.find("body").text().trim()=="" && contentbody.find("body img").size()<=0)
	         {
	              alert(fields[field].value);
                  return false;
	         }
	     }
	   return true;	    
	  }
	  //点击发表帖子按钮时，首先执行此方法
	  function submitForm(form){
	    if(form.postTheme.value=="回复" && validateForm(form)) return true;//此处应写return true;不能写form.submit(),这样将导致form提交两次
	    else return false;                                                    //若想使用form.submit();则还应加上return false;这样浏览器不会执行默认的form表单提交
	  }
	 //-->
	</script>
  </head>
  
  <body>
  <div align="center">
   <div class="outDiv">
<jsp:include page="/WEB-INF/page/share/front_top.jsp"></jsp:include>

	<!-- 导航条 -->
  <jsp:include page="/WEB-INF/page/share/navigator.jsp"></jsp:include>

  <html:form action="/reply/manage" method="post" enctype="multipart/form-data" onsubmit="javascript:return submitForm(this)">
    <!-- 隐藏元素method -->
    <input type="hidden" name="method" value="post"/>
    <!-- 用户名称 -->
    <input type="hidden" name="author" value="${SESSION_LOGGED_ON_USER.loginName}"/>
   <!-- 回复所在的帖子-->
   <html:hidden property="themeId"/>
   <table>
      <tr height="320px">
      <td width="5%"></td>
      <td><textarea name="content"></textarea>
		<script type="text/javascript">
		if (typeof CKEDITOR == 'undefined') {
	          document.write('加载CKEditor失败');
	       }
		else {
		CKEDITOR.replace('content',
		{
	        filebrowserUploadUrl : '<%=basePath%>/ckeditor/uploader/upload.jsp',
	        filebrowserImageUploadUrl : '<%=basePath%>/ckeditor/uploader/upload.jsp?type=Images',
	        filebrowserFlashUploadUrl : '<%=basePath%>/ckeditor/uploader/upload.jsp?type=Flashs'
		}
		);
		}
		</script>
	 </td>
      </tr>
     <tr>
	 <td colspan="2" align="center"  class="attachment"><input type="submit" value="回复" name="postTheme"/></td>
	 </tr>
</table>
	</html:form>
<jsp:include page="/WEB-INF/page/share/front_footer.jsp"></jsp:include>
 </div>
</div>
  </body>
</html>
