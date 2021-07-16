package com.sist.user;
// 8.5 => javax  10.2 => jakarta
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
@WebServlet("/MusicList")
public class MusicList extends HttpServlet {
	private static final long serialVersionUID = 1L;
    // 브라우저로 전송하는 방식 (GET/POST) => doGet(),doPost()
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 서비스 메소드 => 클라이언트가 요청시 실행 => 자바번역 (HTML)을 만들어서 브라우저로 전송
		// 1. 전송하는 데이터 선택 => HTML/XML => 화면 출력 (HTML) , 문서(XML)
		response.setContentType("text/html;charset=UTF-8");
		// 1-1 response(응답=> 브라우저로 전송), request(클라이언트가 보낸준 값을 받을때 사용)
		// 2. 브라우저가 읽어갈 수 있는 메모리 제작 
		PrintWriter out=response.getWriter();
		// 3. out메모리에 HTML를 출력하면 => 브라우저에 읽어서 출력 
		out.println("<html>"); 
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>row{margin:0px auto}</style>"); // 화면 가운데 정렬 
		out.println("</head>"); // CSS,JavaScript등록 => 출력하는 영역이 아니다 
		out.println("<body>");
		out.println("<div class=container>"); // full 화면
		out.println("<div class=row>");
		out.println("<h1 class=text-center>지니 Top 100</h1>");
		MusicDAO dao=new MusicDAO();
		ArrayList<GenieVO> list=dao.genieAllData();
		out.println("<table class=table>");
		out.println("<tr class=danger>");
		out.println("<th class=text-center>번호</th>");
		out.println("<th class=text-center></th>");
		out.println("<th class=text-center></th>");
		out.println("<th class=text-center>곡명</th>");
		out.println("<th class=text-center>가수명</th>");
		out.println("<th class=text-center>앨범</th>");
		out.println("</tr>");
		// 데이터 출력 
		for(GenieVO vo:list)
		{
			out.println("<tr>");
			out.println("<td class=text-center>"+vo.getNo()+"</td>");
			String s="";
			if(vo.getState().trim().equals("유지"))
			{
				s="<font color=gray>-</font>";
			}
			else if(vo.getState().trim().equals("상승"))
			{
				s="<font color=blue>▲</font> "+vo.getIdcerment();
			}
			else if(vo.getState().trim().equals("하강"))
			{
				s="<font color=red>▼</font> "+vo.getIdcerment();
			}
			out.println("<td class=text-center>"+s+"</td>");
			out.println("<td class=text-center><img src="+vo.getPoster()+" width=30 height=30></td>");
			out.println("<td>"+vo.getTitle()+"</td>");
			out.println("<td>"+vo.getSinger()+"</td>");
			out.println("<td>"+vo.getAlbum()+"</td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>"); // 화면에 출력하는 내용
		out.println("</html>");
	}

}









