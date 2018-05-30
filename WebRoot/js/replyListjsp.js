<!--
$(function(){
	 //将状态栏中的隐藏字段及其后的所有字段都居右,并将管理字段下拉菜单隐藏
	 $(".reply_hide").css({"float":"right","cursor":"pointer"}).nextAll("span").css({"float":"right","cursor":"pointer"});
	 $(".reply_hide").click(function(){
		 //var parentDiv=$(this).parents("div[class^='reply*']");
		 //parentDiv.hide("slow"); //此处不用hide时，得到的parentDiv.height()会有问题？
		 
		 var parentdiv=$(this).parent().next("div");
		 parentdiv.stop(false,true);
		 if($(this).text()=="隐藏") {
			 parentdiv.slideUp("slow");
			 $(this).text("显示");
		 }
		  else {
			  parentdiv.slideDown("slow");
			  $(this).text("隐藏");
		  }
		 //changeFrameHeightTo(parentdiv.children(".theme_detail").height());
		 
	 });
	 //设置字段注册hover事件，并将ul注册mouseout事件，并将ul li注册hover,click事件
	 $(".reply_manage").hover(function(){
		                       //如果ul上一次动画未完成，则停止
		                       var $ul=$(this).next("ul");
		                       popDropMenu($(this),$ul);
	                         },function(e){
		                       var $ul=$(this).next("ul");
		                       $ul.stop(false,true);
		                       if(!isInContainer(e,$ul))$ul.fadeOut("slow");
	                         }).next("ul").mouseout(function(e){
						    	                     if(!isInContainer(e,$(this)))$(this).fadeOut("slow");
						                           })
						                  .children("li[onmouseover]").hover(function(){
						                                   $(this).css("text-decoration","underline");
						                                   },
						                                        function(){
						                                   $(this).css("text-decoration","none");
						                                   })
						                                .click(function(){
						                                       var $li=$(this);
						                                       var li_text=$li.text();
						                                       //隐藏菜单
							                        		   $li.parent().hide();
							                        		   //点击了修改菜单====================================================
						                                       if(li_text=="修改"){
						                                    /*
						                                       var slibingDiv=$li.parents("#hr").next("div");
						                                       var replyContent=slibingDiv.find("div.theme_content");
							                        		   if(replyContent.children("textArea")[0]) {
							                        			   alert("已经在修改状态，请不要重复点击!")
							                        			   return false;
							                        		   }
							                        		   if(!slibingDiv.is(":visible")){
							                        			   slibingDiv.slideDown("slow");
							                        			   $li.parent().prevAll(".reply_hide").text("隐藏");
							                        		   }
							                        		   var width=replyContent.width()-4;
							                        		   var height=replyContent.height()-40;
							                        		   var oldhtml=replyContent.html();
							                        		   var oldtext=replyContent.text();
							                        		   var textArea="<textArea class='editReply' style='width:"+width+"px;height:"+height+"px'></textArea>" +
							                        		   "<div class='editSubmit' style='margin:0;text-align:center;margin-top:10px;'><input type='button' value='提交' style='margin:2;padding:0;'/><input type='button' value='取消' style='margin:2;padding:0;'/></div>";
							                        		   replyContent.html("").append(textArea);
							                        		   var div=$("<span></span>");
							                        		   div.html(oldhtml);
							                        		   replyContent.children("textArea").append(div);
							                        		   //给"提交"按钮注册事件
							                        		   $(".editSubmit input[type='button']").click(function(){
							                        			  var $submit=$(this);
							                        	          if($submit.attr("value")=="取消") {replyContent.html(oldhtml);return;}
							                        			  var text=$(this).parent().prev("textArea.editReply")[0].value;//此处不能用JQuery求内容，因为其求得的内容还是以前的内容。
							                        			  if(text==null || $.trim(text)=="") alert("内容不能为空!");
							                        			  if($.trim(text)==oldtext) {alert("您没有做任何修改!");return;}
							                        			   else{
							                        				var path=$("#requestPath").val()+"?method=edit&replyId="+$(this).parents("div[class^='reply*']").attr("class").substring(6);
								                                       $.ajax({
										                       	    	   type: "POST",
										                       	    	   url:path,
										                       	    	   data:"content="+text,
										                       	    	   success: function(){
										                          		     alert("修改成功！");
										                          		     //将修改结果展示出来
										                          		     replyContent.html(text);
			                                                          	    },
										                       	           error:function(){
										                       	    		 alert("修改失败,请稍后重试!");
										                       	    		 replyContent.html(oldhtml);
										                       	    	   }
										                       	    	});						                        			    
							                        			   }
							                        		   });
							                        		   return false;*/
						                                       }
						                                       //点击了其它命令菜单==================================================================
						                                       var parentDiv=$li.parents("div.replyList");
						                                       var path=$li.attr("action");
						                                       $.ajax({
								                       	    	   type: "GET",
								                       	    	   url:path,
								                       	    	   success: function(){
								                        		  if(li_text=="登陆可见") $li.text("不必登陆可见");
								                        		    else if(li_text=="不必登陆可见")$li.text("登陆可见");
								                        		     else if(li_text=="前台可见") $li.text("前台隐藏");
								                        		      else if(li_text=="前台隐藏") $li.text("前台可见");
								                          		    
								                        		    if(path!=null && path.indexOf("method=delete")!=-1) {
								                        		    	var replyList=parentDiv.children("div[class^='reply*']");

								                        		    	var liParentClass=$li.parents("div[class^='reply*']");
								                        		    	//求出本回帖的高度
								                        		    	var height=0;
								                        		    	liParentClass.children().each(function(){
								                        		    		if($(this).height()==0){
								                        		    			var tempHeight=0;
								                        		    			$(this).children().each(function(){
								                        		    				tempHeight=tempHeight>$(this).height()?tempHeight:$(this).height();
								                        		    			});
								                        		    			height+=tempHeight;
								                        		    		}else height+=$(this).height();
								                        		    	});
									                          		    changeFrameHeightTo(height);
									                          		    $li.parents("div[class^='reply*']").remove();
								                        		    }else alert("设置成功!");
	                                                          	    },
								                       	           error:function(){
								                       	    		 alert("设置失败,请稍后重试!");
								                       	    	   }
								                       	    	});
						                               });
	//弹出菜单					                                  
	 function popDropMenu(menu,dropMenu){
		 var offset=menu.offset();
		 dropMenu.stop(false,true);
		 dropMenu.css({left:offset.left,top:offset.top+menu.height()}).fadeIn("slow");
	 }
	//判断鼠标是否在弹出对话框内
	 function  isInContainer(e,obj){
	   var objoffset=obj.offset();
	   var x=e.clientX+$(document).scrollLeft();
	   var y=e.clientY+$(document).scrollTop();
	   if(x>=objoffset.left && x<=objoffset.left+obj.width() && y>=objoffset.top && y<=objoffset.top+obj.height()){
	   return true;
	   }
	   return false;
	 }
   //将iframe框架的高度调整
   function changeFrameHeightTo(height){
	    var frame=window.parent.$("#content");
	    frame.height(frame.height()-height);
   }
});
//-->
