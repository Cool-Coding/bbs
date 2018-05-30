<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%
     String base=request.getContextPath();
     %>
    <style type="text/css">
      ul{
      list-style-image:url("<%=base%>/img/file.png");
      background-color:#EFF6FD;
      list-style-position:inside;
      padding-left:0;
      margin:0;
      font-family:"Tahoma", "宋体";
      }
      li{
      margin:3px;
      color:#745984;
      }
      .folder{
       list-style-image:url("<%=base%>/img/folder.png");
      }
      .up{
       font-weight:bold;
       color:#DDA4CF;
       margin-bottom:5px;
       list-style-image:url("<%=base%>/img/up.png");
       text-align:center;
       font-size:14px;
      }
      #childforums{
      *width:250px;
      *height:250px;
      overflow:auto;
      margin:0;
      padding:0;
      text-align:left;
      }
    </style>
    <script type="text/javascript">
    <!--
     
     $(function(){
     $("li").mouseover(function(){
             $(this).css("cursor","pointer");
             //if($(this).text()!="上一级")$("#forumName").val($(this).text());
             })
            .click(function(){
            var object=$(this);
             var path=$("#requestPath").val();
             var forumid=$(this).attr("id");
               $.get(path,
                   {id:forumid},
                  function(data){
                  var $data=$(data);
                    if($data.find("li[class!='up']").size()>0){
                       $("#forumCascadeList").html(data);
                       }else{
                         $("input[type='hidden'][name='forumId']").val(object.attr("id"));
                         $("li").unbind("mouseover");//解除li的mouseover事件注册
                         $(".forumName").val(object.text());
                         $("#forumCascadeList").fadeOut("slow");
                         $("#searchTheme").children("[name='query']").val(false);//将查询标志设为false
                         //alert("${type}");记住不能使用alert(${type})
                         if(${type!="move"}){
                         $("form:first").submit();
                         }
                         else{
                          $("input[type='hidden'][name='method']").val("moveThemesToForum");
                          //alert($("form:first").attr("action"));
                          if($("form:first").attr("action").indexOf("control")!=-1){
                           $("form:first").attr("action","<html:rewrite action='/control/theme/manage'/>").submit();
                           }
                           else{
                           $("form:first").attr("action","<html:rewrite action='/theme/manage'/>").submit();
                           }
                         }
                       }
                   },
                   "html");
            });
     });
     //-->
     </script>
  </head>
  
  <body>
  <!-- 请求的路径 -->
  <c:if test="${!empty type}">
    <input type="hidden" id="requestPath" value="<html:rewrite action='/ajax/forum/cascade/list?method=getForumCascadeList&type=move'/>"/>
  </c:if>
  <c:if test="${empty type}">
    <input type="hidden" id="requestPath" value="<html:rewrite action='/ajax/forum/cascade/list?method=getForumCascadeList'/>"/>
  </c:if>

  <div id="childforums">
    <ul>
      <c:if test="${param.id>0}"><li id="${parentforum}" class="up" title="返回上一级">上一级<hr style="color:#0ff;"/></li></c:if>
      <c:forEach items="${forums}" var="forum">
      <li id="${forum.id}" style="font-size:15px" title="${forum.name}" <c:if test="${fn:length(forum.children)>0}">class="folder"</c:if>>${forum.name}</li>
      </c:forEach>
     <c:if test="${param.id>0}"><hr style="color:#0ff;"/><li id="${parentforum}" class="up" title="返回上一级">上一级</li></c:if>
    </ul>
   </div>
  </body>
</html>
