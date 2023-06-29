<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/style.css">
</head>
<body>
<body>
<%@ include file="../common/header.jsp" %>
<h2>사용자 목록</h2>
<!-- 목록출력 -->
<!-- 검색 -->
<!-- 페이징 -->
<!-- 상세 -->
<!-- 삭제 -->
<!-- 등록 -->
<!-- ${map.totalCnt } -->
총건수 : ${map.totalCnt }건
<!-- 검색폼 시작 -->
<%@ include file="../common/memSearchForm.jsp" %>
<!-- 검색폼 끝 -->

<table border='1'>
	<tr>
		<th width="5%"></th>
		<th width="10%">아이디</th>
		<th width="10%">이름</th>
		<th width="10%">관리자여부</th>
	</tr>
	
	<c:choose>
		<c:when test="${empty map.mlist }">
			<tr align="center">
				<td colspan="5">등록된 게시물이 없습니다.</td>
			</tr>
		</c:when>
		
		<c:otherwise>
			<c:forEach items="${map.mlist }" var="member">
			<tr align="center">
				<!-- 삭제용 체크박스 -->
				<td><input type="checkbox" name="delNo" value=""></td>
				<td>${member.id }</td>
				<td>${member.name }</td>
				<td>${member.adminyn }</td>
			</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	
	<tr>
		<td colspan="5" class="center">
		<!-- 페이지블록 -->
		<%@ include file="../common/memPageNavi.jsp" %>
		</td>
	</tr>
</table>
</body>
</html>