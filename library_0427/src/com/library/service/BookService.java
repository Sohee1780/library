package com.library.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.library.dao.BookDao;
import com.library.vo.Book;
import com.library.vo.Criteria;
import com.library.vo.pageDto;

public class BookService {
	BookDao dao = new BookDao();
	
	/**
	 * 책 리스트를 조회 합니다.
	 * @param cri 
	 * @return
	 */
	public Map<String, Object> getList(Criteria cri){
		// 맵 생성
		Map<String, Object> map = new HashMap<>();
		
		// 리스트 조회
		List<Book> list = dao.getList(cri);
		map.put("list", list);
		
		// 총 건수
		int totalCnt = dao.getTotalCnt(cri);
		map.put("totalCnt", totalCnt);
		
		// 페이지DTO
		pageDto pageDto = new pageDto(totalCnt, cri);
		map.put("pageDto", pageDto);
		
		return map;
	}

	/**
	 * 도서 정보 입력
	 * @return 
	 */
	public int insert(String title, String author) {
		Book book = new Book(title, author);
		int res = dao.insert(book);
		if(res > 0) {
			System.out.println(res + "건 입력 되었습니다.");
		} else {
			System.err.println("입력중 오류가 발생 하였습니다.");
			System.err.println("관리자에게 문의 해주세요");
		}
		return res;
	}


	/**
	 * 도서 정보 입력
	 * @param book
	 */
	public int write(Book book) {
		// TODO Auto-generated method stub
		int res = dao.insert(book);
		
		if(res > 0) {
			System.out.println(res + "건 입력 되었습니다.");
		} else {
			System.err.println("입력중 오류가 발생 하였습니다.");
			System.err.println("관리자에게 문의 해주세요");
		}
		
		return res;
	}
	
	
	public int delete(String delNo) {
		int res = dao.delete(delNo);
		if(res>0) {
			System.out.println(res+"건 삭제되었습니다.");
		} else {
			System.err.println("삭제중 오류가 발생 하였습니다.");
			System.err.println("관리자에게 문의 해주세요");
		}
		return res;
	}

	public int rentBook(Book book) {
		int res=0;
		res = dao.rentBook(book);
		return res;
	}

	public int returnBook(String no) {
		int res = 0;
		
		// 반납가능한 도서인지 확인
		String rentYN = dao.getRentYN(no);
		
		if("N".equals(rentYN)) {
			System.err.println("반납 가능한 상태가 아닙니다.");
			return res;
		} else if ("".equals(rentYN)) {
			System.out.println("없는 도서 번호 입니다.");
			return res;
		}
		
		// 반납처리
		res = dao.returnBook(no);
		
		if(res>0) {
			System.out.println(res + "건 반납 되었습니다.");
		}else {
			System.out.println("반납 처리 중 오류가 발생 하였습니다.");
			System.out.println("관리자에게 문의 해주세요");
		}
		return res;
	}
	
	public Book viewBook(String no) {
	
		Book book = dao.selectBook(no);
		
		return book;
	}

}













