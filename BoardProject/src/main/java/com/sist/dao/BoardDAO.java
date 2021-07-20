package com.sist.dao;
// ����Ŭ ������ ó��(CURD) 
import java.util.*;
import java.sql.*;
/*
 *   ����Ŭ ���� 
 *   1. ����̹� ��� => 1���� ���� (�ѹ��� ȣ�� �޼ҵ� => ������)
 *      Class.forName("oracle.jdbc.driver.OracleDriver") => thin(���Ḹ�ϴ� ����̹�)
 *   2. ���� 
 *      Connection conn=DriverManager.getConnection(URL,"user","password")
 *   3. SQL���� ���� (SELECT , INSERT , UPDATE ,DELETE)
 *      PreparedStatement ps=conn.preparedStatement("SQL����")
 *   4. ���� 
 *      executeUpdate() => ������ COMMIT() => autocommit() : �ڹٴ� �ڵ����� COMMIT����
 *                         => Ʈ����� (�������� ��ǥ ���α׷�)
 *                         => INSERT,UPDATE,DELETE
 *      executeQuery() => ������ ������ ����� => SELECT
 *   5. SELECT�� ����� ��� => ������� �޴´�  ResultSet (�޸𸮿� ������ ����)
 *   6. �ݱ� 
 *      ps.close()
 *      conn.close()
 *      ============= �ݺ� (�޼ҵ� ����)
 */
// 1. ����Ŭ ���� , 2. �� ��� ���̺귯�� , 3. ������ ���� (Jsoup)
// 1. XML,JSON , ANNTATION , Spring , ���Խ� , RESTFUL(�����)
public class BoardDAO {
   // ���� ��ü 
   private Connection conn;
   // SQL���� ��ü
   private PreparedStatement ps;
   // ����Ŭ �ּ� ���� => ������ �Ǹ� �ȵȴ� = ���
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   //                                        === IP           == �����ͺ��̽� (����)
   // 1. ����̹� ���
   public BoardDAO()
   {
	   try
	   {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
		   // new ���� Ŭ���� �޸� �Ҵ� => ���÷��� (Ŭ�����̸����� Ŭ���� ����,�޸� �Ҵ�)
		   // MyBatis , Spring
	   }catch(Exception ex) 
	   {
		   ex.printStackTrace(); // Ŭ�������� �߸� �����ϸ� ���� �߻� 
		   // ClassNotFoundException => Class.forName()
	   }
   }
   // ����Ŭ ���� ==> conn hr/happy
   public void getConnection()
   {
	  try
	  {
		  conn=DriverManager.getConnection(URL,"hr","happy");
	  }catch(Exception ex) {}
   }
   // ����Ŭ �ݱ� ==> exit
   public void disConnection()
   {
	   try
	   {
		   if(ps!=null) ps.close();
		   if(conn!=null) conn.close();
	   }catch(Exception ex) {}
   }
   // ��� => �Խ��� ��� 
   //1. ��� ��� => SELECT
   public ArrayList<BoardVO> boardListData(int page)
   {
	   ArrayList<BoardVO> list=new ArrayList<BoardVO>();
	   try
	   {
		   // 1. ����
		   getConnection();
		   // 2. SQL���� 
		   String sql="SELECT no,subject,name,regdate,hit "
				     +"FROM board "
				     +"ORDER BY no DESC"; // �Խù� �ֽ� �����ͺ��� 
		   ps=conn.prepareStatement(sql);
		   ResultSet rs=ps.executeQuery();
		   int i=0;// 10���� �����ִ� ���� 
		   int j=0;// while�� ���ư��� Ƚ��
		   int cnt=(page*10)-10; // ��� ���� ��ġ 
		   /*
		    *     1page  => 0~9 (whileȽ��)
		    *     2page  => 10~19
		    *     3page  => 20~29
		    */
		   while(rs.next()) // �ζ��κ� (����Ŭ���� 10)
		   {
			   if(i<10 && j>=cnt)
			   {
				   BoardVO vo=new BoardVO();
				   vo.setNo(rs.getInt(1));
				   vo.setSubject(rs.getString(2));
				   vo.setName(rs.getString(3));
				   vo.setRegdate(rs.getDate(4));
				   vo.setHit(rs.getInt(5));
				   // ����
				   list.add(vo);
				   i++;
			   }
			   j++;
		   }
		   
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection();
	   }
	   return list;
   }
   //2. �������� ���ϱ� => SELECT (CEIL())
   public int boardTotalPage()
   {
	   int total=0;
	   try
	   {
		   //1. ����
		   getConnection();
		   //2. SQL
		   String sql="SELECT CEIL(COUNT(*)/10.0) FROM board";
		   //3. SQL���� ���� => ����� �ޱ�
		   ps=conn.prepareStatement(sql);
		   ResultSet rs=ps.executeQuery();
		   rs.next(); // ������ ��� ��ġ�� Ŀ���� �̵� 
		   /*
		    * ============
		    *           2  |
		    * ============
		    */
		   total=rs.getInt(1);
		   /*
		    *   =============================
		    *     aaa    10   21/07/20  10.5
		    *   =============================
		    *      |      |        |      |
		    *   getString(1)
		    *           getInt(2)
		    *                  getDate(3)
		    *                         getDouble(4)
		    */
		   rs.close();
	   }catch(Exception ex)
	   {
		   // ���� ó��
		   ex.printStackTrace();
	   }
	   finally
	   {
		   // �ݱ�  => try,catch������� ������ �����ؾ� �Ǵ� ���� 
		   disConnection();
	   }
	   return total;
   }
   //3. ���뺸�� => SELECT(������ �б�) , UPDATE(��ȸ�� ����) 
   //4. �Խù� �߰� => INSERT
   //5. �����ϱ� => SELECT(���ο��� Ȯ��:��й�ȣ Ȯ��) , UPDATE
   //6. �����ϱ� => SELECT(���ο��� Ȯ��:��й�ȣ Ȯ��) , DELETE
   //7. �˻� => SELECT (LIKE)  => WHERE title '%'||��||'%' => �ڹٿ��� LIKE���� 
   
   
}








