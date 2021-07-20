package com.sist.main;
// tomcat=> 9버젼 => javax.
// tomcat=> 10버젼 => jakarta
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/*
 *   Servlet  => server+let : 서버에서 실행되는 가벼운 프로그램 (자바=>보안) => 스프링
 *               자바 => 수정시마다 컴파일을 해야 한다 => JSP
 *              // Applet (Application+let) , MIDlet (모바일에서 실행되는 가벼운 프로그램)
 *                                             Kotlin
 *   ==>  Servlet 동작 
 *        메모리 할당 ===> 톰캣(사용자가 URL을 통해서 요청)
 *        ========================================== 자동 호출 (callback)
 *        init() : 환경설정 (생성자 대신)
 *        doGet() / doPost() => 사용자 서비스 (HTML)
 *        사용자 요청하는 방식 
 *         GET : URL뒤에 ?변수=값 (요청값을 전송) => doGet() => 페이지요청,단순한 요청
 *         POST: 보안 (내부네트워크를 통해서 전송) : 로그인,회원가입, 글쓰기 => doPost()
 *        destory() : 메모리 해제 
 *        
 *        Default => 화면 출력 (get)
 *                   데이터를 받아서 출력 (post)
 *                   
 *        중요사항 
 *          => 자바 
 *              클래스 <====> 클래스 
 *                   메소드 (매개변수로 데이터 전송)
 *          => 웹은 데이터를 전송할때 URL을 이용한다 ==> ?=>데이터 전송 
 *             /BoardList?page=1 => 매개변수 전송 
 */
import java.util.*;
import com.sist.dao.*;
/*
 *   <html> : 여는 태그
 *     <head>
 *        => 선언 => CSS/JavaScript => 화면에 출력하는 부분이 아니다 
 *                       react/vue/ajax => 
 *     </head>
 *     <body>
 *        => 화면에 출력하는 부분 
 *     </body>
 *   </html>: 닫는 태그
 */
@WebServlet("/BoardList")
public class BoardList extends HttpServlet {
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
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>row{margin:0px auto;width:700px}</style>"); // 화면 가운데 정렬 
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("<h1 class=text-center>자유게시판</h1>");
		out.println("<table class=table>");
		out.println("<tr class=danger>");// info , danger , warning , 
		out.println("<th width=10%>번호</th>");
		out.println("<th width=45%>제목</th>");
		out.println("<th width=15%>이름</th>");
		out.println("<th width=20%>작성일</th>");
		out.println("<th width=10%>조회수</th>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
	}

}







