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
    <title>发表帖子</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=basePath%>ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.4.4.min.js"></script>
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
	width:1000px;
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
   text-decoration:none;
   }
   a:hover{
   color:#a00;
   text-decoration:underline;
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
	    var fields=new Array(new Field("title","帖子标题不能为空!"),new Field("forumId","帖子所属版块为必填项!"),new Field("content","帖子内容不能为空!"));
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
 <div align="center">
   <div class="outDiv">
   <jsp:include page="/WEB-INF/page/share/front_top.jsp"></jsp:include>

	<!-- 导航条 -->
	<c:set var="out" value=""/>
	<c:forEach items="${parents}" var="menu" varStatus="loop"><!-- 注意items属性的设置，必须有{}-->
    <c:set var="out" value="&nbsp;->&nbsp;<a href='${basePath}get/forum/theme.do?forumId=${menu.id}'>${menu.name}</a>${out}"/>
	</c:forEach>
  <div id="navigator">
	<div style="margin:0;padding:0;float:left;">
	<c:if test="${out!=null && out!=''}"><a href="${basePath}index.do">论坛首页</a></c:if>
	<c:if test="${out==null || out==''}">论坛首页</c:if>
	<c:out value="${out}" escapeXml="false"/>
	</div>
	<br/>
   <div style="float:left;margin-left:15px;">
          子版块:&nbsp;&nbsp;
   <a href="<html:rewrite action='/get/forum/theme?forumId=${currentForum.id}'/> ">全部</a>&nbsp;&nbsp;
     <c:forEach items="${currentForum.children}" var="child" varStatus="loop">
          <a href="<html:rewrite action='/get/forum/theme?forumId=${child.id}'/> ">${child.name}</a>&nbsp;&nbsp;
     </c:forEach>       
   </div>
</div>

  <html:form action="/theme/manage" method="post" focus="title" enctype="multipart/form-data" onsubmit="javascript:return submitForm(this)">
    <!-- 隐藏元素method -->
    <input type="hidden" name="method" value="add"/>
    <!-- 用户名称 -->
    <input type="hidden" name="author" value="${SESSION_LOGGED_ON_USER.loginName}"/>
   <table>
     <tr>
      <td><label title="用户名">用户名：</label></td>
      <td  class="user"><label title="${SESSION_LOGGED_ON_USER.nickname}" style="color:#5F23B6">${SESSION_LOGGED_ON_USER.nickname}</label></td>
     </tr>
     <tr>
      <td><label for="title" title="帖子标题">标题：</label></td>
      <td><input type="text" name="title" id="title" style="width:300px;height:20px;" maxlength="50"/></td>
     </tr>
      <tr>
      <td><label for="forum" title="所属版块">所属版块：</label></td>
      <td>
        <html:select property="forumId">
          <option selected="selected" value="">--请选择分类--</option>
          <html:optionsCollection name="themeform" property="siblingForums" label="name" value="id"/>
        </html:select>
      </td>
     </tr>
     <bbs:permission resource="System" action="Manage" >
     <tr>
      <td><label for="isTop" title="状态">置顶：</label></td>
      <td><select name="isTop">
           <option value="true">是</option>
           <option selected="selected" value="false">否</option>
          </select>
      </td>
     </tr>
     </bbs:permission>
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
	        filebrowserUploadUrl : '<%=basePath%>ckeditor/uploader/upload.jsp',
	        filebrowserImageUploadUrl : '<%=basePath%>ckeditor/uploader/upload.jsp?type=Images',
	        filebrowserFlashUploadUrl : '<%=basePath%>ckeditor/uploader/upload.jsp?type=Flashs'
		}
		);
		}
		</script>
	 </td>
      </tr>
       <bbs:permission resource="Attachment" action="Create" >
      <tr id="de_second">
        <td valign="top"><label title="附件">附件:</label></td>
        <td class="attachment" valign="top">
       <jsp:include page="/WEB-INF/page/uploadfile/attachment.jsp"></jsp:include>
        </td>
      </tr>
      </bbs:permission>
     <tr>
	 <td colspan="2" align="center"  class="attachment"><input type="submit" value="发表帖子" name="postTheme"/></td>
	 </tr>
</table>
	</html:form>
 <jsp:include page="/WEB-INF/page/share/front_footer.jsp"></jsp:include>
 </div>
</div>
  </body>
</html>
