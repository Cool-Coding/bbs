<%@ page pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- <c:if test="${request_pageView.totalPage gt 1}"> --%>
<div class="pageview">分页：
    <%--页码列表--%>
    <a href="javascript:gotoPage(1)">[首页]</a>
    <c:forEach begin="${request_pageView.pageIndex.startIndex}" end="${pageView.pageIndex.endIndex}" var="pageNum">
        <c:if test="${request_pageView.currentPage ne pageNum}">
            <a href="javascript:gotoPage(${pageNum})">[${pageNum}]</a>
        </c:if>
        <c:if test="${request_pageView.currentPage eq pageNum}">
            <span class="currentpage">[${pageNum}]</span>
        </c:if>
    </c:forEach>
    <a href="javascript:gotoPage(${request_pageView.totalPage})">[尾页]</a>
    总记录数：${request_pageView.totalRecord}
    <%--快速选择页码--%>
    快速跳转:
    <select name="selPageNum" onchange="gotoPage(this.value)">
        <c:forEach begin="1" end="${request_pageView.totalPage}" var="pageNum">
            <option value="${pageNum}" ${request_pageView.currentPage eq pageNum ? 'selected=\\"selected\\"' : ''}>${pageNum}</option>
        </c:forEach>
    </select>
</div>

<script type="text/javascript">
    function gotoPage(pageNum) {
    	// 参数检查
    	if( paginationOptions == "undefined" ){
    		alert("请提供 paginationOptions 参数");
    		return;
    	}
    	
    	var pageSize = $("select[name='selPageSize']").val();
    	
    	// 方式一：type="form"
    	if(paginationOptions.type == "form"){
    		paginationOptions.form.page.value = pageNum;
    		paginationOptions.form.submit();
    	}
    	else{ // 方式二：type="url", 要指定参数 url
    		var symbol = "?";
    		if(paginationOptions.url.indexOf("?") > -1){
    			symbol = "&";
    		}
    		document.location.href = paginationOptions.url + symbol +"page=" + pageNum;
    	}
    }
</script>
