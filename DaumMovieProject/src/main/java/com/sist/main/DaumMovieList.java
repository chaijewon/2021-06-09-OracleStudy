package com.sist.main;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
@WebServlet("/DaumMovieList")
public class DaumMovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          // Servlet => ������ �����ϴ� �ڹ����� (�� ������ �ʿ��� ��� , �ҽ��� �������� �ʴ� ���)		
	      // JSP => ������ ����Ǵ� ���� (������ ���� , ��ü �ҽ��� ���� => View)
		  // Spring => ������ ���̺귯�� => ������ => (�ҽ��� �������� �ʴ´�)
		  /*
		   *    request : ����� ���� ������ ����� ������ �ִ� 
		   *              ?no=1 ==> request�� ��� ���´� (��Ĺ�� ���� ó��)
		   *    response : ���� (�����=> html,cookie)
		   *    session : ������ ������� �Ϻ����� ���� => id,name,....
		   *    cookie  : ����� ��ǻ�Ϳ� ���� => �ֱ� �湮 
		   */
		// 1. ���� ��Ÿ�� ( text/html , text/xml , text/plain )
		//                   html        xml        json
		response.setContentType("text/html;charset=UTF-8");
		// �޸� ���� => HTML�� ���� => ������ �о�� 
		PrintWriter out=response.getWriter(); // response���� ������ ������ ����� IP
		String strCno=request.getParameter("cno");
		if(strCno==null)
			strCno="1"; // Default ���� (1)
		MovieDAO dao=new MovieDAO();
		ArrayList<DaumMovieVO> list=dao.daumMovieListData(Integer.parseInt(strCno));
		// HTML�� ��� => �ش� ����ڰ� �о� ���� 
		out.println("<html>"); // ML <a> , </a> , <br/> => XML,WML , HDML ...
		// HTML�� �̹� �±װ� ������� �ִ� 
		// XML => ����� ���� (Spring,Mybatis...)
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>.row{margin:0px auto;width:1200px}</style>");
		// . => �±׸� (X) , class�� 
		// .container
		out.println("</head>");
		out.println("<body>");
		out.println("<div style=\"height:50px\"></div>");// ����
		out.println("<div class=container>"); // container/wrap
		out.println("<div class=row>");
		// ����ϴ� ������ ������ ��� ��� => margin:0px auto => center ����
		out.println("<a href=DaumMovieList?cno=1 class=\"btn btn-lg btn-danger\">��ȭ����</a>");
		out.println("<a href=DaumMovieList?cno=2 class=\"btn btn-lg btn-success\">�ڽ����ǽ�</a>");
		out.println("<a href=DaumMovieList?cno=5 class=\"btn btn-lg btn-info\">OTT</a>");
		out.println("<a href=DaumMovieList?cno=6 class=\"btn btn-lg btn-warning\">���ø���</a>");
		out.println("<a href=DaumMovieList?cno=7 class=\"btn btn-lg btn-primary\">����</a>");
		out.println("<a href=DaumMovieList?cno=8 class=\"btn btn-lg btn-default\">īī��������</a>");
		out.println("</div>");
		
		out.println("<div style=\"height:30px\"></div>");// ����
		out.println("<div class=row>");
		// ��ȭ ��� 
		for(DaumMovieVO vo:list)
		{
			out.println("<div class=col-sm-2>");// 3 3 3 3  => ����
			// 2 2 2 2 2 2 => ���� 4 4 4
			out.println("<img src="+vo.getPoster()+" width=100% title=\""+vo.getTitle()+"\">");
			out.println("</div>");
		}
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
	}

}









