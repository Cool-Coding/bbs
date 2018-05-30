<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
<!--
  .fenye{
  font-size:12px;
  float:right;
  margin-right:10px;
  margin-top:5px;
  margin-bottom:5px;
  }
-->
</style>
<script type="text/javascript">
<!--
 $(function(){
  $("table#replies tbody tr:odd span[class]").css("cursor","pointer")
                                        .hover(function(){
                                        $(this).css("text-decoration","underline");
                                        },function(){
                                        $(this).css("text-decoration","none");
                                        })
                                        .click(function(){
                                         var obj=$(this);
                                         var path="<html:rewrite action='/reply/manage'/>";
                                         var replyId=obj.parent().attr("target");
                                         //path=path+"?method="+$(this).attr("class")+"&replyId="+replyId;
                                         var method=obj.attr("class");
                                         $.get(path,{"replyId":replyId,"method":method},function(data){
                                          alert(data);
                                          if(method=="delete" || method=="hide") obj.parents("tr").hide().prev("tr").hide();
                                          if(method=="onlyLogin") obj.attr("class","notLogin").text("登陆可见(解除)");
                                          if(method=="notLogin")  obj.attr("class","onlyLogin").text("登陆可见");
                                         },"text");
                                        });
 });
//-->
</script>
<div style="margin-top:10px;">
  <span style="margin:0;padding:0;float:left;">回帖数:${pageView.totalRecord}</span>
  <!-- 分页 -->
  <div class="fenye">
     <%@include file="/WEB-INF/page/share/simple-fenye.jsp"%>
  </div>
</div>
	<table cellspacing="2" style="clear:both;" id="replies">
	<tbody>
     <c:forEach items="${pageView.records}" var="entity" varStatus="status">
	    <tr>
	    <td width="25%" style="background-color:#DEE3E7">
       <img src="<html:rewrite action='/user?method=showAvatar&loginName=${entity.user.loginName}'/>"/>
        <br/>
		<ul>
			<li>昵称：${entity.user.nickname}</li>
			<li>积分：${entity.user.score}</li>
			<li>等级：${entity.user.rank}</li>
			<li>发帖数:${entity.user.themeCount}</li>
			<li>回帖数:${entity.user.replyCount}</li>
			<li style="word-wrap:break-word;word-break:break-all;">个人描述：${entity.user.signature}</li>
		</ul>
	    </td>
	    <td width="73%">
		<span>${pageView.viewPageRecords*(replyform.page-1)+status.count}楼</span>
	    <span>发表时间:${entity.createTime}</span>
	    <br/>
	    <hr/>
	    <c:if test="${!entity.onlyLogin || (entity.onlyLogin && SESSION_LOGGED_ON_USER ne null)}">
	    ${entity.content}
	    </c:if>
	    <c:if test="${entity.onlyLogin && SESSION_LOGGED_ON_USER eq null }">
          <span style="border:1px dotted #ccc;line-height:100px;">此回帖只有登陆后才能查看,请<html:link action="/user?method=loginUI">登陆</html:link>或<html:link action="/user?method=regUserUI" target="_blank">注册</html:link>!</span>
         </c:if>
	    </td>
	    </tr>
	    <bbs:permission resource="Reply" action="Update" >
	    <bbs:moderatorIdentify forum="${entity.theme.forum}">
	    <tr>
	      <td colspan="2" style="background-color:#D1D7DC;" target="${entity.id}"><span class="delete">删除</span><span class="hide">隐藏</span><span><html:link action="/reply/manage?method=showEditUI&replyId=${entity.id}&page=${pageView.currentPage}" style="color:#069;">编辑</html:link></span><span class="onlyLogin">登陆可见</span></td>
	    </tr>
	    </bbs:moderatorIdentify>
	    </bbs:permission> 
	</c:forEach>
	  </tbody>
	</table>
  <!-- 分页 -->
  <div class="fenye">
     <%@include file="/WEB-INF/page/share/simple-fenye.jsp"%>
  </div>
