<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>系统设置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
    <style type="text/css">
    body{
      font-family:"Tahoma", "宋体";
      font-size:14px;
    }
    ul{
    list-style-type:none;
    padding:0;
    margin:0;
    padding-left:10px;
    }   
   .title{
   width:50%;
   background-color:#E1F2FA;
   }
   input{
    width:25px;
   }
    </style>
  </head>
  <body>
  <html:form action="/manage/config">
 
    <input type="hidden" name="method" value="save"/>
        <div id="control">
           <div class="title">后台设置</div>
             <ul>
              <li>
                                         禁用父版块时是否禁用所有子版块
                <td><html:checkbox property="disableAllChildren_forum"></html:checkbox>
              </li>
              <li>
                                       启用父版块时是否启用所有子版块
              <html:checkbox property="enableAllChildren_forum"></html:checkbox>
              </li>
              <li>
                                      简单版块列表的列数
                <html:text property="viewPageRecordsCols_simpleForum"></html:text>
              </li>
              <li>
                                     每页所显示的记录数(版块、帖子列表)
                <html:text property="viewPageRecords"></html:text>
              </li>
              <li>
                                     每页显示页码数
                <html:text property="viewPageCount"></html:text>
              </li>
              <li>
                                     回帖列表页面显示的回帖数
                <html:text property="replyCount_perPage"></html:text>
              </li>
              <li>
                                     显示回帖时是否将不可见的回帖显示出来
                <html:checkbox property="show_invisible_reply"></html:checkbox>
              </li>
             </ul>
        </div>
        <div id="front">
          <div class="title">前台设置</div>
          <ul>
          <li>
                           每页所显示的记录数(版块、帖子列表)
          <html:text property="front_viewPageRecords"></html:text>
          </li>
          <li>
                          每页显示页码数
          <html:text property="front_viewPageCount"></html:text>
          </li>
          <li>
                             回帖列表页面显示的回帖数
          <html:text property="front_replyCount_perPage"></html:text>
          </li>
          <li>
                            会员每发表一个帖子增加的分数
           <html:text property="front_score_per_theme"></html:text>
          </li>
          <li>
                            会员每发表一个回复增加的分数
           <html:text property="front_score_per_reply"></html:text>
          </li>
          </ul>
        </div>
        <div id="common">
         <div class="title">通用设置</div> 
         <ul>
          <li>
                                 会员头像的最大宽度X高度
            <html:text property="avatar_max_width" size="3"></html:text>px X <html:text property="avatar_max_height" size="3"></html:text>px
          </li>
          <li>
                                 附件允许上传的大小
            <html:text property="attachment_size"></html:text>M
          </li>
          <li>
                                 允许用户上传附件的个数
            <html:text property="attachment_count"></html:text>
          </li>
          <li>
                                  删除帖子时是否也删除相应的附件
            <html:checkbox property="theme_delete_for_attachment_delete"></html:checkbox>
          </li>
          <li>
                                  用户上传附件之后，删除时是否进行物理删除
            <html:checkbox property="attachment_delete_for_user"></html:checkbox>
          </li>         
         </ul>
        </div>
      <div><input type="submit" value="提交" style="width:40px;margin-top:15px;"/></div>
</html:form>
</body>
</html>
