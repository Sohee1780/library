<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<h2>도서목록</h2>
<!-- 목록출력 -->
<!-- 검색 -->
<!-- 페이징 -->
<!-- 상세 -->
<!-- 삭제 -->
<!-- 등록 -->
총건수 : 00건
<!-- 검색폼 시작 -->
<%@ include file="../common/searchForm.jsp" %>
<!-- 검색폼 끝 -->

<table border='1' width="90%">

	<c:if test="${sessionScope.adminYN eq 'Y' }">
		<tr>
			<td colspan="5" class="right">
			<!-- 어드민 계정인 경우 등록, 삭제버튼을 출력 -->
			<button>도서등록</button>
			<button>도서삭제</button>
			</td>
		</tr>
	</c:if>

	<tr>
		<th width="5%"></th>
		<th width="20%">제목</th>
		<th width="10%">저자</th>
		<th width="20%">대여여부/반납일</th>
		<th width="20%">등록일</th>
	</tr>
	
	<c:choose>
		<c:when test="${empty list }">
			<tr align="center">
				<td colspan="5">등록된 게시물이 없습니다.</td>
			</tr>
		</c:when>
		
		<c:otherwise>
			<c:forEach items="${list }" var="book">
			<tr align="center">
				<!-- 삭제용 체크박스 -->
				<td><input type="checkbox" name="delNo" value="${book.no }"></td>
				<td><a href="../book/view.book?no=${book.no}">${book.title }</a></td>
				<td>${book.author }</td>
				<td>${book.rentyn }</td>
				<td></td>
			</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>

</table>

</body>
</html>