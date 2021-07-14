package com.sist.dao;
// ����Ŭ�� �����ؼ� �����͸� ������ �´� => Emp => ����
import java.util.*;
import java.sql.*; 
/*
 *   java.util.* => ArrayList (Emp 14��)
 *   java.sql.*
 *   ===========
 *     Connection : ����Ŭ ���� 
 *     PreparedStatement : SQL������ ����Ŭ�� ���� => ������� �о� �´� 
 *     ResultSet : ������� ����Ǵ� �޸� ���� 
 *     
 *    ����� ��û ==============> �ڹ� (��û���� �޴´�=> SQL����) ======> ����Ŭ�� ����
 *               ��û���� �޴´�            �����(��ü�� ���=>�������� ����)
 *                                       ================
 *                                       ã�� => ������ (ArrayList:������ �������� �ʴ´�)
 *    ==========
 *    ������,������
 */
public class EmpDAO {
     private Connection conn;
     private PreparedStatement ps;
     // ����Ŭ ���� ==> jdbc:oracle:thin:@localhost:1521:XE
     private final String URL="jdbc:oracle:thin:@211.238.142.181:1521:XE";
     
     // 1. ����̹� ��� => thin(���� ���Ḹ ���ִ� ����=����) oci(����)
     // �ѹ��� ���� 
     public EmpDAO()
     {
    	 try
    	 {
    		 Class.forName("oracle.jdbc.driver.OracleDriver");
    		 // Class.forName() => ��ϵ� Ŭ���� �޸� �Ҵ�
    		 // Ŭ�����̸����� �޸� �Ҵ� => ���÷��� 
    		 // �޸� �Ҵ� (Ŭ���� ����) => new�� �ִ� ���� �ƴϴ� , �̸����� ����
    		 // => ClassNotFoundException => ������ ���� (�ݵ�� ����ó��)
    	 }catch(Exception ex)
    	 {
    		 ex.printStackTrace();// �����޼��� ��� 
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
     // 3. ����Ŭ ���� ����
     public void disConnection()
     {
    	 try
    	 {
    		 if(ps!=null)  // ����Ŭ�ϰ� ������̸� ����
    			 ps.close();
    		 if(conn!=null) // ����Ŭ�� �����ִٸ� �ݴ´� 
    			 conn.close();
    		 // ����Ŭ => exit
    	 }catch(Exception ex) {}
     }
     // 4. ��� (���, ã�� , �󼼺���) => SELECT
     public ArrayList<Emp> empListData() //Emp�� ��� ���� => ������ ��Ƽ� �������� ����
     {
    	 ArrayList<Emp> list=new ArrayList<Emp>();//����Ŭ�� �ִ� �����͸� �о ä���ش�
    	 try
    	 {
    		 // 1. ����Ŭ ���� 
    		 getConnection();
    		 // 2. ����Ŭ�� SQL������ ���� 
    		 // "SELECT empno,ename,job,hiredate FROM emp" From�� ��ġ�� ���� 
    		 // this.conn null => URL
    		 String sql="SELECT empno,ename,job, hiredate "
    				   +"FROM emp"; // ;�� �����ϱ� ������ ;�� ������� �ʴ´� 
    		 // 3. ����Ŭ�� SQL���� ����
    		 ps=conn.prepareStatement(sql);
    		 // 4. SQL���� ������ ����� �޸𸮿� ���� 
    		 ResultSet rs=ps.executeQuery(); //rs�ȿ� ����Ŭ���� ����� ������ �� �ִ�
    		 // 5. ������� ArrayList�� ��´� 
    		 /*   
    		  *      no    name    sex   birthday
    		  *   ================================
    		  *      1    ȫ�浿    ����    20/01/01  while�� 1�� ����� 4���� ������ �б�
    		  *   ================================
    		  *      2    ��û��    ����    21/01/01
    		  *   ================================
    		  *     ����   ���ڿ�    ���ڿ�    ��¥�� 
    		  *     getInt(1) getString(2) getString(3) getDate(4) => ArrayList�� ����
    		  *     getInt(1) getString(2) getString(3) getDate(4) => ArrayList
    		  *     4.5 => getDouble()
    		  *   ==
    		  *   ==
    		  *   ==
    		  */
    		 while(rs.next()) // ����� ù��° ��ġ���� �����͸� �о� �´� 
    			 // ������ ������ �´� => Record
    		 {
    			 Emp emp=new Emp();// �Ѹ� ���� ������ ������ �ִ� Ŭ���� (VO,DTO)
    			 emp.setEmpno(rs.getInt(1));
    			 emp.setEname(rs.getString(2));
    			 emp.setJob(rs.getString(3));
    			 emp.setHiredate(rs.getDate(4));
    			 list.add(emp);//14�� ��� ������ �� �ִ�
    		 }
    		 // 6. �޸� �ݱ�
    		 rs.close();
    		 
    	 }catch(Exception ex)
    	 {
    		 ex.printStackTrace(); // ���� �޼��� Ȯ�� 
    	 }
    	 finally
    	 {
    		 // ����Ŭ�� �ݴ´� (NO Error, Error)
    		 disConnection();//try(������๮��),catch(����) ������� ������ ���� 
    	 }
    	 return list;
     }
     // ��� => ������ 8�� (�) => ���ϴ� �����͸� ���� (��� �̸� ���� �Ի���)
     // SELECT empno,ename,job,hiredate FROM emp
     // SELECT * FROM emp WHERE empno=7788
     public Emp empDetailData(int empno)
     {
    	 Emp emp=new Emp();// �Ѹ� ���� ������ ���´� 
    	 try
    	 {
    		 //1. ����Ŭ ����
    		 getConnection(); // �޼ҵ� : �Ѱ� ��� ���� , �ݺ��� �ڵ� ���� => �ٸ� Ŭ������ ����
    		 //2. SQL���� ����
    		 String sql="SELECT * FROM emp "
    				   +"WHERE empno="+empno;
    		 //3. SQL�� ����Ŭ ���� 
    		 ps=conn.prepareStatement(sql);
    		 //4. ������ ����� �ޱ�
    		 ResultSet rs=ps.executeQuery();
    		 rs.next(); // ������ ��� ��ġ�� cursor
    		 //5. emp�� ���� ä��� empno,ename,job,mgr,hiredate,sal,comm,deptno
    		 emp.setEmpno(rs.getInt(1));
    		 emp.setEname(rs.getString(2));
    		 emp.setJob(rs.getString(3));
    		 emp.setMgr(rs.getInt(4));
    		 emp.setHiredate(rs.getDate(5));
    		 emp.setSal(rs.getInt(6));
    		 emp.setComm(rs.getInt(7));
    		 emp.setDeptno(rs.getInt(8));
    		 
    		 //6. �޸� �ݱ�
    		 rs.close();
    	 }catch(Exception ex)
    	 {
    		 ex.printStackTrace();// ����ó��
    	 }
    	 finally
    	 {
    		 disConnection();// ����Ŭ �ݱ�
    	 }
    	 return emp;
     }
     // SEELCT * FROM emp WHERE ename LIKE '%()%'
     // ����Ŭ => ���� ���� ����Ŭ 
     // ��ȭ ��� 
     public void movieInsert(Movie movie)
     {
    	 try
    	 {
    		 getConnection();//���� 
    		 // 2. SQL���� ����� 
    		 String sql="INSERT INTO movie VALUES(?,?,?,?,?,?,?,?)";
    		 ps=conn.prepareStatement(sql);
    		 // ������ => ?�� ���� ä���� ���� 
    		 // genre,poster,actor,regdate,grade,director;
    		 ps.setInt(1, movie.getMno());
    		 ps.setString(2, movie.getTitle());
    		 ps.setString(3, movie.getGenre());
    		 ps.setString(4, movie.getPoster());
    		 ps.setString(5, movie.getActor());
    		 ps.setString(6, movie.getRegdate());
    		 ps.setString(7, movie.getGrade());
    		 ps.setString(8, movie.getDirector());
    		 ps.executeUpdate();
    	 }catch(Exception ex)
    	 {
    		 ex.printStackTrace(); // ����ó��
    	 }
    	 finally
    	 {
    		 disConnection();//����Ŭ �ݱ� 
    	 }
     }
}











