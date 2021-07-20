package com.sist.data;
// ����Ŭ ���� 
import java.util.*;
import java.sql.*;
public class FoodDAO {
   private Connection conn;// ����Ŭ ���� 
   private PreparedStatement ps;// SQL���� 
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   //                                        => 211.238.142.()
   // 1. ����̹� ��� 
   public FoodDAO()
   {
	   try
	   {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
   }
   // 2. ����
   public void getConnection()
   {
	   // View , Sequence , ����¡��� , �ó��� , �ε��� , PL/SQL
	   try
	   {
		   conn=DriverManager.getConnection(URL,"hr","happy");
	   }catch(Exception ex) {}
   }
   // 3. �ݱ�
   public void disConnection()
   {
	   try
	   {
		   if(ps!=null) ps.close();
		   if(conn!=null) conn.close();
	   }catch(Exception ex) {}
   }
   // 4. ��� 
   // 4-1. �����÷���Ʈ => ī�װ� ����
   // insert,update,delete => ������� ���� => ������ (void)
   public void foodCategoryInsert(FoodCategoryVO vo) //vo=row
   {
	   try
	   {
		   // 1.����
		   /*
		    *   �������� 
		    *   1) ������ �������� : �÷��� �Ѱ� , �����Ͱ��� 1�� 
		    *      WHERE �÷���(�񱳿�����)(SELECT ~~)
		    *                          ============ ������� 1�� 
		    *   2) ������ �������� : �÷��� �Ѱ� , �����Ͱ��� ������ 
		    *      WHERE �÷��� =(X) 
		    *                       IN(SELECT ~) => ���ÿ� ����
		    *                       ANY ==> SOME()
		    *                       > ANY (SELECT DISTINCT deptno FROM emp)
		    *                             => 10,20,30 ==> ���� ������ => 10
		    *                       < ANY (SELECT DISTINCT deptno FROM emp)
		    *                             => 10,20,30 ==> ���� ū�� => 30
		    *                       > ALL (SELECT DISTINCT deptno FROM emp)
		    *                             => 10,20,30 ==> ���� ū�� => 30
		    *                       < ALL (SELECT DISTINCT deptno FROM emp)
		    *                             => 10,20,30 ==> ���� ������ => 10
		    *                       
		    *   3) �����÷� �������� : �÷�:2�� , �����Ͱ� 1
		    *      WHERE (job,sal) =(SELECT job,sal ~~~)
		    *   4) ��Į�� �������� : �÷���� ��� => ������� �Ѱ� ���� 
		    *       ==> �������� ���̺��� �����͸� ��� ���� ��� 
		    *      SELECT empno,(SELECT ~~ ),(SELECT ~~) ==> JOIN��� 
		    *      FROM emp
		    *   5) ���̺� ��� ��� : �ζ��κ�
		    *      SELECT ~~
		    *      FROM (SELECT ~~) => ���������� ��µ� �÷��� ����� ���� 
		    *   ���� : SQL���� �������� �Ѱ��� ��� �ѹ��� ���� (�ڹٿ��� �ַ� ���)
		    *         ���� : TOP-N (ó�����ʹ� ������ �´� , �߰����� ������ ���� ���Ѵ�)
		    *   ��� SQL���忡�� ����� ���� 
		    *   ��) ���̺� ���鶧
		    *       CREATE TABLE table_name
		    *       AS
		    *       SELECT ~
		    *      ������ �����͸� ���ο� ���̺� ÷�� 
		    *      INSERT INTO table 
		    *      SELECT ~~
		    *      INSERT,UPDATE,DELETE����� ����  
		    */
		   getConnection();
		   String sql="INSERT INTO food_category VALUES((SELECT NVL(MAX(cno)+1,1) FROM food_category),?,?,?,?)";
		   ps=conn.prepareStatement(sql);
		   // ?�� ���� ä��� 
		   ps.setString(1, vo.getTitle());
		   ps.setString(2, vo.getSubject());
		   ps.setString(3, vo.getPoster());
		   ps.setString(4, vo.getLink());
		   // ���� ��û => COMMIT
		   ps.executeUpdate();
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection(); // �ݱ�
	   }
   }
   // 4-2. �����÷���Ʈ => ���� ���� 
}





