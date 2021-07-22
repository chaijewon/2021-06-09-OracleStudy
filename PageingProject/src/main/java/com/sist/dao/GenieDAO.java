package com.sist.dao;
// 오라클 연결 => 데이터는 VO에 저장 => 브라우저 전송 
// ROW => VO => 여러개 있는 경우 (ArrayList)
import java.util.*;// ArrayList (배열 => 가변형)
import java.sql.*; // Connection / Statement / ResultSet
/*
 *   Statement : 문장 (SQL문장을 전송할 때)
 *     = Statement => 입력값과 SQL 동시처리 "WHERE no="+no
 *                                      "WHERE name='"+name+"'"
 *     = PreparedStatement => SQL을 먼저 만들고 마지막에 입력값을 채운다 (default)
 *                                      "WHERE name=?"
 *                                      ?에 값을 채운다 
 *     = CallableStatement => Procedure 함수 호출시에 사용 (사용자 정의)
 *                           ============ 보안(ERP) 
 */
// Servlet  ==> JSP ==> MVC ==> SpringMVC
// ==> DAO,VO
public class GenieDAO {
         // 연결 객체 
	     private Connection conn;
	     // SQL 문장 전송 객체
		 private PreparedStatement ps;
		 // 오라클 주소 
		 private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
		 // 1. 드라이버 
		 public GenieDAO()
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
	 // 4. 기능 설정 (인라뷰를 이용해서 페이지 나누는 방법) => 웹 70% 페이지 나누기 
		 /*
		  * NO        NOT NULL NUMBER         
			 POSTER             VARCHAR2(1000) 
			 TITLE              VARCHAR2(200)  
			 SINGER             VARCHAR2(100)  
			 ALBUM              VARCHAR2(200)  
			 STATE              CHAR(10)       
			 IDCREMENT          NUMBER         
			 KEY                VARCHAR2(100)
		  */
		public ArrayList<GenieVO> genieListData(int page)
		{
			// 배열 => [20] => 마지막 페이지는 몇개인지 알 수 없다  => 198 => 18
			ArrayList<GenieVO> list=new ArrayList<GenieVO>(); // 가변형 
			try
			{
				// 1. 연결
				getConnection();
				// 2. SQL문장을 만든다 
				String sql="SELECT no,poster,title,singer,album,num "
						  +"FROM (SELECT no,poster,title,singer,album,rownum as num "
						  +"FROM (SELECT no,poster,title,singer,album "
						  +"FROM music_view ORDER BY no ASC)) "
						  +"WHERE num BETWEEN ? AND ?";
				// 오라클 => 인라인뷰 
				// 자바 => while => 전체 200개에서 20만 추출 => 20 15만개 
				/*
				 *   1page  => 1 ~ 20
				 *   2page  => 21 ~ 40
				 *   3page  => 41 ~ 60
				 */
				int rowSize=20;
				int start=(rowSize*page)-(rowSize-1);
				// 1        20*1 - (20-1) => 20-19 = 1
				// 2        20*2 (40-19)= 21
				// 오라클은 0이 아니라 => 1번
				int end=rowSize*page;
				// ?에 값을 채운다 
				ps=conn.prepareStatement(sql);
				ps.setInt(1, start);
				ps.setInt(2, end);
				// 페이지 나눈다 
				// 데이터 읽기
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
				    	GenieVO vo=new GenieVO();
				    	vo.setNo(rs.getInt(1));
				    	vo.setPoster(rs.getString(2));
				    	vo.setTitle(rs.getString(3));
				    	vo.setSinger(rs.getString(4));
				    	vo.setAlbum(rs.getString(5));
						/*
						 * vo.setState(rs.getString(6)); vo.setIdcrement(rs.getInt(7));
						 * vo.setKey(rs.getString(8));
						 */
				    	list.add(vo);
				}
				rs.close();
				
			}catch(Exception ex)
			{
				ex.printStackTrace(); // 에러 처리
			}
			finally
			{
				disConnection(); //오라클 닫기 
			}
			return list;
		}
		// 총페이지 구하기 (view => table+table) => table (가상) => 처리는 동일하다 
		// FROM table_name|view_name
		public int genieTotalPage()
		{
			int total=0;
			try
			{
				// 1. 연결
				getConnection();
				// 2. SQL문장
				String sql="SELECT CEIL(COUNT(*)/20.0) FROM music_view";
				// 3. 실행 
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				rs.next();
				// 4. 결과값 받기
				total=rs.getInt(1);
				rs.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				disConnection();
			}
			
			return total;
		}
	 // 사람 => 한눈에 볼 수 있는 (10~20) => 모바일 
	 // 기술면접 => 페이징기법 , 게시판 (제작 시간)
	 // 5. SQL문장을 단순화 => 복합뷰 (조인) => 지니뮤직(동영상(X),멜론(동영상)) 
		/*
		 *  CREATE OR REPLACE VIEW music_view
            AS
		    SELECT gc.no,gc.title,gc.singer,gc.album,mc.key,gc.idcrement,gc.state,gc.poster
		    FROM genie_cjw gc,melon_cjw mc
		    WHERE gc.title=mc.title;
		    
		    SELECT * FROM music_view
		 */
	public GenieVO genieDetailData(int no)
	{
		GenieVO vo=new GenieVO();
		try
		{
			// 1. 연결
			getConnection();
			// 2. SQL문장 
			// 
			   
			/*
			 *   String sql="SELECT gc.no,gc.title,gc.singer,gc.album,mc.key,"
			 *             +"gc.idcrement,gc.state,gc.poster "
                           +"FROM genie_cjw gc,melon_cjw mc "
                           +"WHERE gc.title=mc.title"
                  view => SELECT문장을 가지고 있다 
                  
                  SELECT no,title,singer,album,key
                  FROM (SELECT gc.no,gc.title,gc.singer,gc.album,mc.key,
                        gc.idcrement,gc.state,gc.poster
                        FROM genie_cjw gc,melon_cjw mc
                        WHERE gc.title=mc.title)
                  WHERE no=10     
			 */
			String sql="SELECT no,title,singer,album,key "
					  +"FROM music_view "
					  +"WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			
			vo.setNo(rs.getInt(1));
			vo.setTitle(rs.getString(2));
			vo.setSinger(rs.getString(3));
			vo.setAlbum(rs.getString(4));
			vo.setKey(rs.getString(5));
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
	 
	 
	 
}










