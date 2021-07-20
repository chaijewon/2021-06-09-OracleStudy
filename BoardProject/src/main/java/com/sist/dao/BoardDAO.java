package com.sist.dao;
// 오라클 연결후 처리(CURD) 
import java.util.*;
import java.sql.*;
/*
 *   오라클 연결 
 *   1. 드라이버 등록 => 1번만 수행 (한번만 호출 메소드 => 생성자)
 *      Class.forName("oracle.jdbc.driver.OracleDriver") => thin(연결만하는 드라이버)
 *   2. 연결 
 *      Connection conn=DriverManager.getConnection(URL,"user","password")
 *   3. SQL문장 전송 (SELECT , INSERT , UPDATE ,DELETE)
 *      PreparedStatement ps=conn.preparedStatement("SQL문장")
 *   4. 실행 
 *      executeUpdate() => 수행후 COMMIT() => autocommit() : 자바는 자동으로 COMMIT수행
 *                         => 트랜잭션 (스프링의 대표 프로그램)
 *                         => INSERT,UPDATE,DELETE
 *      executeQuery() => 수행후 데이터 결과값 => SELECT
 *   5. SELECT가 실행된 경우 => 결과값을 받는다  ResultSet (메모리에 저장후 실행)
 *   6. 닫기 
 *      ps.close()
 *      conn.close()
 *      ============= 반복 (메소드 제작)
 */
// 1. 오라클 연결 , 2. 웹 출력 라이브러리 , 3. 데이터 수집 (Jsoup)
// 1. XML,JSON , ANNTATION , Spring , 정규식 , RESTFUL(모바일)
public class BoardDAO {
   // 연결 객체 
   private Connection conn;
   // SQL전송 객체
   private PreparedStatement ps;
   // 오라클 주소 설정 => 변경이 되면 안된다 = 상수
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   //                                        === IP           == 데이터베이스 (폴더)
   // 1. 드라이버 등록
   public BoardDAO()
   {
	   try
	   {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
		   // new 없이 클래스 메모리 할당 => 리플렉션 (클래스이름으로 클래스 제어,메모리 할당)
		   // MyBatis , Spring
	   }catch(Exception ex) 
	   {
		   ex.printStackTrace(); // 클래스명을 잘못 기재하면 에러 발생 
		   // ClassNotFoundException => Class.forName()
	   }
   }
   // 오라클 연결 ==> conn hr/happy
   public void getConnection()
   {
	  try
	  {
		  conn=DriverManager.getConnection(URL,"hr","happy");
	  }catch(Exception ex) {}
   }
   // 오라클 닫기 ==> exit
   public void disConnection()
   {
	   try
	   {
		   if(ps!=null) ps.close();
		   if(conn!=null) conn.close();
	   }catch(Exception ex) {}
   }
   // 기능 => 게시판 기능 
   //1. 목록 출력 => SELECT
   public ArrayList<BoardVO> boardListData(int page)
   {
	   ArrayList<BoardVO> list=new ArrayList<BoardVO>();
	   try
	   {
		   // 1. 연결
		   getConnection();
		   // 2. SQL문장 
		   String sql="SELECT no,subject,name,regdate,hit "
				     +"FROM board "
				     +"ORDER BY no DESC"; // 게시물 최신 데이터부터 
		   ps=conn.prepareStatement(sql);
		   ResultSet rs=ps.executeQuery();
		   int i=0;// 10개씩 나눠주는 변수 
		   int j=0;// while이 돌아가는 횟수
		   int cnt=(page*10)-10; // 출력 시작 위치 
		   /*
		    *     1page  => 0~9 (while횟수)
		    *     2page  => 10~19
		    *     3page  => 20~29
		    */
		   while(rs.next()) // 인라인뷰 (오라클에서 10)
		   {
			   if(i<10 && j>=cnt)
			   {
				   BoardVO vo=new BoardVO();
				   vo.setNo(rs.getInt(1));
				   vo.setSubject(rs.getString(2));
				   vo.setName(rs.getString(3));
				   vo.setRegdate(rs.getDate(4));
				   vo.setHit(rs.getInt(5));
				   // 저장
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
   //2. 총페이지 구하기 => SELECT (CEIL())
   public int boardTotalPage()
   {
	   int total=0;
	   try
	   {
		   //1. 연결
		   getConnection();
		   //2. SQL
		   String sql="SELECT CEIL(COUNT(*)/10.0) FROM board";
		   //3. SQL문장 전송 => 결과값 받기
		   ps=conn.prepareStatement(sql);
		   ResultSet rs=ps.executeQuery();
		   rs.next(); // 데이터 출력 위치에 커서를 이동 
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
		   // 에러 처리
		   ex.printStackTrace();
	   }
	   finally
	   {
		   // 닫기  => try,catch상관없이 무조건 수행해야 되는 문장 
		   disConnection();
	   }
	   return total;
   }
   //3. 내용보기 => SELECT(데이터 읽기) , UPDATE(조회수 증가) 
   public BoardVO boardDetail(int no)
   {
	   BoardVO vo=new BoardVO();
	   try
	   {
		   // 1. 연결
		   getConnection();
		   // 2. SQL문장 (조회수 증가) +=,++(X) => hit=hit+1
		   String sql="UPDATE board SET "
				     +"hit=hit+1 "
				     +"WHERE no="+no;
		   ps=conn.prepareStatement(sql);
		   ps.executeUpdate(); // UPDATE 실행 => COMMIT
		   // 3. 실행
		   sql="SELECT no,name,subject,content,regdate,hit "
			  +"FROM board "
			  +"WHERE no="+no; // 항상 => 중복이 없는 데이터를 가지고 있어야 한다 
		      // no NUMBER => PRIMARY KEY => 자동 증가 => SEQUENCE
		   ps=conn.prepareStatement(sql);
		   ResultSet rs=ps.executeQuery();
		   rs.next();
		   // 데이터를 받는다
		   vo.setNo(rs.getInt(1));
		   vo.setName(rs.getString(2));
		   vo.setSubject(rs.getString(3));
		   vo.setContent(rs.getString(4));
		   vo.setRegdate(rs.getDate(5));
		   vo.setHit(rs.getInt(6));
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
   //4. 게시물 추가 => INSERT
   public void boardInsert(BoardVO vo)
   {
	   try
	   {
		   //1. 연결
		   getConnection();
		   //2. SQL문장 
		   // 2-1 게시물 번호의 최대값 +1 
		   //String sql="SELECT NVL(MAX(no)+1,1) FROM board";// 자동증가번호 
		   //ps=conn.prepareStatement(sql);
		   // 결과값 받기 
		   //ResultSet rs=ps.executeQuery();
		   //rs.next();
		   //int max=rs.getInt(1);
		   //rs.close();
		   // 연결 <=> 닫기
		   // 서브쿼리 : SELECT , table,column,INSERT,UPDATE,DELETE
		   String sql="INSERT INTO board(no,name,subject,content,pwd) "
			  +"VALUES((SELECT NVL(MAX(no)+1,1) FROM board),?,?,?,?)";
		   ps=conn.prepareStatement(sql);
		   ps.setString(1, vo.getName()); // String => ''
		   ps.setString(2, vo.getSubject());
		   ps.setString(3, vo.getContent());
		   ps.setString(4, vo.getPwd());
		   
		   //3. SQL문장 실행
		   ps.executeUpdate();// commit()
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection();
	   }
   }
   //5. 수정하기 => SELECT(본인여부 확인:비밀번호 확인) , UPDATE
   //6. 삭제하기 => SELECT(본인여부 확인:비밀번호 확인) , DELETE
   public void boardDelete(int no)
   {
	   try
	   {
		   getConnection();
		   // 비밀번호 검색 
		   String sql="DELETE FROM board WHERE no="+no;
		   ps=conn.prepareStatement(sql);
		   ps.executeUpdate();
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection();
	   }
   }
   //7. 검색 => SELECT (LIKE)  => WHERE title '%'||값||'%' => 자바에서 LIKE사용시 
   public ArrayList<BoardVO> boardFind(String fs,String ss)
   {
	   ArrayList<BoardVO> list=new ArrayList<BoardVO>();
	   try
	   {
		   //1. 연결
		   getConnection();
		   //2. SQL문장 
		   // fs= name,subject ,content
		   String sql="SELECT no,subject,name,regdate,hit "
				     +"FROM board "
				     +"WHERE "+fs+" LIKE '%'||?||'%'";// 오라클과 다르다 
		   //3. 실행후 결과값 받기
		   ps=conn.prepareStatement(sql);
		   ps.setString(1, ss);
		   ResultSet rs=ps.executeQuery();
		   
		   //4. ArrayList에 값 채우기
		   while(rs.next())
		   {
			   BoardVO vo=new BoardVO();
			   vo.setNo(rs.getInt(1));
			   vo.setSubject(rs.getString(2));
			   vo.setName(rs.getString(3));
			   vo.setRegdate(rs.getDate(4));
			   vo.setHit(rs.getInt(5));
			   // 저장
			   list.add(vo);
		   }
		   rs.close();
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
   public int boardFindCount(String fs,String ss)
   {
	   int count=0;
	   try
	   {
		   //1. 연결
		   getConnection();
		   //2. SQL문장 
		   // fs= name,subject ,content
		   String sql="SELECT COUNT(*) "
				     +"FROM board "
				     +"WHERE "+fs+" LIKE '%'||?||'%'";// 오라클과 다르다 
		   //3. 실행후 결과값 받기
		   ps=conn.prepareStatement(sql);
		   ps.setString(1, ss);
		   ResultSet rs=ps.executeQuery();
		   rs.next();
		   count=rs.getInt(1);
		   rs.close();
	   }catch(Exception ex)
	   {
		  ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection();
	   }
	   return count;
   }
   
}








