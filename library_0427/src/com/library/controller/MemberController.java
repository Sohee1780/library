package com.library.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.service.MemberService;
import com.library.vo.Criteria;

@WebServlet("*.member")
public class MemberController extends HttpServlet{
	MemberService ms = new MemberService();
	
	public MemberController() {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		
		System.out.println("요청uri : "+uri);
		
		if(uri.indexOf("memberlist")>0) {
//			System.out.println("field: "+req.getParameter("searchField"));
//			System.out.println("word: "+req.getParameter("searchWord"));
//			System.out.println("pageNo: "+req.getParameter("pageNo"));
			
			// 검색조건 세팅
			Criteria cri = new Criteria(req.getParameter("searchField"),
									req.getParameter("searchWord"),
									req.getParameter("pageNo"), "member");
			
			
			// 리스트 조회 및 요청 객체에 저장
			req.setAttribute("map", ms.getList(cri));
			// 포워딩
			req.getRequestDispatcher("./memberlist.jsp").forward(req, resp);
		}
		
		
		
	}

}
