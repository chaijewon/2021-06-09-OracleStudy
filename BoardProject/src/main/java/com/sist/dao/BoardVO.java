package com.sist.dao;
// 오라클에 있는 데이터 저장하는 데이터형 
import java.util.*;
/*
 *   NO      NOT NULL NUMBER         
	 NAME    NOT NULL VARCHAR2(34)   
	 SUBJECT NOT NULL VARCHAR2(1000) 
	 CONTENT NOT NULL CLOB           
	 PWD     NOT NULL VARCHAR2(10)   
	 REGDATE          DATE           
	 HIT              NUMBER    
	     서블릿,JSP    JDBC
	 HTML  =   자바    =    오라클         HTML/Oracle (연결기능이 없다) => 연결(자바)
	화면출력          데이터만 저장 관리
 */
public class BoardVO {
    private int no;
    private String name;
    private String subject;
    private String content;
    private String pwd;
    private Date regdate;
    private int hit;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	   
}
