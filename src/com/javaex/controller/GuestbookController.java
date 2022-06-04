package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;
import com.javaex.vo.UserVo;

@WebServlet("/guestbook")
public class GuestbookController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
    //생성자(디폴트생성자 사용)
	//메소드 gs
	
	//메소드 일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GuestbookController");
		
		//포스트 방식일때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("addList".equals(action)) {
			System.out.println("GuestbookController>addList");
			
			//데이터 가져오기
			GuestbookDao guestDao = new GuestbookDao();
			List<GuestbookVo> guestList = guestDao.getList();
			
			request.setAttribute("guestList", guestList);
			
			//포워드 방명록리스트
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		}else if("insert".equals(action)) { //등록
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			GuestbookDao guestDao = new GuestbookDao();
			GuestbookVo guestVo = new GuestbookVo(name, password, content);
			int count = guestDao.insert(guestVo);
			
			WebUtil.redirect(request, response, "/mysite2/guestbook?action=addList");
		}else if("deleteForm".equals(action)) {//삭제폼
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		}else if("delete".equals(action)){ //삭제
			//세션에서 no  알아내기
			//HttpSession session = request.getSession();
			//GuestbookVo authGuest = (GuestbookVo)session.getAttribute("authGuest");
			//int no = authGuest.getNo();
			
			//파라미터에서 값 꺼내오기
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			GuestbookVo guestbookVo = new GuestbookVo();
			guestbookVo.setNo(no);
			guestbookVo.setPassword(password);
			
			GuestbookDao guestbookDao = new GuestbookDao();
			guestbookDao.delete(guestbookVo);
			
			//리다이렉트 
			WebUtil.redirect(request, response, "/mysite2/guestbook?action=addList");
			
		}else {
			System.out.println("action 파라미터 없음");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
