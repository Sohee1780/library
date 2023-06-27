<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function searchBook() {
		searchForm.action = "../book/list.book";
		searchForm.submit();
	}
</script>
</head>
<body>

	<!-- 검색 폼 시작-->
	<form name="searchForm" method="get">
		<!-- 페이지 번호 -->
		<input type="text" name="pageNo">
		<!--삭제 번호 -->
		<input type="text" name="delNo">
		
		<table border='1' width="100%">
			<tr>
				<td align="center">
				<select name="searchField"> 
	            <option value="title" ${param.searchField eq "title"? "selected":"" }>도서명</option> 
	            <option value="author" ${param.searchField eq "author"? "selected":"" }>작가명</option>
            	</select>
								
				<input type="text" name="searchWord" value="${param.searchWord }">
				<!-- <input type="submit" value="검색하기"> -->
				<button onclick="searchBook()">검색하기</button>
				</td>
			</tr>
		</table>
	</form>
	
</body>
</html>