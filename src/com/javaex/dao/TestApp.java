package com.javaex.dao;

import java.util.List;

import com.javaex.vo.BoardVo;



public class TestApp {

	public static void main(String[] args) {
		
		BoardDao boardDao = new BoardDao();
		List<BoardVo> personList = boardDao.getList();
		
		System.out.println(personList.toString());
		//.toString() 굳이 안써도 됨,
		
	}

}
