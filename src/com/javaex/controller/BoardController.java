package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;


@WebServlet("/board")
public class BoardController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
    //생성자(디폴트생성자 사용)
	//메소드 gs
	
	//메소드 일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardController");
		
		//포스트 방식일때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
				
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("list".equals(action)) {
			System.out.println("BoardController>list");
			
			//데이터 가져오기
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getList();
			
			request.setAttribute("boardList", boardList);
			
			//포워드 게시판리스트
			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
		}else if("delete".equals(action)) {
			//세션에서 no  알아내기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			
			//파라미터에서 값 꺼내오기
			int boardNo = Integer.parseInt(request.getParameter("no"));
			
			BoardVo boardVo = new BoardVo();
			boardVo.setNo(boardNo);
			
			BoardDao boardDao = new BoardDao();
			boardDao.delete(boardVo);
			
			//리다이렉트 
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
		}else if("click".equals(action)) {
			//파라미터에서 값 꺼내오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardVo boardVo = new BoardVo();
			boardVo.setNo(no);
			
			BoardDao boardDao = new BoardDao();
			int count = boardDao.updateHit(boardVo);
			
			WebUtil.redirect(request, response, "/mysite2/board?action=read&no="+no);
			
		}else if("read".equals(action)) { //name hit regDate title content
			//파라미터에서 값 꺼내오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//no로 게시글 정보 가져오기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.readBoard(no);
			
			request.setAttribute("boardVo", boardVo);
			WebUtil.forword(request, response, "/WEB-INF/views/board/read.jsp");
		}else if("modifyForm".equals(action)) {	//수정폼
			//세션에서 no  알아내기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			//파라미터에서 값 꺼내오기
			int boardNo = Integer.parseInt(request.getParameter("no"));
			
			//no 로 사용자 정보 가져오기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getBoard(boardNo);
			
			request.setAttribute("boardVo", boardVo);
			WebUtil.forword(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}else if("modify".equals(action)) { //수정
			//세션에서 no  알아내기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			
			int boardNo = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			BoardVo boardVo = new BoardVo();
			boardVo.setNo(boardNo);
			boardVo.setTitle(title);
			boardVo.setContent(content);
			
			BoardDao boardDao = new BoardDao();
			int count = boardDao.modifyBoard(boardVo);
			
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
		}else if("writeForm".equals(action)) {	//글쓰기폼
			//로그인한 사용자의  no 값을 세션에서 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int userNo = authUser.getNo();
			
			//no 로 사용자 정보 가져오기
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(userNo);
			
			//request 의 attribute 에 userVo 는 넣어서 포워딩
			request.setAttribute("userVo", userVo);
			WebUtil.forword(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}else if("insert".equals(action)){	//파라미터에서 유저번호 꺼내오기
			//파라미터에서 값 꺼내오기
			int userNo = Integer.parseInt(request.getParameter("userNo"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			BoardVo boardVo = new BoardVo();
			boardVo.setUserNo(userNo);
			boardVo.setTitle(title);
			boardVo.setContent(content);
			
			BoardDao boardDao = new BoardDao();
			int count = boardDao.insertBoard(boardVo);
			
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
		}
	}
	
	
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
