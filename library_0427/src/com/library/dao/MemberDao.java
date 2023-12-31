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
import com.library.vo.Member;

public class MemberDao {
	/**
	 * 사용자 로그인
	 * @param id
	 * @param pw
	 * @return
	 */
	public Member login(String id, String pw) {
		Member member = null;
		
		String sql = 
				String.format("select id, name, adminyn, status, grade from member "
				+ "where id='%s' and pw='%s'",id,pw);
		
		// 쿼리 출력
		// System.out.println(sql);
		
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			// 질의결과 결과집합을 member객체에 담아줍니다
			if(rs.next()) {
				String name = rs.getString(2);
				String adminYN = rs.getString(3);
				String status = rs.getString(4);
				String grade = rs.getString(5);
				
				member = new Member(id, "", name, adminYN, status, grade);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return member;
	}
	
	public int insert(Member member) {
		int res = 0;
		String sql = String.format(
				"INSERT INTO MEMBER (id, pw, name, adminYN)  VALUES "
				+ "('%s','%s','%s')"
				, member.getId(), member.getPw()
				, member.getName(), member.getAdminyn());
		
		System.out.println(sql);
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();){
			res = stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
		
	}
	
	/**
	 * 아이디 중복 체크
	 * 중복일경우 false리턴
	 * 
	 * @param id
	 * @return
	 */
	public boolean idCheck(String id) {
		boolean res = false;
		
		String sql = String.format(
				"select * from member where id = '%s'",id);
		System.out.println(sql);
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
			// rs.next = 결과집합이 있으면 true -> !rs.next를 반환
			return !rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return res;
	}
	
	public int delete(String id) {
		int res = 0;
		
		String sql = String.format
				("delete from member where id = '%s'",id);
		
		System.out.println(sql);
		
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();){
			
			res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	public List<Member> getList(Criteria cri) {
		List<Member> mlist = new ArrayList<>();
		String sql="select * from ( select rownum rn, t.* from( select * from member ";
		
		if(!"".equals(cri.getSearchWord())&&cri.getSearchWord()!=null) {
			sql += " where "+cri.getSearchField()+" like '%"+cri.getSearchWord()+"%'";
		}
		
		sql += " ) t ) where rn between "+cri.getStartNo()+ " and "+cri.getEndNo();
		
		// System.out.println(sql);
		
		try(Connection conn = DBConnPool.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);) {
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()) {
				Member mem = new Member();
				mem.setId(rs.getString(2));
				mem.setName(rs.getString(4));
				mem.setAdminyn(rs.getString(5));
				mem.setStatus(rs.getString(6));
				mem.setGrade(rs.getString(7));
				
				mlist.add(mem);
			}
			
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mlist;
	}

	/**
	 * 총건수
	 * @param cri
	 * @return
	 */
	public int getTotalCnt(Criteria cri) {
		int cnt = 0;
		String sql="select count(*) from member";
		
		if(!"".equals(cri.getSearchWord())&&cri.getSearchWord()!=null) {
			sql += " where "+cri.getSearchField()+" like '%"+cri.getSearchWord()+"%'";
		}
		
		// System.out.println(sql);
		
		try(Connection conn = DBConnPool.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);) {
			ResultSet rs = psmt.executeQuery();
			
			rs.next();
			
			cnt = rs.getInt(1);
			
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cnt;
	}
}





























