<%@page import="com.util.CookieManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<aside id='rightside'>
	<div class='side1'>
	<!-- form : 페이지를 요청하는 태그 -->
	<form action="./login/LoginAction.do" method="post">
		<%
			// 로그인 실패 메세지 처리
			String err = request.getParameter("error");
			if("Y".equals(err)){
				out.print("아이디/비밀번호를 확인해주세요");
			}
			String savedId=CookieManager.readCookie(request, "userId");
		%>
		<div class='loginbox'>
		    <div id='login'>
		        <input type="text" name="userid" id="userid" placeholder='ID를 입력해주세요.' required="required" value=<%=savedId %>>
		        <input type="password" name="userpw" id="userpw" placeholder='PW를 입력해주세요.' required="required">
		    </div>
		    <div id='button'>
		    <input type="submit" value="로그인">
		    </div>
		</div>
		
		<div id='info'>
			<input type="checkbox" name="saveId" value="Y" <%=!"".equals(savedId)?"checked":"" %>>아이디 저장<br>
		    <a href="">회원가입</a>
		    <a href="">ID찾기</a>
		    <a href="">PW찾기</a>
		</div>
	</form>	
	</div>
	</aside>

</body>
</html>