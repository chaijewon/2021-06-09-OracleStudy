package com.sist.dao;
// ����Ŭ ���� => CURD�� �����ϰ� ����� 
import java.sql.*; 
import java.util.*;
/*
 *   ���� : ���(is-s) , ����(has-a) => ������ ����� ������� ������Ҷ�
 *           ========   ===========
 *           => ������ ����� �����ؼ� ����� ���  
 */
public class MovieDAO {
    private Connection conn; //����Ŭ ���� 
    private PreparedStatement ps; // SQL���� ����Ŭ�� ���� 
    private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
    // ����̹��� ���ؼ� ���� => ����̹� ����Ѵ� = Ŭ���� Class.forName("����̹� Ŭ����")
    public MovieDAO() // ���۰� ���ÿ� ����̹� ��� 
    {
    	// �ʱ�ȭ (����̹� �ʱ�ȭ) => ������� => �����б�,�� �б� (�ʱ�ȭ ��� �޼ҵ�)
    	try
    	{
    		Class.forName("oracle.jdbc.driver.OracleDriver"); // ��Ű����.Ŭ������ => XML(��Ű����.Ŭ����)
    		// Ŭ������ ���� ��� => ClassNotFoundException (������ ����ó��) 
    		// =�ݵ�� ����ó���� �Ѵ� 
    		// ��� => Ŭ���� �޸� �Ҵ� => ���÷��� (�ٽ�:������) => ������(Ŭ����������)
    	}catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
    // ����Ŭ ���� ==> conn hr/happy => �ڹ� conn=DriverManager.getConnection(URL,"","")
    public void getConnection()
    {
    	try
    	{
    		conn=DriverManager.getConnection(URL,"hr","happy");
    		// getConnection() ==> conn hr/happy
    		// TCP (�ŷڼ�) => ���������� �������� , UDP 
    		// ��ȭ                            ���� 
    	}catch(Exception ex){}
    }
    // ����Ŭ�� �ݱ� => �����ڴ� conn�� �Ѱ��� ����� ���� (XE�� ������ => 12~15)
    public void disConnection()
    {
    	try
    	{
    		// exit => ps.close(),conn.close() 
    		if(ps!=null) ps.close();
    		if(conn!=null) conn.close(); // ����Ǿ��ٸ� ����
    	}catch(Exception ex) {}
    }
    // ��� ���� 
    // 1. ������ ÷�� 
    /*
     *  MNO      NOT NULL NUMBER        
		CNO               NUMBER        
		TITLE    NOT NULL VARCHAR2(300) 
		REGDATE           VARCHAR2(100) 
		GENRE    NOT NULL VARCHAR2(200) 
		NATION            VARCHAR2(100) 
		GRADE    NOT NULL VARCHAR2(50)  
		TIME     NOT NULL VARCHAR2(30)  
		SCORE             NUMBER(2,1)   
		SHOWUSER          VARCHAR2(100) 
		POSTER   NOT NULL VARCHAR2(260) 
     */
    // �ΰ��� => ���� ó�� , ����Ʈ �б� 
    public void daumMovieInsert(DaumMovieVO vo)
    {
    	try
    	{
    		// 1. ����
    		getConnection();
    		// 2. SQL���� 
    		String sql="INSERT INTO daum_movie VALUES("
    				  +"daum_mno_seq.nextval,?,?,?,?,?,?,?,?,?,?,?)";
    		// Sequence�� ���� 
    		// daum_mno_seq.nextval => SELECT MAX(mno)+1 FROM daum_movie
    		/*
    		 *   1
    		 *   2
    		 *   3
    		 *   4
    		 *   
    		 *   6 
    		 *   7 
    		 *   8  ==> ���� �ߺ��� ���ڰ� ���� 
    		 */
    		// 3. ����
    		ps=conn.prepareStatement(sql);
    		// 4. ���� ä�� �� ���� ��û
    		ps.setInt(1, vo.getCno());
    		ps.setString(2, vo.getTitle());
    		ps.setString(3, vo.getRegdate());
    		ps.setString(4, vo.getGenre());
    		ps.setString(5, vo.getNation());
    		ps.setString(6, vo.getGrade());
    		ps.setString(7, vo.getTime());
    		ps.setDouble(8, vo.getScore());
    		ps.setString(9, vo.getShowUser());
    		ps.setString(10, vo.getPoster());
    		ps.setString(11, vo.getStory());
    		
    		ps.executeUpdate(); // commit()�� ������ �Ǿ� �ִ� 
    	}catch(Exception ex)
    	{
    		ex.printStackTrace();// ���� ó��
    	}
    	finally
    	{
    		disConnection(); // ���� ���� 
    	}
    }
    // ������ �б� 
    public ArrayList<DaumMovieVO> daumMovieListData(int cno)
    {
    	ArrayList<DaumMovieVO> list=new ArrayList<DaumMovieVO>();
    	try
    	{
    		// 1. ����
    		getConnection();
    		// 2. SQL���� 
    		String sql="SELECT /*+ INDEX_ASC(daum_movie dm_mno_pk) */ mno,poster,title FROM daum_movie "
    				  +"WHERE cno="+cno;
    		// 3. SQL���� ����
    		ps=conn.prepareStatement(sql);
    		// 4. SQL���� ������ ����� �ޱ�
    		ResultSet rs=ps.executeQuery();
    		// 5. ����� �޸𸮺��� �����͸� �о ArrayList�� ä��� 
    		while(rs.next())
    		{
    			DaumMovieVO vo=new DaumMovieVO();
    			vo.setMno(rs.getInt(1));
    			vo.setPoster(rs.getString(2));
    			vo.setTitle(rs.getString(3));
    			list.add(vo);
    		}
    		// 6. �޸� �ݴ´� rs.close()
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
    public void daumMovieScoreAvg()
    {
    	try
    	{
    		getConnection();
    		String sql="SELECT cno,ROUND(AVG(score),2) FROM dm "
    				  +"GROUP BY cno "
    				  +"ORDER BY cno";
    		ps=conn.prepareStatement(sql);
    		ResultSet rs=ps.executeQuery();
    		while(rs.next())
    		{
    			System.out.println(rs.getInt(1)+","+rs.getDouble(2));
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
    }
    // �󼼺��� 
    public DaumMovieVO daumMovieDetailData(int mno)
    {
    	// mno => Primary Key (�ߺ����� ������)
    	DaumMovieVO vo=new DaumMovieVO();
    	try
    	{
    		//1. ���� getConnection()
    		getConnection();
    		//2. SQL���� String sql=""
    		String sql="SELECT mno,title,poster,genre,grade,nation,showUser,"
    				  +"score,story,time,regdate "
    				  +"FROM dm "
    				  +"WHERE mno=?";
    		//3. SQL���� ps=conn.preparedStatement(sql)
    		ps=conn.prepareStatement(sql);
    		//4. �ʿ��� �����Ͱ� �ִ� ��� (?) ���� ä��� ps.setInt(1,1)
    		ps.setInt(1, mno);
    		//5. ������ ������� �޴´�  ResultSet rs=ps.executeQuery()
    		ResultSet rs=ps.executeQuery();
    		//6. VO�� ���� ä���  rs.next() vo.setMno(...)
    		rs.next();
    		vo.setMno(rs.getInt(1));
    		vo.setTitle(rs.getString(2));
    		vo.setPoster(rs.getString(3));
    		vo.setGenre(rs.getString(4));
    		vo.setGrade(rs.getString(5));
    		vo.setNation(rs.getString(6));
    		vo.setShowUser(rs.getString(7));
    		vo.setScore(rs.getDouble(8));
    		vo.setStory(rs.getString(9));
    		vo.setTime(rs.getString(10));
    		vo.setRegdate(rs.getString(11));
    		//7. �޸� �ݱ�  rs.close()
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
    public static void main(String[] args) {
		MovieDAO dao=new MovieDAO();
		//dao.daumMovieScoreAvg();
		Scanner scan=new Scanner(System.in);
		System.out.println("��ȭ����(1)");
		System.out.println("�ڽ����ǽ�-�ְ�(2)");
		System.out.println("�ڽ����ǽ�-����(3)");
		System.out.println("�ڽ����ǽ�-�Ⱓ(4)");
		System.out.println("OTT(5)");
		System.out.println("���÷���(6)");
		System.out.println("����(7)");
		System.out.println("īī��������(8)");
		System.out.println("================");
		System.out.print("�޴� ����:");
		int menu=scan.nextInt();
		ArrayList<DaumMovieVO> list=dao.daumMovieListData(menu);
		for(DaumMovieVO vo:list)
		{
			System.out.println(vo.getTitle());
		}
		System.out.println("�󼼺� ��ȭ ����(1~183):");
		int mno=scan.nextInt();
		DaumMovieVO vo=dao.daumMovieDetailData(mno);
		System.out.println(vo.getMno());
		System.out.println(vo.getTitle());
		System.out.println(vo.getGenre());
		System.out.println(vo.getGrade());
		System.out.println(vo.getNation());
		System.out.println(vo.getTime());
		System.out.println(vo.getShowUser());
		System.out.println(vo.getScore());
		System.out.println(vo.getRegdate());
				
	}
    // ã�� => LIKE  => ��� ��� , �󼼺��� , ã�� 
    public ArrayList<DaumMovieVO> daumMovieFindData(String fs,String ss)
    {
    	// title , genre , grade
    	ArrayList<DaumMovieVO> list=new ArrayList<DaumMovieVO>();
    	try
    	{
    		getConnection();
    		String sql="SELECT mno,poster,title "
    				  +"FROM dm "
    				  +"WHERE "+fs+" LIKE '%'||?||'%'";
    		/*
    		 *        "WHERE ? LIKE '%'||?||'%'"
    		 *        
    		 *        ps.setString(1,"title") => WHERE 'title' LIKE '%'��'%'
    		 *        ps.setString(2,"��")
    		 */
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
}








