package com.sist.data;
// ����Ŭ ���� 
/*
 *  �ڹ� + ����Ŭ  => DAO  => �� �ٽ� 
 */
import java.util.*;
import java.sql.*; // JDBC => DBCP(Project) => ORM(Spring)
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
   /*
    *   NO      NOT NULL NUMBER         
		CNO              NUMBER         
		NAME    NOT NULL VARCHAR2(100)  
		SCORE   NOT NULL NUMBER(2,1)    
		ADDRESS NOT NULL VARCHAR2(200)  
		TEL     NOT NULL VARCHAR2(20)   
		TYPE    NOT NULL VARCHAR2(100)  
		PRICE   NOT NULL VARCHAR2(100)  
		PARKING NOT NULL VARCHAR2(100)  
		TIME             VARCHAR2(30)   
		MENU             VARCHAR2(2000) 
		POSTER  NOT NULL VARCHAR2(1000) 
		GOOD             NUMBER         
		SOSO             NUMBER         
		BAD              NUMBER  
    */
   public void foodHouseInsert(FoodHouseVO vo)
   {
	   try
	   {
		   //1.����Ŭ ����
		   getConnection(); // �޼ҵ� ���� => ��� ����(�Ѱ����� ����) , �ݺ������� ȣ�� 
		   //2. SQL������ ����� 
		   String sql="INSERT INTO food_house VALUES("
				     +"(SELECT NVL(MAX(no)+1,1) FROM food_house),"
				     +"?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		   //3. SQL������ ����Ŭ ���� 
		   ps=conn.prepareStatement(sql);
		   //4. ?�� ���� ä��� 
		   ps.setInt(1, vo.getCno()); // '' ������� �ʴ´� 
		   ps.setString(2, vo.getName());
		   ps.setDouble(3, vo.getScore());
		   ps.setString(4, vo.getAddress());
		   ps.setString(5, vo.getTel());
		   ps.setString(6, vo.getType());
		   ps.setString(7, vo.getPrice());
		   ps.setString(8, vo.getParking());
		   ps.setString(9, vo.getTime());
		   ps.setString(10, vo.getMenu());
		   ps.setString(11, vo.getPoster());
		   ps.setInt(12, vo.getGood());
		   ps.setInt(13, vo.getSoso());
		   ps.setInt(14, vo.getBad());
		   //5. ����Ŭ�� ���� ����� ������ 
		   ps.executeUpdate(); // commit() => AutoCommit()
		   //6. => �ӵ� (������(15����)) => ������ �۾� (������)
		   
	   }catch(Exception ex)
	   {
		   ex.printStackTrace(); // ����ó�� 
	   }
	   finally
	   {
		   disConnection(); // ����Ŭ �ݱ�
	   }
   }
   // 4-3. INDEX (������ �۰ų� , ���÷� ���� (X)) => �˻� (ī�װ� ���)
   public ArrayList<FoodCategoryVO> foodCategoryListData()
   {
	   ArrayList<FoodCategoryVO> list=new ArrayList<FoodCategoryVO>();
	   try
	   {
		   //1. ���� 
		   getConnection();
		   //2. SQL����
		   String sql="SELECT cno,title,subject,poster,link "
				     +"FROM food_category "
				     +"ORDER BY cno";
		   //3. SQL���� ���� 
		   ps=conn.prepareStatement(sql); 
		   //4. �����ϰ� ����� �ޱ� 
		   ResultSet rs=ps.executeQuery();
		   while(rs.next()) //  ��� ù��°���� 
		   {
			   FoodCategoryVO vo=new FoodCategoryVO();
			   vo.setCno(rs.getInt(1));
			   vo.setTitle(rs.getString(2));
			   vo.setSubject(rs.getString(3));
			   vo.setPoster(rs.getString(4));
			   vo.setLink("https://www.mangoplate.com"+rs.getString(5));
			   list.add(vo);
		   }
		   rs.close();
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection(); // �ݱ� 
	   }
	   return list;
   }
   public FoodCategoryVO foodCategoryInfoData(int cno)
   {
	   FoodCategoryVO vo=new FoodCategoryVO();
	   try
	   {
		   //1. ����
		   getConnection();
		   //2. SQL
		   String sql="SELECT title,subject "
				     +"FROM food_category "
				     +"WHERE cno="+cno;
		   //3. ����
		   ps=conn.prepareStatement(sql);
		   //4. ����� �ޱ�
		   ResultSet rs=ps.executeQuery();
		   //5. �����ġü Ŀ�� �̵� 
		   rs.next();
		   vo.setTitle(rs.getString(1));
		   vo.setSubject(rs.getString(2));
		   rs.close();
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection();
	   }
	   return vo;
   }
   // ī�װ��� ������ ������ ���� SQL���� 
   public ArrayList<FoodHouseVO> foodList(int cno)
   {
	   ArrayList<FoodHouseVO> list=new ArrayList<FoodHouseVO>();
	   try
	   {
		   // 1. ����
		   getConnection();
		   // 2. SQL���� ����
		   String sql="SELECT no,name,score,address,tel,REPLACE(poster,'#','&') "
				     +"FROM food_house "
				     +"WHERE cno="+cno
				     +"ORDER BY no ASC";
		   ps=conn.prepareStatement(sql);
		   // 3. ���� 
		   ResultSet rs=ps.executeQuery();
		   // 4. �����͸� => ArrayList�� ���
		   while(rs.next())
		   {
			   FoodHouseVO vo=new FoodHouseVO();
			   vo.setNo(rs.getInt(1));
			   vo.setName(rs.getString(2));
			   vo.setScore(rs.getDouble(3));
			   vo.setAddress(rs.getString(4));
			   vo.setTel(rs.getString(5));
			   String s=rs.getString(6);
			   s=s.substring(0,s.indexOf("^"));
			   vo.setPoster(s);
			   list.add(vo);
		   }
		   rs.close();
	   }catch(Exception ex)
	   {
		   ex.printStackTrace(); // ����ó�� 
	   }
	   finally
	   {
		   disConnection();//����Ŭ �ݱ�
	   }
	   return list;
   }
   // ������ ���� �󼼺��� ��� 
   
}













