<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
*{
    padding: 0;
    margin: 0;
}

body{
	margin: 0 auto;
	width:500px;
	border: 1px solid black;
}
header{
	border: 1px solid black;
	width:500px;
	height:50px;
	line-height:50px;
	display:flex;
	justify-content:space-between;
}
main{
	border: 1px solid red;
}

.right{
	text-align:right;
}

.center{
	text-align:center;
}
</style>

<script type="text/javascript">
	let message='${message}';
	if(message!=null&&""!=message){
		alert(message);
	}

	function deleteBook() {
		// 체크박스가 선택된 요소의 value값을 ,로 연결
		delNoList = document.querySelectorAll("[name=delNo]:checked");
		
		let delNo="";
		delNoList.forEach((e)=>{
			delNo += e.value+',';
		});
		delNo = delNo.substr(0,delNo.length-1);
		
		// 삭제 요청	
		searchForm.action = "../book/delete.book";
		searchForm.delNo.value = delNo;

		//document.searchForm.pageNo.value=${param.pageNo};
		
		searchForm.submit();
	}
	
</script>
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
총건수 : ${map.totalCnt }건
<!-- 검색폼 시작 -->
<%@ include file="../common/searchForm.jsp" %>
<!-- 검색폼 끝 -->

<table border='1'>

	<c:if test="${sessionScope.adminYN eq 'Y' }">
		<tr>
			<td colspan="5" class="right">
			<!-- 어드민 계정인 경우 등록, 삭제버튼을 출력 -->
			<button onclick="location.href='insert.jsp'">도서등록</button>
			<button onclick="deleteBook()">도서삭제</button>
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
		<c:when test="${empty map.list }">
			<tr align="center">
				<td colspan="5">등록된 게시물이 없습니다.</td>
			</tr>
		</c:when>
		
		<c:otherwise>
			<c:forEach items="${map.list }" var="book">
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
	
	<tr>
		<td colspan="5" class="center">
		<!-- 페이지블록 -->
		<%@ include file="../common/pageNavi.jsp" %>
		</td>
	</tr>
</table>
</body>
</html>