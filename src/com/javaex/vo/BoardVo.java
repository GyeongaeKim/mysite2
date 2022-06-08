package com.javaex.vo;

public class BoardVo {
	
	//필드
	private int no;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int userNo;
	
	private String name;
	
	
	//생성자
	public BoardVo() {
	}
	public BoardVo (int no, String name, String title, int hit, String regDate) {
		this.no = no;
		this.name = name;
		this.title = title;
		this.hit = hit;
		this.regDate = regDate;
	}
	public BoardVo (int no, String name, int hit, String regDate, String title, String content) {
		this.no = no;
		this.name = name;
		this.hit = hit;
		this.regDate = regDate;
		this.title = title;
		this.content = content;
	}
	
	public BoardVo (String name, int hit, String regDate, String title, String content){
		this.name = name;
		this.hit = hit;
		this.regDate = regDate;
		this.title = title;
		this.content = content;
	}
	public BoardVo (int userNo, String title, String content){
		this.userNo = userNo;
		this.title = title;
		this.content = content;
	}
	
	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
	}
	
	

	
	
	//메소드 gs
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	
	
	//메소드 일반
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDate="
				+ regDate + ", userNo=" + userNo + ", name=" + name +"]";
	}
}
