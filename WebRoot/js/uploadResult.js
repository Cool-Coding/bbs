<!--
 var count=parseInt(window.parent.$("#attachment_count").val())-1;
 window.parent.$("#attachment_count").val(count);
 if(count==0){
        var pagetitle=window.parent.$("title").html();//不能用window.parent.$("title:first").text();这对于IE不起作用
        switch(pagetitle){
         case "发表帖子": window.parent.$("input[name='postTheme']").val("发表帖子");break;
         case "修改帖子": window.parent.$("input[name='postTheme']").val("确认修改");break;
         default: null;
        }
    }else{
     window.parent.$("input[name='postTheme']").val(count+"个附件正在上传中，请上传完毕后再提交");
    }
//-->