package com.sist.data;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FoodDetail")
public class FoodDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HTML 출력
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		// 데이터 받기
		//out.println("<a href=FoodDetail?no="+fvo.getNo()+">");
		// ?no=100
		String no=request.getParameter("no");
		FoodDAO dao=new FoodDAO();
		FoodHouseVO vo=dao.foodDetail(Integer.parseInt(no));
		//vo에 있는 데이터 출력 
		out.println("<html>");
		out.println("<head>");
		// 선언 => CSS / JavaScript
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style type=text/css>.row{margin:0px auto;width:1200px}</style>"); // 화면 가운데 정렬  
		out.println("</head>");
		out.println("<body>");
		out.println("<div style=\"height:30px\"></div>");
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("<table class=table>");
		out.println("<tr>");
		StringTokenizer st=new StringTokenizer(vo.getPoster(),"^");
		while(st.hasMoreTokens())
		{
			out.println("<td>");
			out.println("<img src="+st.nextToken()+" width=100%>");
			out.println("</td>");
		}
		out.println("</tr>");
		out.println("</table>");
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td colspan=2><h3>"+vo.getName()+" <span style=\"color:orange\">"+vo.getScore()+"</span></h3>");
		out.println("</tr>");
		// 주소
		out.println("<tr>");
		out.println("<th width=20% class=text-right>주소</th>");
		out.println("<td width=80%>"+vo.getAddress()+"</td>");
		out.println("</tr>");
		// 전화
		out.println("<tr>");
		out.println("<th width=20% class=text-right>전화</th>");
		out.println("<td width=80%>"+vo.getTel()+"</td>");
		out.println("</tr>");
		// 음식종류
		out.println("<tr>");
		out.println("<th width=20% class=text-right>음식종류</th>");
		out.println("<td width=80%>"+vo.getType()+"</td>");
		out.println("</tr>");
		// 가격대
		out.println("<tr>");
		out.println("<th width=20% class=text-right>가격대</th>");
		out.println("<td width=80%>"+vo.getPrice()+"</td>");
		out.println("</tr>");
		// 주차
		out.println("<tr>");
		out.println("<th width=20% class=text-right>주차</th>");
		out.println("<td width=80%>"+vo.getParking()+"</td>");
		out.println("</tr>");
		// 영업시간
		out.println("<tr>");
		out.println("<th width=20% class=text-right>영업시간</th>");
		out.println("<td width=80%>"+vo.getTime()+"</td>");
		out.println("</tr>");
		// 메뉴
		out.println("<tr>");
		out.println("<th width=20% class=text-right>메뉴</th>");
		out.println("<td width=80%>");
		if(!vo.getMenu().equals("no"))
		{
			out.println("<ul>");
			st=new StringTokenizer(vo.getMenu(),"원");
			while(st.hasMoreTokens())
			{
				out.println("<li>"+st.nextToken()+"원</li>");
			}
			out.println("</ul>");
			out.println("</td>");
		}
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
	}

}







