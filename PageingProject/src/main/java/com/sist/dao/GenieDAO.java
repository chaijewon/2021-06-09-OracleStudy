package com.sist.dao;
// ����Ŭ ���� => �����ʹ� VO�� ���� => ������ ���� 
// ROW => VO => ������ �ִ� ��� (ArrayList)
import java.util.*;// ArrayList (�迭 => ������)
import java.sql.*; // Connection / Statement / ResultSet
/*
 *   Statement : ���� (SQL������ ������ ��)
 *     = Statement => �Է°��� SQL ����ó�� "WHERE no="+no
 *                                      "WHERE name='"+name+"'"
 *     = PreparedStatement => SQL�� ���� ����� �������� �Է°��� ä��� (default)
 *                                      "WHERE name=?"
 *                                      ?�� ���� ä��� 
 *     = CallableStatement => Procedure �Լ� ȣ��ÿ� ��� (����� ����)
 *                           ============ ����(ERP) 
 */
// Servlet  ==> JSP ==> MVC ==> SpringMVC
// ==> DAO,VO
public class GenieDAO {
         // ���� ��ü 
	     private Connection conn;
	     // SQL ���� ���� ��ü
		 private PreparedStatement ps;
		 // ����Ŭ �ּ� 
		 private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
		 // 1. ����̹� 
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
		 // 2. ����
		 public void getConnection()
		 {
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
	 // 4. ��� ���� (�ζ�並 �̿��ؼ� ������ ������ ���) => �� 70% ������ ������ 
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
			// �迭 => [20] => ������ �������� ����� �� �� ����  => 198 => 18
			ArrayList<GenieVO> list=new ArrayList<GenieVO>(); // ������ 
			try
			{
				// 1. ����
				getConnection();
				// 2. SQL������ ����� 
				String sql="SELECT no,poster,title,singer,album,num "
						  +"FROM (SELECT no,poster,title,singer,album,rownum as num "
						  +"FROM (SELECT no,poster,title,singer,album "
						  +"FROM music_view ORDER BY no ASC)) "
						  +"WHERE num BETWEEN ? AND ?";
				// ����Ŭ => �ζ��κ� 
				// �ڹ� => while => ��ü 200������ 20�� ���� => 20 15���� 
				/*
				 *   1page  => 1 ~ 20
				 *   2page  => 21 ~ 40
				 *   3page  => 41 ~ 60
				 */
				int rowSize=20;
				int start=(rowSize*page)-(rowSize-1);
				// 1        20*1 - (20-1) => 20-19 = 1
				// 2        20*2 (40-19)= 21
				// ����Ŭ�� 0�� �ƴ϶� => 1��
				int end=rowSize*page;
				// ?�� ���� ä��� 
				ps=conn.prepareStatement(sql);
				ps.setInt(1, start);
				ps.setInt(2, end);
				// ������ ������ 
				// ������ �б�
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
				ex.printStackTrace(); // ���� ó��
			}
			finally
			{
				disConnection(); //����Ŭ �ݱ� 
			}
			return list;
		}
		// �������� ���ϱ� (view => table+table) => table (����) => ó���� �����ϴ� 
		// FROM table_name|view_name
		public int genieTotalPage()
		{
			int total=0;
			try
			{
				// 1. ����
				getConnection();
				// 2. SQL����
				String sql="SELECT CEIL(COUNT(*)/20.0) FROM music_view";
				// 3. ���� 
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				rs.next();
				// 4. ����� �ޱ�
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
	 // ��� => �Ѵ��� �� �� �ִ� (10~20) => ����� 
	 // ������� => ����¡��� , �Խ��� (���� �ð�)
	 // 5. SQL������ �ܼ�ȭ => ���պ� (����) => ���Ϲ���(������(X),���(������)) 
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
			// 1. ����
			getConnection();
			// 2. SQL���� 
			// 
			   
			/*
			 *   String sql="SELECT gc.no,gc.title,gc.singer,gc.album,mc.key,"
			 *             +"gc.idcrement,gc.state,gc.poster "
                           +"FROM genie_cjw gc,melon_cjw mc "
                           +"WHERE gc.title=mc.title"
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










