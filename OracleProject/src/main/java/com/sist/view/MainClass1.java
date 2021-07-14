package com.sist.view;
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
        Scanner scan=new Scanner(System.in);
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
        System.out.println("부서번호:"+emp.getDeptno());
        
	}

}







