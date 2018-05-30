<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%@ taglib uri="http://cn.yang.bbs/taglibs.tld" prefix="bbs" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>发表帖子</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=basePath%>/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.4.4.min.js"></script>
	<style type="text/css">
    body{
	  background-color:#9ECDE4;
		}
	 label{
	  font-size:13px;
	 }
	 table{
	 width:100%;
	 background-color:#F4FBFE;
	 }
	 .user,.attachment,.submit{
	 background-color:#9ECDE4;
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
	    var fields=new Array(new Field("title","帖子标题不能为空!"),new Field("content","帖子内容不能为空!"));
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
	    if(form.postTheme.value=="发表帖子" && validateForm(form)) return true;//此处应写return true;不能写form.submit(),这样将导致form提交两次
	    else return false;                                                    //若想使用form.submit();则还应加上return false;这样浏览器不会执行默认的form表单提交
	  }
	 //-->
	</script>
  </head>
  
  <body>
  <html:form action="/control/theme/manage" method="post" focus="title" enctype="multipart/form-data" onsubmit="return submitForm(this)">
    <!-- 隐藏元素method -->
    <input type="hidden" name="method" value="addTheme"/>
    <!-- 用户名称 -->
    <input type="hidden" name="author" value="${SESSION_LOGGED_ON_USER.loginName}"/>
    <html:hidden property="forumId"/>
   <table>
     <tr>
      <td><label title="用户名">用户名：</label></td>
      <td class="user"><label title="${SESSION_LOGGED_ON_USER.loginName}">${SESSION_LOGGED_ON_USER.loginName}</label></td>
     </tr>
     <tr>
      <td><label for="title" title="帖子标题">标题：</label></td>
      <td><input type="text" name="title" id="title" style="width:300px;height:20px;" maxlength="50"/></td>
     </tr>
     
     <tr>
      <td><label for="title" title="类型">类型：</label></td>
      <td>
      <html:select property="type">
       <html:optionsCollection property="themeTypes" label="name" value="value"/>
      </html:select>
      </td>
     </tr>

     <tr>
      <td><label for="isTop" title="状态">置顶：</label></td>
      <td><select name="isTop">
           <option value="true">是</option>
           <option selected="selected" value="false">否</option>
          </select>
      </td>
     </tr>
      <tr height="320px">
      <td valign="top"><label title="帖子内容">内容：</label></td>
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
      <tr id="de_second">
        <td valign="top"><label title="附件">附件:</label></td>
        <td class="attachment" valign="top">
       <jsp:include page="/WEB-INF/page/uploadfile/attachment.jsp"></jsp:include>
        </td>
      </tr>
     <tr>
	 <td colspan="2" align="center"  class="attachment"><input type="submit" value="发表帖子" name="postTheme"/></td>
	 </tr>
</table>
	</html:form>
  </body>
</html>
