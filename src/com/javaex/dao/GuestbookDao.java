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
		List<GuestbookVo> guestList = new ArrayList<GuestbookVo>();
		getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select no, ";
			query += "        name, ";
			query += "        password, ";
			query += "        content, ";
			query += "        reg_date ";
			query += " from guestbook ";
			query += " order by reg_date desc ";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				GuestbookVo guestVo = new GuestbookVo(no, name, password, content, regDate);
				guestList.add(guestVo);
			}
			
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return guestList;
	}
	
	
	public int insert(GuestbookVo guestVo) {
		int count = -1;
		getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into guestbook ";
			query += " values (seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPassword());
			pstmt.setString(3, guestVo.getContent());
			
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println(count + "건이 등록되었습니다.");
			
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	
	public int delete(GuestbookVo guestbookVo) {
		int count=-1;
		getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from guestbook ";
			query += " where no= ? ";
			query += " 	 and password= ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, guestbookVo.getNo());
			pstmt.setString(2, guestbookVo.getPassword());
			
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println(count + "건이 삭제되었습니다.");
			
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}	
		close();
		return count;
	}
	
	
	
	
}
