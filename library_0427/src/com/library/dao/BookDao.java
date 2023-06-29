package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.common.ConnectionUtil;
import com.library.common.DBConnPool;
import com.library.vo.Book;
import com.library.vo.Criteria;

public class BookDao {
	/**
	 * 도서목록 조회
	 * @param cri 
	 * @return
	 */
	public List<Book> getList(Criteria cri){
		List<Book> list = new ArrayList<Book>();
		
		//String sql = "select * from book order by no";
//		String sql = "select * from ( select rownum rn, t.* from ( select no, title, nvl((select 대여여부"
//				+ " from rent where bookno = no and 대여여부='Y'),'N') rentyn, author from book";
		
		String sql = "select * from ( select rownum rn, t.* from ( "
				+ " select b.no, b.title, b.rentyn, b.author, to_char(rentday, 'yy/mm/dd') 대여일, to_char(RETERNABLEDAY, 'yy/mm/dd') 반납가능일, sfile, ofile"
				+ " from book b, ( "
				+ " select * from rent where 대여여부='Y' )d where b.no = d.bookno(+) ";
		if(!"".equals(cri.getSearchWord())&&cri.getSearchWord()!=null) {
			sql += " and "+cri.getSearchField()+" like '%"+cri.getSearchWord()+"%'";
		}
		
		sql += " order by no desc ) t ) where rn between "+cri.getStartNo()+ " and "+cri.getEndNo();
		
		// System.out.println(sql);
		// try ( 리소스생성 ) => try문이 종료되면서 리소스를 자동으로 반납
		try (Connection conn = ConnectionUtil.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql);
				// stmt.executeQuery : resultSet (질의한 쿼리에 대한 결과집합)
				// stmt.executeUpdate : int (몇건이 처리되었는지!!!)
				ResultSet rs = psmt.executeQuery()){
			
			while(rs.next()) {
				Book book = new Book();
				book.setNo(rs.getString(2));
				book.setTitle(rs.getString(3));
				book.setRentyn(rs.getString(4));
				book.setAuthor(rs.getString(5));
				book.setStartDate(rs.getString(6));// 대여일 6
				book.setEndDate(rs.getString(7));// 반납가능일 7
				book.setSfile(rs.getString(8));
				book.setOfile(rs.getString(9));
				
				list.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 도서 등록
	 * @param book
	 * @return
	 */
	public int insert(Book book) {
		int res = 0;
		
		String sql = "insert into book(no, title, author, ofile, sfile) values (SEQ_BOOK_NO.NEXTVAL,?,?,?,?)";

		// 실행될 쿼리를 출력해봅니다
		System.out.println(sql);
		
		try(Connection conn = DBConnPool.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);) {
			psmt.setString(1, book.getTitle());
			psmt.setString(2, book.getAuthor());
			psmt.setString(3, book.getOfile());
			psmt.setString(4, book.getSfile());
			res = psmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return res;
	}
	
	/**
	 * 도서 삭제
	 * @return
	 */
	public int delete(String delNo) {
		int res = 0;
		
		String sql = String.format
						("delete from book where no in(%s)", delNo);
	
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();	){
			res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	/**
	 * 도서 업데이트
	 * @return
	 */
	public int returnBook(String no) {
		int res = 0;
		String sql1 = "UPDATE rent SET 대여여부='N', reternday=SYSDATE, lareternday=CASE WHEN FLOOR(SYSDATE-reternableday)<0 THEN 0 ELSE FLOOR(SYSDATE-reternableday) END WHERE bookno=? AND 대여여부='Y'";
		String sql2 = "UPDATE book SET rentyn='N', rentno = null WHERE no=?";

		try(Connection conn = DBConnPool.getConnection();) {
			conn.setAutoCommit(false);
			PreparedStatement psmt = conn.prepareStatement(sql1);
			psmt.setString(1, no);
			// rent 테이블 반납일, 반납가능일보다 반납일이 늦으면 연체일 업데이트 
			res = psmt.executeUpdate();
			if(res>0) {
				psmt.close();
				
				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, no);
				
				// 업데이트 됐으면 book 테이블 rentyn = N 업데이트
				res = psmt.executeUpdate();
				if(res > 0) {
					conn.commit();
				} else {
					conn.rollback();
				}
			}
			psmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}

	public String getRentYN(String no) {
		String rentYN = "";
		String sql = "SELECT RENTYN FROM BOOK WHERE NO = ?";
		
		try(Connection conn = DBConnPool.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);) {
			psmt.setString(1, no);
			ResultSet rs = psmt.executeQuery();
			
			rs.next();
			rentYN = rs.getString(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return rentYN;
	}

	public int getTotalCnt(Criteria cri) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from book";
		
		if(!"".equals(cri.getSearchWord())&&cri.getSearchWord()!=null) {
			sql += " where "+cri.getSearchField()+" like '%"+cri.getSearchWord()+"%'";
		}
		
		int totalCnt=0;
		
		try(Connection conn = DBConnPool.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);) {
			ResultSet rs = psmt.executeQuery();
			
			rs.next();
			totalCnt = rs.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return totalCnt;
	}

	public Book selectBook(String no) {
		
		String sql = "select b.no, b.title, d.대여여부, b.author, d.id, to_char(rentday, 'yy/mm/dd') 대여일, to_char(RETERNABLEDAY, 'yy/mm/dd') 반납가능일, reternday, sfile, ofile, d.rentno\r\n"
				+ "from book b, rent d\r\n"
				+ "where b.rentno = d.rentno(+) and b.no=?";
		Book book = new Book();
		
		try(Connection conn = DBConnPool.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql);) {
			psmt.setString(1, no);
			ResultSet rs = psmt.executeQuery();
				
			rs.next();	
			book.setNo(rs.getString(1));
			book.setTitle(rs.getString(2));
			book.setRentyn(rs.getString(3));
			book.setAuthor(rs.getString(4));
			book.setId(rs.getString(5));
			book.setStartDate(rs.getString(6));// 대여일 6
			book.setEndDate(rs.getString(7));// 반납가능일 7
			book.setReturnDate(rs.getString(8));// 반납일 8
			book.setSfile(rs.getString(9));
			book.setOfile(rs.getString(10));
			book.setRentno(rs.getString(11));
			
			rs.close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return book;
	}

	public int rentBook(Book book) {
		int res = 0;
		String sql1 = "SELECT 'R'||LPAD(seq_rent.nextval,5,0) FROM dual";
		String sql2 = "UPDATE book SET rentno=?, rentyn='Y' WHERE no=? AND (rentno IS NULL OR rentno='')";
		String sql3 = "INSERT INTO rent VALUES(?, ?, ?, 'Y', SYSDATE, null, SYSDATE+14, null)";
		
		
		// 1. 대여번호 조회(R000001) 
		try(Connection conn = DBConnPool.getConnection();) {
			PreparedStatement psmt = conn.prepareStatement(sql1);
			conn.setAutoCommit(false); // 자동 커밋 해제
			ResultSet rs = psmt.executeQuery();
			
			if(!rs.next()) {
				return res;
			}
			
			String rentno = rs.getString(1);
			
			System.out.println("rentno : "+rentno);
			
			psmt.close();
			
			// 2. book테이블 업데이트(rentyn=Y, rentno=대여번호)
			//		조건 : 도서번호, rentno가 null 또는 빈문자열
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, rentno);
			psmt.setString(2, book.getNo());
			
			res = psmt.executeUpdate();
			
			System.out.println("sql2 : "+sql2);
			System.out.println("res : "+res);
			
			// 3. 대여테이블 인서트
			if(res>0) {
				psmt.close();
				psmt = conn.prepareStatement(sql3);
				psmt.setString(1, rentno);
				psmt.setString(2, book.getId());
				psmt.setString(3, book.getNo());
				
				res = psmt.executeUpdate();
				
				System.out.println("sql3 : "+sql3);
				System.out.println("res : "+res);
				
				if(res > 0) {
					conn.commit();
				} else {
					conn.rollback();
				}
			} else {
				conn.rollback();
			}
			rs.close();
			psmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}























