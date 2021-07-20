package com.sist.data;
// 오라클 연결 
import java.util.*;
import java.sql.*;
public class FoodDAO {
   private Connection conn;// 오라클 연결 
   private PreparedStatement ps;// SQL전송 
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   //                                        => 211.238.142.()
   // 1. 드라이버 등록 
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
   // 2. 연결
   public void getConnection()
   {
	   // View , Sequence , 페이징기법 , 시노임 , 인덱스 , PL/SQL
	   try
	   {
		   conn=DriverManager.getConnection(URL,"hr","happy");
	   }catch(Exception ex) {}
   }
   // 3. 닫기
   public void disConnection()
   {
	   try
	   {
		   if(ps!=null) ps.close();
		   if(conn!=null) conn.close();
	   }catch(Exception ex) {}
   }
   // 4. 기능 
   // 4-1. 망고플레이트 => 카테고리 저장
   // insert,update,delete => 결과값이 없다 => 리턴형 (void)
   public void foodCategoryInsert(FoodCategoryVO vo) //vo=row
   {
	   try
	   {
		   // 1.연결
		   /*
		    *   서브쿼리 
		    *   1) 단일행 서브쿼리 : 컬럼이 한개 , 데이터값이 1개 
		    *      WHERE 컬럼명(비교연산자)(SELECT ~~)
		    *                          ============ 결과값이 1개 
		    *   2) 다중행 서브쿼리 : 컬럼이 한개 , 데이터값이 여러개 
		    *      WHERE 컬럼명 =(X) 
		    *                       IN(SELECT ~) => 동시에 적용
		    *                       ANY ==> SOME()
		    *                       > ANY (SELECT DISTINCT deptno FROM emp)
		    *                             => 10,20,30 ==> 가장 작은값 => 10
		    *                       < ANY (SELECT DISTINCT deptno FROM emp)
		    *                             => 10,20,30 ==> 가장 큰값 => 30
		    *                       > ALL (SELECT DISTINCT deptno FROM emp)
		    *                             => 10,20,30 ==> 가장 큰값 => 30
		    *                       < ALL (SELECT DISTINCT deptno FROM emp)
		    *                             => 10,20,30 ==> 가장 작은값 => 10
		    *                       
		    *   3) 다중컬럼 서브쿼리 : 컬럼:2개 , 데이터값 1
		    *      WHERE (job,sal) =(SELECT job,sal ~~~)
		    *   4) 스칼라 서브쿼리 : 컬럼대신 사용 => 결과값은 한개 존재 
		    *       ==> 여러개의 테이블에서 데이터를 얻어 오는 경우 
		    *      SELECT empno,(SELECT ~~ ),(SELECT ~~) ==> JOIN대신 
		    *      FROM emp
		    *   5) 테이블 대신 사용 : 인라인뷰
		    *      SELECT ~~
		    *      FROM (SELECT ~~) => 서브쿼리에 출력된 컬럼만 사용이 가능 
		    *   목적 : SQL문장 여러개를 한개로 묶어서 한번에 실행 (자바에서 주로 사용)
		    *         단점 : TOP-N (처음부터는 가지고 온다 , 중간에는 가지고 오지 못한다)
		    *   모든 SQL문장에서 사용이 가능 
		    *   예) 테이블 만들때
		    *       CREATE TABLE table_name
		    *       AS
		    *       SELECT ~
		    *      기존의 데이터를 새로운 테이블에 첨부 
		    *      INSERT INTO table 
		    *      SELECT ~~
		    *      INSERT,UPDATE,DELETE사용이 가능  
		    */
		   getConnection();
		   String sql="INSERT INTO food_category VALUES((SELECT NVL(MAX(cno)+1,1) FROM food_category),?,?,?,?)";
		   ps=conn.prepareStatement(sql);
		   // ?에 값을 채운다 
		   ps.setString(1, vo.getTitle());
		   ps.setString(2, vo.getSubject());
		   ps.setString(3, vo.getPoster());
		   ps.setString(4, vo.getLink());
		   // 실행 요청 => COMMIT
		   ps.executeUpdate();
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection(); // 닫기
	   }
   }
   // 4-2. 망고플레이트 => 맛집 저장 
}





