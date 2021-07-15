package com.sist.dao;
import java.util.*;
import java.sql.*;
public class MusicDAO {
   // 오라클 연결 객체 
   private Connection conn;
   // SQL문장 전송 객체 
   private PreparedStatement ps;
   // 오라클 서버 주소 
   private final String URL="jdbc:oracle:thin:@211.238.142.181:1521:XE";
   // 1. 드라이버 등록
   public MusicDAO()
   {
	   try
	   {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
	   }catch(Exception ex) 
	   {
		   ex.printStackTrace();
	   }
   }
   // 2. 오라클 연결
   public void getConnection()
   {
	   try
	   {
		   conn=DriverManager.getConnection(URL,"hr","happy");
		   // conn hr/happy 
	   }catch(Exception ex) {}
   }
   // 3. 오라클 해제  => JDBC => DBCP => ORM(MyBatis,JPA,Hibernate...)
   public void disConnection()
   {
	   try
	   {
		   if(ps!=null) ps.close(); //연결중이면 닫는다
		   if(conn!=null) conn.close();
	   }catch(Exception ex) {}
   }
   // 4. 데이터 등록 => 지니뮤직
   /*
    *   NO        NOT NULL NUMBER         
		POSTER             VARCHAR2(1000) 
		TITLE              VARCHAR2(200)  
		SINGER             VARCHAR2(100)  
		ALBUM              VARCHAR2(200)  
		STATE              CHAR(4)        
		IDCREMENT          NUMBER         
		KEY                VARCHAR2(100) 
    */
   public void genieInsert(GenieVO vo)
   {
	   try
	   {
		   //1. 연결
		   getConnection();
		   //2. SQL문장을 만든다 
		   /*String sql="INSERT INTO genie_cjw VALUES("
				     +vo.getNo()+",'"+vo.getPoster()+"','"+vo.getTitle()
				     +"','"+vo.getSinger()+"','"+vo.getAlbum()+"','"
				     +vo.getState()+"',"+vo.getIdcrement()+",'"+vo.getKey()+"')";*/
		   String sql="INSERT INTO genie_cjw VALUES(?,?,?,?,?,?,?,?)";
		   ps=conn.prepareStatement(sql);
		   ps.setInt(1, vo.getNo());
		   ps.setString(2, vo.getPoster());
		   ps.setString(3, vo.getTitle());
		   ps.setString(4, vo.getSinger());
		   ps.setString(5, vo.getAlbum());
		   ps.setString(6, vo.getState());
		   ps.setInt(7, vo.getIdcrement());
		   ps.setString(8, vo.getKey());
		   // 실행 명령
		   ps.executeUpdate(); //
		   /*
		    *   executeQuery() ==> SELECT => 데이터를 읽어와서 ResultSet(데이터 검색)
		    *   ==============(X)
		    *   executeUpdate() ==> DML (INSERT,UPDATE,DELETE)
		    *   ==============(O) COMMIT ==> AUTOCOMMIT
		    */
	   }catch(Exception ex)
	   {
		   ex.printStackTrace(); // 에러처리
	   }
	   finally
	   {
		   disConnection(); // 오라클 종료
	   }
   }
   // 5. 데이터 등록 => 메론 
}








