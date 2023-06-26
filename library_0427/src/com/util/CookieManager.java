package com.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {

	public CookieManager() {
		// TODO Auto-generated constructor stub
	}
	
	// 쿠키 생성
	public static void CookieMake(HttpServletResponse response, String cName, String cValue, int cTime) {
		Cookie cookie = new Cookie(cName, cValue);
		cookie.setPath("/");
		cookie.setMaxAge(cTime);
		response.addCookie(cookie);
	}
	
	// 쿠키 이름 읽어서 반환
	public static String readCookie(HttpServletRequest request, String cName) {
		String cookieValue = "";
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies!=null) {
			for(Cookie cookie : cookies) {
				if(cName.equals(cookie.getName())) {
					cookieValue = cookie.getValue();
					break;
				}
			}
		}
		return cookieValue;
	}
	
	// 쿠키 삭제
	
}
