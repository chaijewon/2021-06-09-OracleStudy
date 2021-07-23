package com.sist.main;

// apache���� ���� ���̺귯�� => jakarta.~ ��Ĺ 10����
// 10���� ���� => javax => 8.5 , 9.0 
import java.io.*;
import java.util.ArrayList;

import com.sist.dao.DaumMovieVO;
import com.sist.dao.MovieDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DaumMovieFind")
public class DaumMovieFind extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Servlet => ������ �����ϴ� �ڹ����� (�� ������ �ʿ��� ��� , �ҽ��� �������� �ʴ� ���)		
	      // JSP => ������ ����Ǵ� ���� (������ ���� , ��ü �ҽ��� ���� => View)
		  // Spring => ������ ���̺귯�� => ���� => (�ҽ��� �������� �ʴ´�)
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
		
		String fs=request.getParameter("fs"); // title,genre
		String ss=request.getParameter("ss");
		System.out.println("ss="+ss);
		MovieDAO dao=new MovieDAO();
		ArrayList<DaumMovieVO> list=dao.daumMovieFindData(fs, ss);
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
		out.println("</div>");
		out.println("<form action=DaumMovieFind>");
		out.println("<select name=fs class=input-sm>");
		out.println("<option value=title>��ȭ��</option>");
		out.println("<option value=genre>�帣</option>");
		out.println("</select>");
		out.println("<input type=text name=ss class=input-sm size=15>");
		out.println("<input type=submit value=�˻� class=\"btn btn-danger btn-sm\">");
		out.println("</form>"); // ������ ���� 
		out.println("<div style=\"height:30px\"></div>");// ����
		out.println("<div class=row>");
		// ��ȭ ��� 
		if(list!=null)
		{
			for(DaumMovieVO vo:list)
			{
				out.println("<div class=col-sm-2>");// 3 3 3 3  => ����
				// 2 2 2 2 2 2 => ���� 4 4 4
				out.println("<img src="+vo.getPoster()+" width=100% title=\""+vo.getTitle()+"\">");
				out.println("</div>");
			}
		}
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
	}

}
