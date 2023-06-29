<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function searchBook() {
		searchForm.action = "../member/memberlist.member";
		searchForm.submit();
	}
</script>
<link rel="stylesheet" href="../css/style.css">
</head>
<body>

	<!-- 검색 폼 시작-->
	<form name="searchForm" method="get">
		<input type='hidden' name='pageNo'>
		<table border='1' width="100%">
			<tr>
				<td align="center">
				<select name="searchField"> 
	            <option value="id" ${param.searchField eq "id"? "selected":"" }>아이디</option> 
	            <option value="name" ${param.searchField eq "name"? "selected":"" }>이름</option>
            	</select>
								
				<input type="text" name="searchWord" value="${param.searchWord }">
				<!-- <input type="submit" value="검색하기"> -->
				<button onclick="searchMember()">검색하기</button>
				</td>
			</tr>
		</table>
	</form>
	
</body>
</html>