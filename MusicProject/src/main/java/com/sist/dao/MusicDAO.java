package com.sist.dao;
import java.sql.*;
import java.util.*; 
public class MusicDAO {
   // ����Ŭ ���� 
   private Connection conn; //���� Ŭ���� (has-a) => ������ �ʿ䰡 ����(�ִ� �״��)
   // SQL���� ���� 
   private PreparedStatement ps;
   // ����Ŭ �ּ�
   private final String URL="jdbc:oracle:thin:@211.238.142.181:1521:XE";
   // 1. ����̹� ��� 
   public MusicDAO()
   {
	   try
	   {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
	   }catch(Exception ex) {}
   }
   // 2. ����Ŭ ����
   public void getConnection()
   {
	   try
	   {
		   conn=DriverManager.getConnection(URL,"hr","happy");
	   }catch(Exception ex) {}
   }
   // 3. ����Ŭ �ݱ�
   public void disConnection()
   {
	   try
	   {
		   if(ps!=null) ps.close();
		   if(conn!=null) conn.close();
	   }catch(Exception ex) {}
   }
   //=================================== DAO���� ����
   // 4. ���
   // 4-1. ���Ϲ��� 
   public ArrayList<GenieVO> genieAllData()
   {
	   ArrayList<GenieVO> list=new ArrayList<GenieVO>();
	   try
	   {
		   //1. ����
		   getConnection();
		   //2. SQL���� ����
		   String sql="SELECT no,title,poster,singer,album,idcrement,state "
				     +"FROM genie_cjw "
				     +"WHERE no<=100 "
				     +"ORDER BY 1";
		   //3. ����Ŭ ���� => ����� �ޱ�
		   ps=conn.prepareStatement(sql);
		   //4. ����� => ArrayList ���
		   ResultSet rs=ps.executeQuery();
		   while(rs.next()) // rs.next()=> ��� ù��° ��ġ���� ���
		   {
			   // rs.next() => ������ �о� �´� no,title,poster,singer,album,idcrement,state
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
		   //5. �޸� �ݱ�
		   rs.close();
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();//����ó�� 
	   }
	   finally
	   {
		   // ����Ŭ �ݱ� 
		   disConnection();
	   }
	   return list;
   }
   // 4-2. ���
   // 4-3. ����+��� => ���� => JOIN
   // 4-4. ������ => subquery
   // 4-5. ã�� => ����Ŭ Query�� �ٸ���  
}







