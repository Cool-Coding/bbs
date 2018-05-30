<%@ page pageEncoding="utf-8" language="java" %>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<style type="text/css">
<!--
*{
margin:0;
padding:0;
}
.bottom{
clear:both;
text-align:center;
height:50px;
margin-top:10px;
vertical-align:middle;
font-size:14px;
}
#site_nav ul{
list-style-type:none;
}

#site_nav ul li{
display:inline;
}

a{
text-decoration:none;
color:blue;
}
a:hover{
color:red;
text-decoration:underline;
}
-->
</style>
<div class="bottom">
  <div id="site_nav" align="center">
    <ul>
      <li><a href="<html:rewrite action='/index/siteinfo' /> ">关于网站</a></li>
      <li><a href="mailto:riguang_2007@163.com">联系我们</a></li>
      <li class="last"><a href="#">友情链接</a></li>
    </ul>
  </div>
  <div id="copyright">
    &copy; 2011-2013 GotoIT.com.[<a href="http://localhost/bbs/">京ICP证110151号</a>]
  </div>
</div>

