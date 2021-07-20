package com.sist.main;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.sist.dao.*;
@WebServlet("/BoardDetail")
public class BoardDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request => 사용자 요청을 받는 경우에 사용 
				// response => 사용자 요청 처리후에 응답(브라우저로 HTML,Cookie전송)
				// 1. 전송하는 형식 => 브라우저 (HTML/XML)
				response.setContentType("text/html;charset=UTF-8");
				// 2. HTML을 전송 할 준비 => 요청한 사람에게 전송 
				PrintWriter out=response.getWriter(); //out이라고 메모리 공간에 HTML출력 => 브라우저
				/*
				 *     <html>
						<head>
						</head>
						<body>
						<center><h1>자유게시판</h1></center>
						</body>
						</html>
				 */
				// 1. 데이터 받기 BoardDetail?no=1
				String no=request.getParameter("no");
				BoardDAO dao=new BoardDAO();
				BoardVO vo=dao.boardDetail(Integer.parseInt(no));
				// 상세보기 데이터를 오라클로 부터 읽어 온다 
;				out.println("<html>");
				out.println("<head>");
				out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
				out.println("<style>row{margin:0px auto;width:500px}</style>"); // 화면 가운데 정렬 
				out.println("</head>");
				out.println("<body>");
				out.println("<div class=container>");
				out.println("<div class=row>");
				out.println("<h1 class=text-center>내용보기</h1>");
				out.println("<table class=table>");
				out.println("<tr>");
				out.println("<th width=20% class=text-center>번호</th>");
				out.println("<td width=30% class=text-center>"+vo.getNo()+"</td>");
				out.println("<th width=20% class=text-center>작성일</th>");
				out.println("<td width=30% class=text-center>"+vo.getRegdate().toString()+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width=20% class=text-center>이름</th>");
				out.println("<td width=30% class=text-center>"+vo.getName()+"</td>");
				out.println("<th width=20% class=text-center>조회수</th>");
				out.println("<td width=30% class=text-center>"+vo.getHit()+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width=20% class=text-center>제목</th>");
				out.println("<td width=30% colspan=3>"+vo.getSubject()+"</td>");
				out.println("</tr>");
				
				out.println("<tr>");
				out.println("<td height=200 valign=top colspan=4>"+vo.getContent()+"</td>");
				out.println("</tr>");
				
				out.println("<tr>");
				out.println("<td colspan=4 class=text-right>");
				out.println("<a href=# class=\"btn btn-xs btn-success\">수정</a>");
				out.println("<a href=# class=\"btn btn-xs btn-danger\">삭제</a>");
				out.println("<a href=BoardList class=\"btn btn-xs btn-warning\">목록</a>");
				out.println("</td>");
				out.println("</tr>");
				
				out.println("</table>");
				out.println("</div>");
				out.println("</div>");
				out.println("</body>");
				out.println("</html>");
				
				
	}

}
