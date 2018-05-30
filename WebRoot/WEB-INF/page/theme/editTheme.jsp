<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改帖子</title>
    
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
	    if(form.postTheme.value=="确认修改" && validateForm(form)) return true;//此处应写return true;不能写form.submit(),这样将导致form提交两次
	    else return false;                                                    //若想使用form.submit();则还应加上return false;这样浏览器不会执行默认的form表单提交
	  }
	 //-->
	</script>
  </head>
  
  <body>
  <html:form action="/control/theme/manage" method="post" focus="title" enctype="multipart/form-data" onsubmit="return submitForm(this)">
    <!-- 隐藏元素method -->
    <input type="hidden" name="method" value="editTheme"/>
    <!-- 用户名称 -->
    <input type="hidden" name="author" value="${theme.user.loginName}"/>
    <!-- 所属版块ID -->
    <html:hidden property="forumId"/>
    <!-- 帖子ID -->
    <html:hidden property="themeId"/>
   <table>
     <tr>
      <td><label title="用户名">用户名：</label></td>
      <td  class="user"><label title="${theme.user.loginName}">${theme.user.loginName}</label></td>
     </tr>
     <tr>
      <td><label for="title" title="帖子标题">标题：</label></td>
      <td><input type="text" name="title" id="title" style="width:300px;height:20px;" maxlength="50" value="${theme.title}"/></td>
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
           <option <c:if test="${theme.top}">selected="selected"</c:if> value="true">是</option>
           <option <c:if test="${!theme.top}">selected="selected"</c:if> value="false">否</option>
          </select>
      </td>
     </tr>
      <td><label for="forum" title="所属版块">所属版块：</label></td>
      <td><html:text property="forumName" disabled="true"/><input type="button" value="修改" id="changeForum" onclick='javascript:window.open("<html:rewrite action='/control/forum/list'/>?simpleForumList=true&id=${themeform.forumId}&other=true","板块列表","1440px","200px")'/>
      </td>
     </tr>
      <tr height="320px">
      <td valign="top"><label title="帖子内容">内容：</label></td>
      <td><textarea name="content">${theme.content}</textarea>
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
       <jsp:include page="/WEB-INF/page/uploadfile/attachment_edit.jsp"></jsp:include>
        </td>
      </tr>
     <tr>
	 <td colspan="2" align="center"  class="attachment"><input type="submit" value="确认修改" name="postTheme"/></td>
	 </tr>
</table>
	</html:form>
  </body>
</html>
