package com.sist.dao;
import java.sql.*;
import java.util.*; 
public class MusicDAO {
   // 오라클 연결 
   private Connection conn; //포함 클래스 (has-a) => 변경할 필요가 없다(있는 그대로)
   // SQL문장 전송 
   private PreparedStatement ps;
   // 오라클 주소
   private final String URL="jdbc:oracle:thin:@211.238.142.181:1521:XE";
   // 1. 드라이버 등록 
   public MusicDAO()
   {
	   try
	   {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
	   }catch(Exception ex) {}
   }
   // 2. 오라클 연결
   public void getConnection()
   {
	   try
	   {
		   conn=DriverManager.getConnection(URL,"hr","happy");
	   }catch(Exception ex) {}
   }
   // 3. 오라클 닫기
   public void disConnection()
   {
	   try
	   {
		   if(ps!=null) ps.close();
		   if(conn!=null) conn.close();
	   }catch(Exception ex) {}
   }
   //=================================== DAO공통 사항
   // 4. 기능
   // 4-1. 지니뮤직 
   public ArrayList<GenieVO> genieAllData()
   {
	   ArrayList<GenieVO> list=new ArrayList<GenieVO>();
	   try
	   {
		   //1. 연결
		   getConnection();
		   //2. SQL문장 제작
		   String sql="SELECT no,title,poster,singer,album,idcrement,state "
				     +"FROM genie_cjw "
				     +"WHERE no<=100 "
				     +"ORDER BY 1";
		   //3. 오라클 전송 => 결과값 받기
		   ps=conn.prepareStatement(sql);
		   //4. 결과값 => ArrayList 출력
		   ResultSet rs=ps.executeQuery();
		   while(rs.next()) // rs.next()=> 출력 첫번째 위치부터 출력
		   {
			   // rs.next() => 한줄을 읽어 온다 no,title,poster,singer,album,idcrement,state
		       GenieVO vo=new GenieVO();
		       vo.setNo(rs.getInt(1));
		       vo.setTitle(rs.getString(2));
		       vo.setPoster(rs.getString(3));
		       vo.setSinger(rs.getString(4));
		       vo.setAlbum(rs.getString(5));
		       vo.setIdcerment(rs.getInt(6));
		       vo.setState(rs.getString(7));
		       list.add(vo);
		   }
		   //5. 메모리 닫기
		   rs.close();
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();//에러처리 
	   }
	   finally
	   {
		   // 오라클 닫기 
		   disConnection();
	   }
	   return list;
   }
   // 4-2. 멜론
   // 4-3. 지니+멜론 => 순위 => JOIN
   // 4-4. 동영상 => subquery
   // 4-5. 찾기 => 오라클 Query와 다르다  
}







