package com.sist.view;
import java.io.FileReader;
import java.util.*;
import com.sist.dao.*; // ��Ű���� �ٸ� ��� �ݵ�� import�� ����ؼ� Ŭ������ �о� �´� 
public class MainClass1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        EmpDAO dao=new EmpDAO();
		/*
		 * ArrayList<Emp> list=dao.empListData(); for(Emp e:list) {
		 * System.out.println(e.getEmpno()+" " +e.getEname()+" " +e.getJob()+" "
		 * +e.getHiredate().toString()); }
		 */
        /*Scanner scan=new Scanner(System.in);
        System.out.print("��� �Է�:");
        int empno=scan.nextInt();
        Emp emp=dao.empDetailData(empno);
        System.out.println("===== ��� =====");
        System.out.println("���:"+emp.getEmpno());
        System.out.println("�̸�:"+emp.getEname());
        System.out.println("����:"+emp.getJob());
        System.out.println("���:"+emp.getMgr());
        System.out.println("�Ի���:"+emp.getHiredate().toString());
        System.out.println("�޿�:"+emp.getSal());
        System.out.println("������:"+emp.getComm());
        System.out.println("�μ���ȣ:"+emp.getDeptno());*/
        try
        {
        	StringBuffer data=new StringBuffer();
        	FileReader fr=new FileReader("c:\\oracleDev\\movie.txt");
        	int i=0;
        	while((i=fr.read())!=-1)
        	{
        		//data+=String.valueOf((char)i);
        		data.append(String.valueOf((char)i));
        	}
        	System.out.println(data.toString());
        	fr.close();
        	
        	String movie_data=data.toString();
        	String[] md=movie_data.split("\n");
        	//genre,poster,actor,regdate,grade,director;
        	int k=1;
        	for(String s:md)
        	{
        		try 
        		{
	        		StringTokenizer st=new StringTokenizer(s,"|");
	        		Movie m=new Movie();
	        		m.setMno(Integer.parseInt(st.nextToken()));
	        		m.setTitle(st.nextToken());
	        		m.setGenre(st.nextToken());
	        		m.setPoster(st.nextToken());
	        		m.setActor(st.nextToken());
	        		m.setRegdate(st.nextToken());
	        		m.setGrade(st.nextToken());
	        		m.setDirector(st.nextToken());
	        		dao.movieInsert(m);
	        		System.out.println("k="+k);
	        		k++;
        		}catch(Exception ex) {}
        	}
        	System.out.println("���� �Ϸ�!!");
        }catch(Exception ex) {}
        
	}

}







