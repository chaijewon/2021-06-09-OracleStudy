package com.sist.view;
import java.io.FileReader;
import java.util.*;
import com.sist.dao.*; // 패키지가 다른 경우 반드시 import를 사용해서 클래스를 읽어 온다 
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
        System.out.print("사번 입력:");
        int empno=scan.nextInt();
        Emp emp=dao.empDetailData(empno);
        System.out.println("===== 결과 =====");
        System.out.println("사번:"+emp.getEmpno());
        System.out.println("이름:"+emp.getEname());
        System.out.println("직위:"+emp.getJob());
        System.out.println("사수:"+emp.getMgr());
        System.out.println("입사일:"+emp.getHiredate().toString());
        System.out.println("급여:"+emp.getSal());
        System.out.println("성과급:"+emp.getComm());
        System.out.println("부서번호:"+emp.getDeptno());*/
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
        	System.out.println("저장 완료!!");
        }catch(Exception ex) {}
        
	}

}







