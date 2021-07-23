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
    		String sql="INSERT INTO duam_movie VALUES("
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
}








