<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" contentType="text/html; charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>修改个人资料</title>
    <%@ include file="/WEB-INF/page/share/commons.jsp" %>
    <link href="${basePath}/css/member_regUserUI.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
      var cc={};
cc.iswindow = /window/i.test(navigator.userAgent);
cc.islinux = /linux/i.test(navigator.userAgent);
cc.isie = /msie/i.test(navigator.userAgent);
cc.isff = /firefox/i.test(navigator.userAgent);
cc.iscr = /chrome/i.test(navigator.userAgent);
cc.isfr = /safari/i.test(navigator.userAgent);
cc.isop = /opera/i.test(navigator.userAgent);
cc.browserName = cc.isie ? "IE" : cc.isff ? "Firefox" : cc.iscr ? "Chrome" : cc.isfr ? "Safari" : cc.isop ? "Opear" : "other";
cc.browserVersion = /(?:msie|firefox|chrome|version)\D+([\d\.]+)/i.test(navigator.userAgent) ? RegExp.$1 : 0;
cc.isie6 = cc.isie && cc.browserVersion=="6.0";
 
cc.getId = function(id){
	return document.getElementById(id);
}
var upImg = {};
//初始化
upImg.init = function(){
	this.w = 100;
	this.h = 100;
}
//显示
upImg.show = function(o){
	 //if(cc.iscr){cc.getId("previewText").value = o.value;}
	var url = o.files ? o.files[0].getAsDataURL() : o.value;
	
	var img = new Image();
	
	var isLoad = false;
	img.onload = function(){
		if(isLoad) return;
		isLoad = true;
		cc.getId("userImage").src = url;
		var w=img.width, h=img.height;
		if(w>upImg.w){
			h = upImg.w/w*h;			
			w = upImg.w;
		}
		if(h>upImg.h){
			w = upImg.h/h*w;		
			h = upImg.h;
		}		
		with(cc.getId("userImage"))width=w,height=h;
		//cc.getId("previewText").value = o.value;
	}
	img.src = url;
	
}
    		
   	$(function(){
    		$("input[name=nickname]").attr("minlength", "2");
    		$("input[name=nickname]").attr("maxlength", "64");
    	});
 
 function validate(form){
  var value=form.signature.value;
  if(value.length>225) {alert("个性签名不能多于225个字符!");return false;}
  var img=form.avatarResource.value;
  if(img!=null)return ObjectManager("upload",img);
  return true;
 }
    </script>
    <script type="text/javascript">
	 <!--
	  var File={
	   upload:function(fileName){
          //上传文件的格式
          var extension=new Array('jpg','jpeg','bmp','png','gif');
          var allow=false;
          for(var i in extension){
             if(new RegExp("\\."+extension[i]+"$","i").test(fileName)) {
             allow=true;
             }
          }
          if(!allow){
           alert("请上传png, jpg, gif或者bmp格式的图片!"); 
           return false;
          }
	   }
	  };
	  function ObjectManager(method,obj){
	    return  File[method](obj);
	  }
	 //-->
	</script>   
</head>
<body>
<div align="center">
<%--上面--%>
<div class="topt">
    <div style="padding-top: 28px;" class="sm">GotoIT论坛</div>
    <div class="clearit"></div>
</div>

<%--显示表单--%>
<html:form action="/user?method=editUser" styleClass="validate"  enctype="multipart/form-data" onsubmit="return validate(this);">

   <html:hidden property="loginName"/>
    <div style="border-bottom: 0pt none;" class="topt">
        <h2 style="padding: 20px 0pt 6px;" class="f14">请完善你的个人信息，加入GotoITBBS</h2>
        <div class="blank16"></div>
        
		<%--显示错误--%>
		<div class="errorMessages"><html:errors suffix="html.br"/></div>

        <table cellspacing="0" cellpadding="0" border="0" width="100%" class="table">
            <tbody align="left">
            <tr>
                <td>登 录 名：</td>
                <td><label>${userform.loginName}</label></td>
                <td id="passport2" colspan="2">
                    <div style="width: 450px; overflow: hidden; height: 30px;" class="infor"><span></span>2-64 个字符（包括大小写字母、中文、数字、特殊字符等），注册后不可修改。</div>
                </td>
            </tr>
            <tr>
                <td width="60">昵　　称：</td>
                <td width="197"><html:text tabindex="2" styleClass="inputk required min max" property="nickname"/></td>
                <td id="passport1" colspan="2">
                    <div style="width: 450px; overflow: hidden; height: 30px;" class="infor"><span></span>2-64 个字符（包括大小写字母、中文、数字、特殊字符等）。注册后不可修改。 </div>
                </td>
            </tr>
            <tr>
                <td>电子邮件：</td>
                <td><html:text tabindex="5" styleClass="inputk required email" property="email" /></td>
                <td id="passport5" colspan="2">
                    <div style="width:190px;" class="infor"><span></span>请正确填写您的常用电子邮件</div>
                </td>
            </tr>
            <tr>
                <td>性    别：</td>
                <td>
                    <html:radio value="MALE" tabindex="7" property="gender"/> 男 
                    <html:radio value="FEMALE" property="gender"/> 女
                </td>
                <td colspan="2"></td>
            </tr>
            <bbs:permission resource="System" action="Manage" >
             <tr>
                <td>等    级：</td>
                <td>
                    <html:text property="rank"/>
                </td>
                <td colspan="2"> </td>
            </tr>
            </bbs:permission>
             <tr>
             <td>图像:</td>
             <td><img id="userImage" src="${basePath}/img/z_05.jpg"/>
             <script type="text/javascript">upImg.init();</script>
             </td>
             <td valign="bottom" align="left"><input type="file" name="avatarResource" size="30" style="height:18px;" onchange="upImg.show(this)"/>
             </td>
            </tr>
             <tr>
                <td>个性签名：</td>
                <td><html:textarea property="signature" cols="30" rows="6"></html:textarea> </td>
                <td colspan="2"><div style="width: 95px;" class="infor"><span></span>125个字符。</div></td>
            </tr>
            <tr>
                <td> </td>
                <td>
                    <button class="buttonys" value="提交" id="button" name="button" type="submit">确认修改</button>
                </td>
                <td align="right" colspan="2"> </td>
            </tr>
            <tr>
                <td> </td>
                <td> </td>
                <td colspan="2"> </td>
            </tr>
            </tbody>
        </table>
    </div>
</html:form>


<!-- =====================底部=================== -->
<%@ include file="/WEB-INF/page/share/footer.jsp"%>
</div>
</body>
</html>
