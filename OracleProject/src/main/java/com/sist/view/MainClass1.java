package com.sist.view;
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
        Scanner scan=new Scanner(System.in);
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
        System.out.println("�μ���ȣ:"+emp.getDeptno());
        
	}

}







