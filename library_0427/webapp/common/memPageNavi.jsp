<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
	function go(page){
		document.searchForm.action="./memberlist.member";
		document.searchForm.pageNo.value=page;
		document.searchForm.submit();
	}
</script>
<body>
<!-- 영역에 저장 -->
<c:set var="pageDto" value="${map.pageDto }"/>
<!-- 이전 -->
<c:if test="${pageDto.prev }">
	<input type='button' value='이전' onclick='go(${pageDto.startNo-1 })'>
</c:if>

<!-- 페이지 번호 -->
<c:forEach begin="${pageDto.startNo }" end="${pageDto.endNo }" step="1" var="pageNo">
	<input type="button" value="${pageNo }" onclick='go(${pageNo})'>
</c:forEach>

<!-- 이후 -->
<c:if test="${pageDto.next }">
	<input type="button" value="이후" onclick='go(${pageDto.endNo+1})'>
</c:if>

</body>
</html>