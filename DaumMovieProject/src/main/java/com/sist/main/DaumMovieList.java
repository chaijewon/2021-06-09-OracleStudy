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
          // Servlet => 웹에서 실행하는 자바파일 (웹 보안이 필요한 경우 , 소스를 공개하지 않는 경우)		
	      // JSP => 웹에서 실행되는 파일 (보안이 없다 , 전체 소스가 공개 => View)
		  // Spring => 웹관련 라이브러리 => 서블릿 => (소스를 공개하지 않는다)
		  /*
		   *    request : 사용자 전송 데이터 목록을 가지고 있다 
		   *              ?no=1 ==> request에 묶어서 들어온다 (톰캣에 의해 처리)
		   *    response : 응답 (사용자=> html,cookie)
		   *    session : 서버에 사용자의 일부정보 저장 => id,name,....
		   *    cookie  : 사용자 컴퓨터에 저장 => 최근 방문 
		   */
		// 1. 응답 스타일 ( text/html , text/xml , text/plain )
		//                   html        xml        json
		response.setContentType("text/html;charset=UTF-8");
		// 메모리 저장 => HTML을 저장 => 브라우저 읽어간다 
		PrintWriter out=response.getWriter(); // response에는 서버에 접속한 사용자 IP
		String strCno=request.getParameter("cno");
		if(strCno==null)
			strCno="1"; // Default 설정 (1)
		MovieDAO dao=new MovieDAO();
		ArrayList<DaumMovieVO> list=dao.daumMovieListData(Integer.parseInt(strCno));
		// HTML을 출력 => 해당 사용자가 읽어 간다 
		out.println("<html>"); // ML <a> , </a> , <br/> => XML,WML , HDML ...
		// HTML은 이미 태그가 만들어져 있다 
		// XML => 사용자 정의 (Spring,Mybatis...)
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>.row{margin:0px auto;width:1200px}</style>");
		// . => 태그명 (X) , class명 
		// .container
		out.println("</head>");
		out.println("<body>");
		out.println("<div style=\"height:50px\"></div>");// 간격
		out.println("<div class=container>"); // container/wrap
		out.println("<div class=row>");
		// 출력하는 내용은 브라우저 가운데 출력 => margin:0px auto => center 정렬
		out.println("<a href=DaumMovieList?cno=1 class=\"btn btn-lg btn-danger\">영화순위</a>");
		out.println("<a href=DaumMovieList?cno=2 class=\"btn btn-lg btn-success\">박스오피스</a>");
		out.println("<a href=DaumMovieList?cno=5 class=\"btn btn-lg btn-info\">OTT</a>");
		out.println("<a href=DaumMovieList?cno=6 class=\"btn btn-lg btn-warning\">넷플릭스</a>");
		out.println("<a href=DaumMovieList?cno=7 class=\"btn btn-lg btn-primary\">왓차</a>");
		out.println("<a href=DaumMovieList?cno=8 class=\"btn btn-lg btn-default\">카카오페이지</a>");
		out.println("</div>");
		
		out.println("<div style=\"height:30px\"></div>");// 간격
		out.println("<div class=row>");
		// 영화 목록 
		for(DaumMovieVO vo:list)
		{
			out.println("<div class=col-sm-2>");// 3 3 3 3  => 다음
			// 2 2 2 2 2 2 => 다음 4 4 4
			out.println("<img src="+vo.getPoster()+" width=100% title=\""+vo.getTitle()+"\">");
			out.println("</div>");
		}
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
	}

}










