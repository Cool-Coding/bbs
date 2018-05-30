<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
         当前页:第${pageView.currentPage}页|总记录数:${pageView.totalRecord}条|每页显示:${pageView.viewPageRecords}条|总页数:${pageView.totalPage}页
    <br/>
    <c:if test="${pageView.totalRecord>0}">
    <c:if test="${pageView.currentPage>1}">
    <a href='javascript:topage(${pageView.currentPage-1})'>上一页</a>
    </c:if>
    <c:forEach begin="${pageView.pageIndex.startIndex}" end="${pageView.pageIndex.endIndex}" var="wp">
    <c:if test="${pageView.currentPage==wp}"><b>第${wp}页</b></c:if>
    <c:if test="${pageView.currentPage!=wp}"><a href='javascript:topage(${wp})'>第${wp}页</a></c:if>
    </c:forEach>
    <c:if test="${pageView.currentPage  < pageView.totalPage }">
    <a href='javascript:topage(${pageView.currentPage+1})'>下一页</a>
    </c:if>
    </c:if>
    <br/>