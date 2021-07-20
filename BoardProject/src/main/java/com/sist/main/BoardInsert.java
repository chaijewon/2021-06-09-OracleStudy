package com.sist.main;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.sist.dao.*;// DB연동 
@WebServlet("/BoardInsert")
public class BoardInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// doPost() 호출 => DAO연결 
		response.setContentType("text/html;charset=UTF-8");
		// 2. HTML을 전송 할 준비 => 요청한 사람에게 전송 
		PrintWriter out=response.getWriter(); //out이라고 메모리 공간에 HTML출력 => 브라우저
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>row{margin:0px auto;width:500px}</style>"); // 화면 가운데 정렬 
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("<h1 class=text-center>글쓰기</h1>");
		out.println("<div style=\"height:30px\"></div>");
		
		out.println("<form method=post action=BoardInsert>");
		// BoardInsert가 가지고 있는 doPost를 호출한다 
		out.println("<table class=table>");
		
		out.println("<tr>");
		out.println("<th width=15% class=success>이름</th>");
		out.println("<td width=85%><input type=text name=name size=12 class=input-sm></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=15% class=success>제목</th>");
		out.println("<td width=85%><input type=text name=subject size=50 class=input-sm></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=15% class=success>내용</th>");
		out.println("<td width=85%><textarea rows=10 cols=55 name=content></textarea></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=15% class=success>비밀번호</th>");
		out.println("<td width=85%><input type=password name=pwd size=10 class=input-sm></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan=2 class=text-center>");
		out.println("<input type=submit value=글쓰기 class=\"btn btn-sm btn-primary\">");
		out.println("<input type=button onclick=\"javascript:history.back()\" value=취소 class=\"btn btn-sm btn-primary\">");
		out.println("</td>");
	    out.println("</tr>");
		
		out.println("</table>");
		out.println("</form>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// BoardList이동
		try
		{
			// 한글 깨진다 => 인코딩 => 디코딩 (데이터가 담겨있는 곳) request
			request.setCharacterEncoding("UTF-8"); // JSP , Spring , Kotlin
		}catch(Exception ex) {}
		BoardVO vo=new BoardVO();
		vo.setName(request.getParameter("name"));
		vo.setSubject(request.getParameter("subject"));
		vo.setContent(request.getParameter("content"));
		vo.setPwd(request.getParameter("pwd"));
		// DAO로 전송 => 데이터 추가 
		BoardDAO dao=new BoardDAO();
		dao.boardInsert(vo);
		// 화면 이동 => BoardList
		response.sendRedirect("BoardList");
	}

}







