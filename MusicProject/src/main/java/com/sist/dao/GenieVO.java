package com.sist.dao;
/*
 *  NO        NOT NULL NUMBER         
	POSTER             VARCHAR2(1000) 
	TITLE              VARCHAR2(200)  
	SINGER             VARCHAR2(100)  
	ALBUM              VARCHAR2(200)  
	STATE              CHAR(10)       
	IDCREMENT          NUMBER         
	KEY                VARCHAR2(100)   ==> 8*200 => 1600
	                                   ==> 200
 */
// 오라클 <==> 자바 (1. 데이터를 모아서 관리, 2. 오라클 데이터 읽기, 3. 브라우저로 전송 )
public class GenieVO {
    private int no;
    private String title;
    private String poster;
    private String singer;
    private String album;
    private String state;
    private int idcerment;
    private String key;
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getIdcerment() {
		return idcerment;
	}
	public void setIdcerment(int idcerment) {
		this.idcerment = idcerment;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	   
   
}












