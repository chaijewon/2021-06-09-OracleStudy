package com.sist.dao;
// 오라클을 연결해서 데이터를 가지고 온다 => Emp => 전송
import java.util.*;
import java.sql.*; 
/*
 *   java.util.* => ArrayList (Emp 14개)
 *   java.sql.*
 *   ===========
 *     Connection : 오라클 연결 
 *     PreparedStatement : SQL문장을 오라클로 전송 => 결과값을 읽어 온다 
 *     ResultSet : 결과값이 저장되는 메모리 공간 
 *     
 *    사용자 요청 ==============> 자바 (요청값을 받는다=> SQL문장) ======> 오라클로 전송
 *               요청값을 받는다            결과값(전체를 묶어서=>브라우저로 전송)
 *                                       ================
 *                                       찾기 => 가변형 (ArrayList:갯수를 지정하지 않는다)
 *    ==========
 *    브라우저,윈도우
 */
public class EmpDAO {
     private Connection conn;
     private PreparedStatement ps;
     // 오라클 서버 ==> jdbc:oracle:thin:@localhost:1521:XE
     private final String URL="jdbc:oracle:thin:@211.238.142.181:1521:XE";
     
     // 1. 드라이버 등록 => thin(단지 연결만 해주는 역할=무료) oci(유료)
     // 한번만 수행 
     public EmpDAO()
     {
    	 try
    	 {
    		 Class.forName("oracle.jdbc.driver.OracleDriver");
    		 // Class.forName() => 등록된 클래스 메모리 할당
    		 // 클래스이름으로 메모리 할당 => 리플랙션 
    		 // 메모리 할당 (클래스 저장) => new만 있는 것은 아니다 , 이름으로 저장
    		 // => ClassNotFoundException => 컴파일 에러 (반드시 예외처리)
    	 }catch(Exception ex)
    	 {
    		 ex.printStackTrace();// 에러메세지 출력 
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
     // 3. 오라클 연결 종료
     public void disConnection()
     {
    	 try
    	 {
    		 if(ps!=null)  // 오라클하고 통신중이면 종료
    			 ps.close();
    		 if(conn!=null) // 오라클이 열려있다면 닫는다 
    			 conn.close();
    		 // 오라클 => exit
    	 }catch(Exception ex) {}
     }
     // 4. 기능 (목록, 찾기 , 상세보기) => SELECT
     public ArrayList<Emp> empListData() //Emp가 사원 정보 => 여러개 모아서 브라우저로 전송
     {
    	 ArrayList<Emp> list=new ArrayList<Emp>();//오라클에 있는 데이터를 읽어서 채워준다
    	 try
    	 {
    		 // 1. 오라클 연결 
    		 getConnection();
    		 // 2. 오라클로 SQL문장을 제작 
    		 // "SELECT empno,ename,job,hiredate FROM emp" From의 위치가 없다 
    		 // this.conn null => URL
    		 String sql="SELECT empno,ename,job, hiredate "
    				   +"FROM emp"; // ;을 포함하기 때문에 ;을 사용하지 않는다 
    		 // 3. 오라클로 SQL문장 전송
    		 ps=conn.prepareStatement(sql);
    		 // 4. SQL문장 실행한 결과를 메모리에 저장 
    		 ResultSet rs=ps.executeQuery(); //rs안에 오라클에서 실행된 데이터 들어가 있다
    		 // 5. 결과값을 ArrayList에 담는다 
    		 /*   
    		  *      no    name    sex   birthday
    		  *   ================================
    		  *      1    홍길동    남자    20/01/01  while문 1번 수행시 4개의 데이터 읽기
    		  *   ================================
    		  *      2    심청이    여자    21/01/01
    		  *   ================================
    		  *     정수   문자열    문자열    날짜형 
    		  *     getInt(1) getString(2) getString(3) getDate(4) => ArrayList에 저장
    		  *     getInt(1) getString(2) getString(3) getDate(4) => ArrayList
    		  *     4.5 => getDouble()
    		  *   ==
    		  *   ==
    		  *   ==
    		  */
    		 while(rs.next()) // 출력한 첫번째 위치부터 데이터를 읽어 온다 
    			 // 한줄을 가지고 온다 => Record
    		 {
    			 Emp emp=new Emp();// 한명에 대한 정보를 가지고 있는 클래스 (VO,DTO)
    			 emp.setEmpno(rs.getInt(1));
    			 emp.setEname(rs.getString(2));
    			 emp.setJob(rs.getString(3));
    			 emp.setHiredate(rs.getDate(4));
    			 list.add(emp);//14개 사원 정보가 들어가 있다
    		 }
    		 // 6. 메모리 닫기
    		 rs.close();
    		 
    	 }catch(Exception ex)
    	 {
    		 ex.printStackTrace(); // 에러 메세지 확인 
    	 }
    	 finally
    	 {
    		 // 오라클을 닫는다 (NO Error, Error)
    		 disConnection();//try(정상수행문장),catch(에러) 상관없이 무조건 수행 
    	 }
    	 return list;
     }
     // 목록 => 데이터 8개 (몇개) => 원하는 데이터만 설정 (사번 이름 직위 입사일)
     // SELECT empno,ename,job,hiredate FROM emp
     // SELECT * FROM emp WHERE empno=7788
     public Emp empDetailData(int empno)
     {
    	 Emp emp=new Emp();// 한명에 대한 정보를 얻어온다 
    	 try
    	 {
    		 //1. 오라클 연결
    		 getConnection(); // 메소드 : 한개 기능 수행 , 반복이 코딩 재사용 => 다른 클래스와 연결
    		 //2. SQL문장 제작
    		 String sql="SELECT * FROM emp "
    				   +"WHERE empno="+empno;
    		 //3. SQL을 오라클 전송 
    		 ps=conn.prepareStatement(sql);
    		 //4. 실행후 결과가 받기
    		 ResultSet rs=ps.executeQuery();
    		 rs.next(); // 데이터 출력 위치에 cursor
    		 //5. emp에 값을 채운다 empno,ename,job,mgr,hiredate,sal,comm,deptno
    		 emp.setEmpno(rs.getInt(1));
    		 emp.setEname(rs.getString(2));
    		 emp.setJob(rs.getString(3));
    		 emp.setMgr(rs.getInt(4));
    		 emp.setHiredate(rs.getDate(5));
    		 emp.setSal(rs.getInt(6));
    		 emp.setComm(rs.getInt(7));
    		 emp.setDeptno(rs.getInt(8));
    		 
    		 //6. 메모리 닫기
    		 rs.close();
    	 }catch(Exception ex)
    	 {
    		 ex.printStackTrace();// 에러처리
    	 }
    	 finally
    	 {
    		 disConnection();// 오라클 닫기
    	 }
    	 return emp;
     }
     // SEELCT * FROM emp WHERE ename LIKE '%()%'
     // 오라클 => 웹과 관련 오라클 
     // 영화 등록 
     public void movieInsert(Movie movie)
     {
    	 try
    	 {
    		 getConnection();//연결 
    		 // 2. SQL문장 만들기 
    		 String sql="INSERT INTO movie VALUES(?,?,?,?,?,?,?,?)";
    		 ps=conn.prepareStatement(sql);
    		 // 실행전 => ?에 값을 채워서 실행 
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
    		 ex.printStackTrace(); // 에러처리
    	 }
    	 finally
    	 {
    		 disConnection();//오라클 닫기 
    	 }
     }
}











