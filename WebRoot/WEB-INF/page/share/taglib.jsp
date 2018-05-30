<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://cn.yang.bbs/taglibs.tld" prefix="bbs" %> 

<script language="javascript">
<!--
/*$(function(){
  $("input").focus(function(){
	$(this).css("border","2px solid red");
	}).focusout(function(){
	$(this).css("border","1px solid #ccc");
	});
});
*/
window.onload=function(){
    var inputs=document.getElementsByTagName("input");
    for(var i=0;i<inputs.length;i++){
        if(inputs[i].value=="" && (inputs[i].type=="text" || inputs[i].type=="password")){
         inputs[i].onfocus=function(){
           this.style.border="2px solid red";
           this.style.backgroundColor="#DCF1FE";
         }
         
         inputs[i].onblur=function(){
            this.style.border="1px solid #ccc";
             this.style.backgroundColor="white";
         }
       }
    }
   var textAreaa=document.getElementsByTagName("textArea");
       for(var i=0;i<textAreaa.length;i++){
         textAreaa[i].onfocus=function(){
           this.style.border="2px solid red";
           this.style.backgroundColor="#DCF1FE";
         }
         
         textAreaa[i].onblur=function(){
            this.style.border="1px solid #ccc";
            this.style.backgroundColor="white";
         }
    }
}
//--> 
</script>