jQuery(function($){
	//$("body").css("width","1220px");
	//$("#body").css("margin-left",(screen.availWidth-$("#body").width())/2+"px");
	//为li分别添加当鼠标在其上的样式的离开时的样式
	$("li").hover(
	function(){
	if($(this).attr("class")!="selected")$(this).addClass("hover");
	$(this).css("cursor","pointer");
	},
	function(){
	if($(this).attr("class")!="selected")$(this).removeClass("hover");
	}
	)
	.click(function(){
		$(this).removeClass().addClass("selected").css("color","white")
		       .siblings("li").removeClass().css("color","#03f");
		
	});
	//iframe高度自适应
	$("#content").load(function(){
		 $(this).height(0);
		 $(this).height($(this).contents().height()<450 ? 450 : $(this).contents().height());   
	});
});
