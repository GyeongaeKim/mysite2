package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.GuestbookDao;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;
import com.javaex.vo.UserVo;

@WebServlet("/guestbook")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GuestbookController");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("addList".equals(action)) {
			System.out.println("GuestbookController>addList");
			//포워드 
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		}else if("add".equals(action)) {
			System.out.println("GuestbookController>add");
			
			//파라미터 꺼내기
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			//Vo만들기
			//Dao를 이용해서 저장하기
			GuestbookDao guestDao = new GuestbookDao();
			GuestbookVo guestVo = new GuestbookVo(name, password, content);
			int count = guestDao.insert(guestVo);
			
			//포워드
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
