var frame;
var frameHeight=0;
$(document).ready(function(){
	//注册文本框的onfocus和onmouseout事件
	$(".forumName").click(function(){

		if(this.id=="moveTheme"){
			if(!validateForm(this.form)) return false;
		}
		var offset=$(this).offset();
		if(navigator.appName=="Netscape"){
			$("#forumCascadeList").css({display:"block",position:"absolute","z-index":"100",top:offset.top+$(this).height()+"px",left:offset.left+"px"})
            .load($(this).attr("action"));
		}else{
			$("#forumCascadeList").css({display:"block",position:"absolute","z-index":"100",top:offset.top+$(this).height()+5+"px",left:offset.left+"px"})
            .load($(this).attr("action"));
		}
	}).mouseout(function(e){
		e=e||window.event;
		if(!isInContainer(e,$("#forumCascadeList"))) $("#forumCascadeList").fadeOut("slow");
	});
	//注册鼠标移出列表事件
	$("#forumCascadeList").mouseout(function(e){
		e=e||window.event;
		if(!isInContainer(e,$(this))) $(this).fadeOut("slow");
	})
	//注册查询按钮事件
	$("#searchTheme").children("input[type='button']").click(function(){
		var value=$(this).prev().val();
		//if($.trim(value)=="") {alert("查询条件不能为空!");return false;}
		//将查询标志设置为true
		$(this).siblings("[name='query']").val(true);
		$("form:first").submit();
	});
	//注册“显示回帖”链接的点击事件
	frame=window.parent.$("#content");
    var flag=true;//执行一次的标志
	$("#showReply").click(function(){
		var labelReply=$(this);
		if(flag) {frameHeight=frame.height();flag=false;}
		var replies=$("#replies");
		if(replies.is(":visible")){
		 replies.hide();
		 if(labelReply.text()=="显示回复") labelReply.text("隐藏回复");
			else labelReply.text("显示回复");
		 frame.height(frameHeight);
		}else{
          /*replies.load($(this).attr("action"),function(){
		    //将回帖所在框架的高度增加
			//0.1毫秒之后再改变frame的调度，这样replies.height的高度才稳定
			setTimeout(function(){
			    frame.height(frameHeight+replies.height());
			    replies.show("slow");
			},100);
		});*/
		$.get($(this).attr("action"),function(data){
			if($(data).find(".user_message").size()<=0) {
				alert("本帖没有回帖!")
				return false;
			}
			else{
			if(labelReply.text()=="显示回复") labelReply.text("隐藏回复");
			 else labelReply.text("显示回复");
			replies.html(data);
			replies.show();//此处的显示用立即显示，否则得到的高度不正确。
			setTimeout(function(){
				frame.height(frameHeight+replies.height());	
			},200);
			//调整回帖内容高度不足200px的为200px
			//注意不能用replies.children(".theme_detail")，此方法只会在直接子结点中找，不会再往下找。
           /*replies.find(".theme_detail").each(function(){
			var content=$(this);
		    var contentHeight=content.height();
			content.height(contentHeight<200 ? 200+"px" : contentHeight+"px");
			});*/
			//此处注释的原因是当在回复列表中点击下一页时不调用这个方法，那样就无法自动修改回帖内容的高度了。
			}
		},"html");
	}
	});
	//发表回复
/*	$("#postReply").click(function(){
		var post_reply=$("#post_reply");
		var postReply=$(this);
		$.get($(this).attr("action"),function(data){
			post_reply.html(data);
			post_reply.show();//此处的显示用立即显示，否则得到的高度不正确。
			setTimeout(function(){
				frame.height(frameHeight+post_reply.height());	
			},200);
		},"html");
	});*/
	//回帖按钮点击事件
	$("#confirm input[type='button']").click(function(){
	  //1.判断力回帖内容是否为空，若为空则提出警告
	      var content_client=$.trim($("#replyContent textArea").val());
	      if(content_client==""){
	    	  alert("回帖内容不能为空!");
	    	  return false;
	      }
	  //2.向服务器提出保存回帖的请求
	 //3.根据服务器返回的信息，给用户反馈结果信息
	      var path=$(this).attr("action");
	      $.ajax({
	    	   type: "POST",
	    	   url: path,
	    	   data:"content="+content_client,
	    	   success: function(){
	    	    $("#replyContent textArea").val("");
	    	     alert("回复成功!");
	    	   },
	           error:function(){
	    		 alert("回复失败,请重试!");
	    	   }
	    	});
	});
});
//到指定分页页面
function topage(page){
 var replies=$("#replies");
 
 if( page && page>=1){
	 $.get($("#showReply").attr("action"),{page:page},function(data){
			replies.html(data);
		    frame.height(frameHeight+replies.height());
		},"html");
/*	 var path=$("#showReply").attr("action");
     $.ajax({
  	   type: "POST",
  	   url: path,
  	   data:"page="+page,
  	   error: function(XMLHttpRequest, textStatus, errorThrown){
  	     alert( "Data Saved: " +textStatus);
  	   },
       success:function(){
		replies.html(data);
		frame.height(frameHeight+replies.height());
  	   }
  	});*/
 }
}

