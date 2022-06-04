package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

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
			
			WebUtil.redirect(request, response, "/mysite2/guestbook");
			
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
