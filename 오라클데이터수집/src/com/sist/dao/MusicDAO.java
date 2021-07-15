package com.sist.dao;
import java.util.*;
import java.sql.*;
public class MusicDAO {
   // ����Ŭ ���� ��ü 
   private Connection conn;
   // SQL���� ���� ��ü 
   private PreparedStatement ps;
   // ����Ŭ ���� �ּ� 
   private final String URL="jdbc:oracle:thin:@211.238.142.181:1521:XE";
   // 1. ����̹� ���
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
   // 2. ����Ŭ ����
   public void getConnection()
   {
	   try
	   {
		   conn=DriverManager.getConnection(URL,"hr","happy");
		   // conn hr/happy 
	   }catch(Exception ex) {}
   }
   // 3. ����Ŭ ����  => JDBC => DBCP => ORM(MyBatis,JPA,Hibernate...)
   public void disConnection()
   {
	   try
	   {
		   if(ps!=null) ps.close(); //�������̸� �ݴ´�
		   if(conn!=null) conn.close();
	   }catch(Exception ex) {}
   }
   // 4. ������ ��� => ���Ϲ���
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
		   //1. ����
		   getConnection();
		   //2. SQL������ ����� 
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
		   // ���� ���
		   ps.executeUpdate(); //
		   /*
		    *   executeQuery() ==> SELECT => �����͸� �о�ͼ� ResultSet(������ �˻�)
		    *   ==============(X)
		    *   executeUpdate() ==> DML (INSERT,UPDATE,DELETE)
		    *   ==============(O) COMMIT ==> AUTOCOMMIT
		    */
	   }catch(Exception ex)
	   {
		   ex.printStackTrace(); // ����ó��
	   }
	   finally
	   {
		   disConnection(); // ����Ŭ ����
	   }
   }
   // 5. ������ ��� => �޷� 
}








