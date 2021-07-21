package com.sist.data;

import java.io.*;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FoodList")
public class FoodList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 전송방식
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		// 3. 메모리 공간에 HTML을 출력한다 
		out.println("<html>");
		out.println("<head>");
		// 선언 => CSS / JavaScript
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style type=text/css>.row{margin:0px auto;width:1200px}</style>"); // 화면 가운데 정렬  
		out.println("</head>");
		out.println("<body>");
		String cno=request.getParameter("cno");
		FoodDAO dao=new FoodDAO();
		FoodCategoryVO vo=dao.foodCategoryInfoData(Integer.parseInt(cno));
		ArrayList<FoodHouseVO> list=dao.foodList(Integer.parseInt(cno));
		out.println("<div class=container>");
		out.println("<div class=jumbotron>");
		out.println("<h1 class=text-center>"+vo.getTitle()+"</h1>");
		out.println("<h3 class=text-center>"+vo.getSubject()+"</h3>");
		out.println("</div>");
		out.println("<div class=row>");
		for(FoodHouseVO fvo:list)
		{
			out.println("<table class=table>");
			out.println("<tr>");
			out.println("<td width=30% class=text-center rowspan=3>");
			out.println("<img src="+fvo.getPoster()+" width=300 height=150 class=img-rounded>");
			out.println("</td>");
			out.println("<td width=70%>");
			out.println("<h3>"+fvo.getName()+" <span style=\"color:orange\">"+fvo.getScore()+"</span></h3>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr height=40>");
			out.println("<td width=70%>");
			out.println(fvo.getAddress());
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr height=40>");
			out.println("<td width=70%>");
			out.println(fvo.getTel());
			out.println("</td>");
			out.println("</tr>");
		}
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
		
		
	}

}
