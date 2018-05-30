$(function(){
//初使化按钮的样式
$("input[id][type='button']").width("60px")
                             .height("25px")
                             .css("font-family","Tahoma ,宋体")
                             .css("background-color","#9BCAE2")
                             .css("font-size","14px");
//页面初使化完毕之后，若页面没有数据，则将除了添加按钮之外其它按钮禁用
if($("tbody tr").size()<=1) $("#lastTr input[type='button']:not(#add)").attr("disabled",true);
//寻找具有action属性的按钮
$entityManage=$("input[type='button'][action]");
$entityManage.click(function(){
		 //若点击了按钮，则做两件事
		 //1.弹出模态对话框
		 //2.注册弹出对话框的移动事件
	   //--------------------------------------------------
		 $("#ForumManagerDialog").show()
		                         .load($(this).attr("action"))
		                         .modal();
		//--------------------------------------------------
		 moveDialog();
	 });
/*#######################################################################*/
//=======================对复选框的一系列操作JS=======================
//单击全选复选框触发的事件
$("input[name='selectAll']").click(function(){
   //首先使禁用和启用按钮可用
   $("#enable").attr("disabled",false);
   $("#disable").attr("disabled",false);
   //根据是否全选来改变每行记录的颜色和每行前面的复选框
   if($(this).attr("checked")){
	   $("input[name$='Ids']:enabled").attr("checked",true).parents("tr").addClass("checked");
   }else{
	   $("input[name$='Ids']:enabled").attr("checked",false).parents("tr").removeClass("checked");
   }
   //判断列表中可用版块个数是否只有一个
   var $forums=$("input[name$='Ids']:checked");
   if($forums.size()==1){
		if($forums.parent().siblings().eq(1).text()=="可用"){//不有使用$forums(0)或$forums.get(0)，因为这样会得到DOM对象，不再是jQuery对象
			  $("#enable").attr("disabled",true);
			  $("#disable").attr("disabled",false);
			}else{
		      $("#enable").attr("disabled",false);
			  $("#disable").attr("disabled",true);	
			}
   }
});
//全选
$("a[name='selectAll']").click(function(){
	$("input[name$='Ids']:enabled").attr("checked",true).parents("tr").addClass("checked");
	$("input[name='selectAll']").attr("checked",true);
});
///反选
$("a[name='selectOpp']").click(function(){
	$("input[name$='Ids']:enabled").each(function(){
		$(this).attr("checked",!$(this).attr("checked"));
		$(this).parents("tr").toggleClass("checked");
	});
    //判断反选时是否选取了全部的复选框，以实现与全选复选框的联动
	if($("input[name$='Ids']:checked").size()==$("input[name$='Ids']").size()){
		$("input[name='selectAll']").attr("checked",true);
	}else{
		$("input[name='selectAll']").attr("checked",false);
	}
});
//不选
$("a[name='selectNone']").click(function(){
	$("input[name$='Ids']:enabled").attr("checked",false).parents("tr").removeClass("checked");
	$("input[name='selectAll']").attr("checked",false);
});
//单击版块前的复选框触发的事件
$("input[name$='Ids']").click(function(){
	//若选中则使这一行的变成黄色，没选中则还原成以前的颜色
	if($(this).attr("checked")){
	  $(this).parents("tr").addClass("checked");
	}else{
		 $(this).parents("tr").removeClass("checked");
	}
	
	var checksize=$("input[name$='Ids']:checked").size();
	if(checksize==1){//如果只单击了一个复选框
		//$(this).parent().nextAll(":has(span)").first().children("span").attr("class")=="enable"也可以
		if($(this).parent().nextAll(":eq(1)").children("span").attr("class")=="enable"){
		  $("#enable").attr("disabled",true);
		  $("#disable").attr("disabled",false);
		}else{
	      $("#enable").attr("disabled",false);
		  $("#disable").attr("disabled",true);	
		}
	}else{
	   $("#enable").attr("disabled",false);
       $("#disable").attr("disabled",false);
	}
	if(checksize==$("input[name$='Ids']").size()){
		$("input[name='selectAll']").attr("checked",true);
	}else{
		$("input[name='selectAll']").attr("checked",false);
	}
});
/*#######################################################################*/
//鼠标经过时table奇偶行颜色变化
/**当页面载入是就注册了这个事件，所以$("table tbody tr:not(:last)").hover和
 * $("table tbody tr[class!='selected']:not(:last)").hover在此处没有区别
 */
$("table tbody tr:not(:last)").hover(function(){
        $(this).addClass("selected");
},function(){
		$(this).removeClass("selected");
});
});
/*#######################################################################*/
//弹出编辑对话框
function popDialog(url){
$("#ForumManagerDialog").show()
		                   .load(url)
		                   .modal();
moveDialog();
}
//移动弹出框
function moveDialog(){
//是否开始拖动的标志
var startDrag=false;
//设置鼠标开始时距离所要弹出窗口的left与top
var mouse_left_object=0;
var mouse_top_object=0;
$("#simplemodal-container").mousedown(function(e){
	e=e||window.event;
   if(e.which==1) {
		   var x=e.clientX+$(document).scrollLeft();
	   var y=e.clientY+$(document).scrollTop();
   	startDrag=true; //1为鼠标左键，2为鼠标中键，3为鼠标右键
   	mouse_left_object=x-$(this).offset().left;
   	mouse_top_object=y-$(this).offset().top;
   }
})
.mousemove(function(e){
	if(startDrag){
	$(this).css("position","absolute").offset({left:e.clientX+$(document).scrollLeft()-mouse_left_object,top:e.clientY+$(document).scrollTop()-mouse_top_object});
	}
})
.mouseup(function(e){
	e=e||window.event;
   if(e.which==1) {
   	startDrag=false;
   	}
})
.mouseout(function(e){
	e=e||window.event;
	if(!isInContainer(e,$(this))){
   	startDrag=false;
   }
});
}
/*#######################################################################*/
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
