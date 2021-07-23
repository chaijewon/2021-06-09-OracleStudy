package com.sist.dao;
// 오라클 연결 => CURD가 가능하게 만든다 
import java.sql.*; 
import java.util.*;
/*
 *   재사용 : 상속(is-s) , 포함(has-a) => 기존의 기능을 변경없이 사용할할때
 *           ========   ===========
 *           => 기존의 기능을 변경해서 사용할 경우  
 */
public class MovieDAO {
    private Connection conn; //오라클 연결 
    private PreparedStatement ps; // SQL문장 오라클에 전송 
    private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
    // 드라이버를 통해서 연결 => 드라이버 등록한다 = 클래스 Class.forName("드라이버 클래스")
    public MovieDAO() // 시작과 동시에 드라이버 등록 
    {
    	// 초기화 (드라이버 초기화) => 멤버변수 => 파일읽기,웹 읽기 (초기화 담당 메소드)
    	try
    	{
    		Class.forName("oracle.jdbc.driver.OracleDriver"); // 패키지명.클래스명 => XML(패키지명.클래스)
    		// 클래스가 없는 경우 => ClassNotFoundException (컴파일 예외처리) 
    		// =반드시 예외처리를 한다 
    		// 등록 => 클래스 메모리 할당 => 리플렉션 (핵심:스프링) => 스프링(클래스관리자)
    	}catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
    // 오라클 연결 ==> conn hr/happy => 자바 conn=DriverManager.getConnection(URL,"","")
    public void getConnection()
    {
    	try
    	{
    		conn=DriverManager.getConnection(URL,"hr","happy");
    		// getConnection() ==> conn hr/happy
    		// TCP (신뢰성) => 연결지향적 프로토콜 , UDP 
    		// 전화                            우편 
    	}catch(Exception ex){}
    }
    // 오라클을 닫기 => 접속자당 conn을 한개만 사용이 가능 (XE는 연습용 => 12~15)
    public void disConnection()
    {
    	try
    	{
    		// exit => ps.close(),conn.close() 
    		if(ps!=null) ps.close();
    		if(conn!=null) conn.close(); // 연결되었다면 종료
    	}catch(Exception ex) {}
    }
    // 기능 설정 
    // 1. 데이터 첨부 
    /*
     *  MNO      NOT NULL NUMBER        
		CNO               NUMBER        
		TITLE    NOT NULL VARCHAR2(300) 
		REGDATE           VARCHAR2(100) 
		GENRE    NOT NULL VARCHAR2(200) 
		NATION            VARCHAR2(100) 
		GRADE    NOT NULL VARCHAR2(50)  
		TIME     NOT NULL VARCHAR2(30)  
		SCORE             NUMBER(2,1)   
		SHOWUSER          VARCHAR2(100) 
		POSTER   NOT NULL VARCHAR2(260) 
     */
    // 두가지 => 직접 처리 , 사이트 읽기 
    public void daumMovieInsert(DaumMovieVO vo)
    {
    	try
    	{
    		// 1. 연결
    		getConnection();
    		// 2. SQL문장 
    		String sql="INSERT INTO daum_movie VALUES("
    				  +"daum_mno_seq.nextval,?,?,?,?,?,?,?,?,?,?,?)";
    		// Sequence의 단점 
    		// daum_mno_seq.nextval => SELECT MAX(mno)+1 FROM daum_movie
    		/*
    		 *   1
    		 *   2
    		 *   3
    		 *   4
    		 *   
    		 *   6 
    		 *   7 
    		 *   8  ==> 절대 중복된 숫자가 없다 
    		 */
    		// 3. 전송
    		ps=conn.prepareStatement(sql);
    		// 4. 값을 채운 후 실행 요청
    		ps.setInt(1, vo.getCno());
    		ps.setString(2, vo.getTitle());
    		ps.setString(3, vo.getRegdate());
    		ps.setString(4, vo.getGenre());
    		ps.setString(5, vo.getNation());
    		ps.setString(6, vo.getGrade());
    		ps.setString(7, vo.getTime());
    		ps.setDouble(8, vo.getScore());
    		ps.setString(9, vo.getShowUser());
    		ps.setString(10, vo.getPoster());
    		ps.setString(11, vo.getStory());
    		
    		ps.executeUpdate(); // commit()이 포함이 되어 있다 
    	}catch(Exception ex)
    	{
    		ex.printStackTrace();// 에러 처리
    	}
    	finally
    	{
    		disConnection(); // 연결 해제 
    	}
    }
}








