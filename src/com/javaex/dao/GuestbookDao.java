package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {
	
	// 0. import java.sql.*;
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;

		private String driver = "oracle.jdbc.driver.OracleDriver";
		private String url = "jdbc:oracle:thin:@localhost:1521:xe";
		private String id = "webdb";
		private String pw = "webdb";

		private void getConnection() {
			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName(driver);
				// 2. Connection 얻어오기
				conn = DriverManager.getConnection(url, id, pw);
				// System.out.println("접속성공");
			} catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

		private void close() {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

	public List<GuestbookVo> getList() {
		getConnection();
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = " select no, " + 
						   "        name, " + 
					       "        password, " + 
					       "        content, " + 
					       "        reg_date " + 
			               " from guestbook " +
					       " order by reg_date desc ";
			pstmt = conn.prepareStatement(query);
			System.out.println("=====");
			System.out.println(query);
			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				GuestbookVo vo = new GuestbookVo(no, name, password, content, regDate);
				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		close();
		return list;
	}

	public int insert(GuestbookVo vo) {
		getConnection();
		int count = 0;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = " insert into guestbook " + 
			               "        (no, " + 
			               "         name, " +
			               "         password, " +
			               "         content, " +
			               "         reg_date) " +
			               " values (seq_guestbook_no.nextval, " +
			               "         ?, " +
			               "         ?, " +
			               "         ?, " +
			               "         sysdate) " ;	
			
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		close();
		return count;
	}

	public int delete(GuestbookVo vo) {
		getConnection();
		int count = 0;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = " delete from guestbook " + 
					       " where no= ? " + 
				           " and password= ? " ;
			
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		close();
		return count;
	}

}