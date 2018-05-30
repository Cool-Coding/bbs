<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
    <c:if test="${pageView.totalRecord > pageView.viewPageRecords}">
    <c:if test="${pageView.currentPage!=1}">
    <span><a href='javascript:topage(${pageView.currentPage-1})'>上一页</a></span>
    </c:if>
    <c:if test="${pageView.currentPage==1}">
    <span style="color:gray;">上一页</span>
    </c:if>
    <c:forEach begin="${pageView.pageIndex.startIndex}" end="${pageView.pageIndex.endIndex}" var="wp">
    [
    <c:if test="${pageView.currentPage==wp}"><b style="color:#fa0;">${wp}</b></c:if>
    <c:if test="${pageView.currentPage!=wp}"><a href='javascript:topage(${wp})'>${wp}</a></c:if>
    ]
    </c:forEach>
    <c:if test="${pageView.currentPage!=pageView.totalPage}">
    <span><a href='javascript:topage(${pageView.currentPage+1})'>下一页</a></span>
    </c:if>
    <c:if test="${pageView.currentPage==pageView.totalPage}">
    <span style="color:gray;">下一页</span>
    </c:if>
    </c:if>