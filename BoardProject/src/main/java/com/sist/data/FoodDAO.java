package com.sist.data;
// 오라클 연결 
/*
 *  자바 + 오라클  => DAO  => 웹 핵심 
 */
import java.util.*;
import java.sql.*; // JDBC => DBCP(Project) => ORM(Spring)
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
		   //1.오라클 연결
		   getConnection(); // 메소드 시점 => 기능 수행(한가지만 수행) , 반복적으로 호출 
		   //2. SQL문장을 만든다 
		   String sql="INSERT INTO food_house VALUES("
				     +"(SELECT NVL(MAX(no)+1,1) FROM food_house),"
				     +"?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		   //3. SQL문장을 오라클 전송 
		   ps=conn.prepareStatement(sql);
		   //4. ?에 값을 채운다 
		   ps.setInt(1, vo.getCno()); // '' 사용하지 않는다 
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
		   //5. 오라클에 실행 명령을 내린다 
		   ps.executeUpdate(); // commit() => AutoCommit()
		   //6. => 속도 (레시피(15만개)) => 나눠서 작업 (쓰레드)
		   
	   }catch(Exception ex)
	   {
		   ex.printStackTrace(); // 에러처리 
	   }
	   finally
	   {
		   disConnection(); // 오라클 닫기
	   }
   }
   // 4-3. INDEX (데이터 작거나 , 수시로 변경 (X)) => 검색 (카테고리 출력)
   public ArrayList<FoodCategoryVO> foodCategoryListData()
   {
	   ArrayList<FoodCategoryVO> list=new ArrayList<FoodCategoryVO>();
	   try
	   {
		   //1. 열기 
		   getConnection();
		   //2. SQL문장
		   String sql="SELECT cno,title,subject,poster,link "
				     +"FROM food_category "
				     +"ORDER BY cno";
		   //3. SQL문장 실행 
		   ps=conn.prepareStatement(sql); 
		   //4. 실행하고 결과값 받기 
		   ResultSet rs=ps.executeQuery();
		   while(rs.next()) //  출력 첫번째부터 
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
		   disConnection(); // 닫기 
	   }
	   return list;
   }
   public FoodCategoryVO foodCategoryInfoData(int cno)
   {
	   FoodCategoryVO vo=new FoodCategoryVO();
	   try
	   {
		   //1. 연결
		   getConnection();
		   //2. SQL
		   String sql="SELECT title,subject "
				     +"FROM food_category "
				     +"WHERE cno="+cno;
		   //3. 전송
		   ps=conn.prepareStatement(sql);
		   //4. 결과값 받기
		   ResultSet rs=ps.executeQuery();
		   //5. 출력위치체 커서 이동 
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
   // 카테고리별 맛집을 가지고 오는 SQL문장 
   public ArrayList<FoodHouseVO> foodList(int cno)
   {
	   ArrayList<FoodHouseVO> list=new ArrayList<FoodHouseVO>();
	   try
	   {
		   // 1. 열기
		   getConnection();
		   // 2. SQL문장 제작
		   String sql="SELECT no,name,score,address,tel,REPLACE(poster,'#','&') "
				     +"FROM food_house "
				     +"WHERE cno="+cno
				     +"ORDER BY no ASC";
		   ps=conn.prepareStatement(sql);
		   // 3. 실행 
		   ResultSet rs=ps.executeQuery();
		   // 4. 데이터를 => ArrayList에 담기
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
		   ex.printStackTrace(); // 에러처리 
	   }
	   finally
	   {
		   disConnection();//오라클 닫기
	   }
	   return list;
   }
   // 맛집에 대한 상세보기 출력 
   
}













