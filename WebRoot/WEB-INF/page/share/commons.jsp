<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" //
			+ request.getServerName() //
			+ ":" + request.getServerPort()//
			+ path;
	request.setAttribute("basePath", basePath);
%><%-- 通用的js与css --%>
<script type="text/javascript" src="${basePath }/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }/widgets/jquery.validation/jquery.validate.js"></script>
<script type="text/javascript" src="${basePath }/widgets/jquery.blockui/jquery.blockUI.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath }/css/manage_common.css">
<link rel="stylesheet" type="text/css" href="${basePath }/css/share.css">
<script type="text/javascript">
	var basePath = "${basePath}";  // 应用程序的根路径 */
	$(function(){
		if( $("form.validate").size() > 0 ){
			// 需要验证的要在验证成功后block
			$.validator.setDefaults({
				submitHandler: function() { 
					this.submit(); // 这行在执行时会出错，但这样可以导致提交表单，怎么正确提交表单呢？
					$.blockUI({ message: '<p style="font-size: 16px;font-weight: bold;">正在提交，请稍候...</p>' }); 
				}
			});
			$.validator.messages = {
				required: "　请填写本字段的信息.",
				remote: "Please fix this field.",
				email: " 请填写正确的email地址.",
				url: "Please enter a valid URL.",
				date: "Please enter a valid date.",
				dateISO: "Please enter a valid date (ISO).",
				number: "Please enter a valid number.",
				digits: "Please enter only digits.",
				creditcard: "Please enter a valid credit card number.",
				equalTo: "　请填写相同的值.",
				accept: "Please enter a value with a valid extension.",
				maxlength: $.validator.format("Please enter no more than {0} characters."),
				minlength: $.validator.format("　最小长度为 {0} 个字符."),
				rangelength: $.validator.format("Please enter a value between {0} and {1} characters long."),
				range: $.validator.format("Please enter a value between {0} and {1}."),
				max: $.validator.format("Please enter a value less than or equal to {0}."),
				min: $.validator.format("Please enter a value greater than or equal to {0}.")
			}
			// class为validate的表单都要验证
			$("form.validate").validate();
		}
		// 不需要验证的提交后就block
		$("form:not(.validate)").submit(function(){
			$.blockUI({ 
				message: '<p style="font-size: 16px;font-weight: bold;">正在提交，请稍候...</p>' 
			});
		});
		// 超链接执行的功能也block（在另外窗口中打开的除外）
		// :not([target]) 指定的target
		// :not([href=#]) 刷新本页，一般是还未最终完成的超链接
		// :not([href^=javascript]) 调用JavaScript函数
		// :not([onclick]) 在点击时调用JS函数。如删除时，选取消也会block。这个效果由confirmDel()完成
		// :not(a:has(img)) 超链接在一个图片上，没有指定target，在点击时也会在新窗口中打开。使用FCKeditor发表内容时可能会出现这样的情况：。
		// :not(div.content a) 在显示文章内容时出现的a，不block
		$("a:not([target]):not([href=#]):not([href^=javascript]):not([onclick]):not(a:has(img)):not(div.content a)").click(function(){
			$.blockUI({ 
				message: '<p style="font-size: 16px;font-weight: bold;">正在执行，请稍候...</p>' 
			});
		});
 })
	function confirmDel( message ){
		if( message == null ){ 
			message = "您确定要删除一条记录吗？";
		}
		if( window.confirm( message ) ){
			$.blockUI({ 
				message: '<p style="font-size: 16px;font-weight: bold;">正在执行，请稍候...</p>' 
			});
			return true;
		}else{
			return false;
		}
	}
</script>

