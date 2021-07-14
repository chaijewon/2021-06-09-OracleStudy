package com.sist.dao;
import java.util.*;
/*
 *    오라클          자바
 *    CHAR
 *    VARCHAR2
 *    CLOB        => String
 *    NUMBER(4)   => int
 *    NUMBER*7,2) => double
 *    DATE        => java.util.Date
 *    ==============================
 *    DESC emp => DB(데이터형 확인) => 컬럼명확인 
 *    
 *      EMPNO    NOT NULL NUMBER(4)    
		ENAME             VARCHAR2(10) 
		JOB               VARCHAR2(9)  
		MGR               NUMBER(4)    
		HIREDATE          DATE         
		SAL               NUMBER(7,2)  
		COMM              NUMBER(7,2)  
		DEPTNO            NUMBER(2) 
 */
public class Emp {
    private int empno; // NUMBER(4)
    private String ename; // VARCHAR2
    private String job; //  VARCHAR2
    private int mgr; // NUMBER
    private Date hiredate; // DATE
    private int sal; // NUMBER
    private int comm; // NUMBER
    private int deptno;// NUMBER
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getMgr() {
		return mgr;
	}
	public void setMgr(int mgr) {
		this.mgr = mgr;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public int getSal() {
		return sal;
	}
	public void setSal(int sal) {
		this.sal = sal;
	}
	public int getComm() {
		return comm;
	}
	public void setComm(int comm) {
		this.comm = comm;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	   
}
