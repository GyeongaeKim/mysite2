package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.javaex.util.WebUtil;

@WebServlet("/main")
public class MainController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
    //생성자(디폴트생성자 사용)
    //메소드 gs
	
	//메소드 일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//코드 입력
		System.out.println("MainController");
		
		//포워드 (index 페이지)
		WebUtil.forword(request, response, "/WEB-INF/views/main/index.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
