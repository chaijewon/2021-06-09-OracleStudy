package com.sist.data;
/*
 *  CNO     NOT NULL NUMBER         
	TITLE   NOT NULL VARCHAR2(1000) 
	SUBJECT NOT NULL VARCHAR2(1000) 
	POSTER  NOT NULL VARCHAR2(260)  
	LINK    NOT NULL VARCHAR2(100)  
 */
// 데이터를 모아서 관리 
public class FoodCategoryVO {
    private int cno;
    private String title,subject,poster,link;
    
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
    
    
}
