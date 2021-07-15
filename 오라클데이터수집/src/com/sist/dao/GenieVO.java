package com.sist.dao;
/*
 *  NO        NOT NULL NUMBER         
	POSTER             VARCHAR2(1000) 
	TITLE              VARCHAR2(200)  
	SINGER             VARCHAR2(100)  
	ALBUM              VARCHAR2(200)  
	STATE              CHAR(4)        
	IDCREMENT          NUMBER         
	KEY                VARCHAR2(100) 
 */
// 사용자 정의 데이터 (오라클 연결 => VO)
public class GenieVO {
    private int no;
    private String poster;
    private String title;
    private String singer;
    private String album;
    private String state;
    private int idcrement;
    private String key;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public int getIdcrement() {
		return idcrement;
	}
	public void setIdcrement(int idcrement) {
		this.idcrement = idcrement;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
   
}
