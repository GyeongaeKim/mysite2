package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
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
	
	
	public List<BoardVo> getList(){
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		getConnection();
		try {
			//SQL, 바인딩, 실행
			String query = "";
			query += " select  b.no, ";
			query += "         u.name, ";
			query += "         b.title, ";
			query += "         b.hit, ";
			query += "         reg_date, ";
			query += "         to_char(reg_date, 'YY-MM-DD HH:MI') newDate ";
			query += " from board b, users u ";
			query += " where b.user_no=u.no ";
			query += " order by reg_date desc ";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			//결과
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String title = rs.getString("title");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("newDate");
				
				
				BoardVo boardVo = new BoardVo(no, name, title, hit, regDate);
				boardList.add(boardVo);
			}
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return boardList;
	}
	
	
	public List<BoardVo> searchList(String keyword){
		List<BoardVo> searchList = new ArrayList<BoardVo>();
		getConnection();
		try {
			//SQL, 바인딩, 실행
			String query = "";
			query += " select  b.no, ";
			query += "         u.name, ";
			query += "         b.title, ";
			query += "         b.hit, ";
			query += "         reg_date, ";
			query += "         to_char(reg_date, 'YY-MM-DD HH:MI') newDate ";
			query += " from board b, users u ";
			query += " where b.user_no=u.no ";
			query += "   and b.title like ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, '%' + keyword + '%');
				
			rs = pstmt.executeQuery();
			
			//결과
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String title = rs.getString("title");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("newDate");
				
				
				BoardVo boardVo = new BoardVo(no, name, title, hit, regDate);
				searchList.add(boardVo);
			}
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return searchList;
	}
	
	
	public int delete(BoardVo boardVo) {
		int count = -1;
		getConnection();
		try {
			//SQL, 바인딩, 실행
			String query = "";
			query += " delete from board ";
			query += " where no= ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardVo.getNo());
			
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println(count + "건이 삭제되었습니다.");
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}	
		close();
		return count;
	}
	
	public int updateHit(BoardVo boardVo) {
		int count = -1;
		getConnection();
		try {
			//SQL, 바인딩, 실행
			String query = "";
			query += " update board ";
			query += " set hit = hit+1 ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardVo.getNo());
			
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println("조회수가 " + count + " 증가하였습니다.");
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	public BoardVo readBoard(int bno) {
		BoardVo boardVo = null;
		getConnection();
		try {
			//SQL, 바인딩, 실행
			String query = "";
			query += " select  b.no, ";
			query += "         name, ";
			query += "         hit, ";
			query += "         reg_date, ";
			query += "         to_char(reg_date, 'YY-MM-DD HH:MI') newDate, ";
			query += "         title, ";
			query += "         content ";
			query += " from board b, users u ";
			query += " where b.user_no=u.no ";
			query += " 		and b.no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("newDate");
				String title = rs.getString("title");
				String content = rs.getString("content");
				
				boardVo = new BoardVo(no, name, hit, regDate, title, content);
			}
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return boardVo;
	}
	
	
	
	public BoardVo getBoard (int no) {//name hit regDate title content
		BoardVo boardVo = null;
		getConnection();
		try {
			//SQL, 바인딩, 실행
			String query = "";
			query += " select  name, ";
			query += "         hit, ";
			query += "         reg_date, ";
			query += "         to_char(reg_date, 'YY-MM-DD HH:MI') newDate, ";
			query += "         title, ";
			query += "         content ";
			query += " from board b, users u ";
			query += " where b.user_no=u.no ";
			query += " 		and b.no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("newDate");
				String title = rs.getString("title");
				String content = rs.getString("content");
				
				boardVo = new BoardVo(name, hit, regDate, title, content);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return boardVo;
		
	}
	
	
	
	public int modifyBoard(BoardVo boardVo) {//title content
		int count=-1;
		getConnection();
		try {
			//SQL, 바인딩, 실행
			String query = "";
			query += " update board ";
			query += " set title = ?, ";
			query += "     content = ? ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());
			
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println(count + "건이 수정되었습니다.");
		
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	
	
	public int insertBoard(BoardVo boardVo) {
		int count = -1;
		getConnection();
		try {
			//SQL, 바인딩, 실행
			String query = "";
			query += " insert into board ";
			query += " values(seq_board_no.nextval, ?, ?, ?, sysdate, ?) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, 0);
			pstmt.setInt(4, boardVo.getUserNo());
			
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println(count + "건이 등록되었습니다.");
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	
	

}
	
	
	
	
	