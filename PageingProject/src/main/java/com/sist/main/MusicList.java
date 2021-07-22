package com.sist.main;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;
import com.sist.dao.*;
// => 멜론 (4시) => 캡쳐본 
@WebServlet("/MusicList")
public class MusicList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 출력된 HTML만 브라우저에서 읽어 간다 
		// 전송 (브라우저=> HTML 설정)
		response.setContentType("text/html;charset=UTF-8");
		// 브라우저에서 HTML을 읽는 위치를 지정 
		PrintWriter out=response.getWriter();
		// 데이터 읽기 
		GenieDAO dao=new GenieDAO();
		//1. 사용자가 요청한 페이지를 받는다 
		// MusicList?page=1
		// http://localhost:8080/PageingProject/MusicList
		String strPage=request.getParameter("page");
		// 처음에 실행시에 페이지를 받지 못한다 
		// MusicList   ==> null
		// MusicList?page=  => " " => strPage.equals(" ")
		if(strPage==null)
			strPage="1"; // default 
		int curpage=Integer.parseInt(strPage);
		ArrayList<GenieVO> list=dao.genieListData(curpage);
		// 총페이지 
		int totalpage=dao.genieTotalPage();
		out.println("<html>");
		out.println("<head>");
		// CSS등록 / JavaScript등록 
		/*
		 *    CSS : 두가지 방법 
		 *          = 인라인 CSS => <img style="color:orange">
		 *          = 내부 CSS  => <style type="text/css">
		 *                         row{
		 *                         }
		 *                         </style>
		 *          = 외부 CSS  => 파일을 만든다 <link href="파일명">
		 *                                              ======= 내부,원격
		 *    <script type="text/javascript"> ES5.0 => jquery,ajax
		 *    <script type="text/babel"> ES6.0 => react,vue
		 */
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>.row{margin:0px auto;width:1200px}</style>");
		/*
		 *    style => selector  
		 *    태그명 {
		 *       스타일 지정 
		 *    }
		 *    예)
		 *        body{
		 *           background-color:red
		 *           ================
		 *             속성 
		 *        }
		 *    <a class="aaa"> => 여러개가 중복 (한번에 모든 태그를 스타일 변경)
		 *    .aaa{
		 *    
		 *    }
		 *    <a id="bbb"> => 중복이 없는 상태 (태그 한개만 변경)
		 *    #bbb{
		 *    }
		 *    
		 *    class / id => 디자인용 (태그 구분) => 데이터 수집
		 */
		out.println("</head>");
		out.println("<body>");
		// 화면에 출력하는 태그 
		out.println("<div style=\"height:50px\">"); // html은 값을 첨부할때 공백이 있으면 안된다 
		// 공백 ""
		/*
		 *   <button class="btn btn-sm btn-danger">
		 */
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("<h1 class=text-center>지니뮤직 Top200</h1>");
		out.println("<table class=table>");
		out.println("<tr class=warning>");
		out.println("<th class=text-center>순위</th>");
		out.println("<th class=text-center></th>");
		out.println("<th class=text-center>곡명</th>");
		out.println("<th class=text-center>가수명</th>");
		out.println("<th class=text-center>앨범</th>");
		out.println("</tr>");// 타이틀바 
		for(GenieVO vo:list)
		{
			
			// GenieDetail이동 => 필요한 데이터를 넘겨준다 ?변수=값
			out.println("<tr>");
			out.println("<td class=text-center>"+vo.getNo()+"</td>");
			out.println("<td class=text-center><img src="+vo.getPoster()+" width=30 height=30></td>");
			out.println("<td><a href=GenieDetail?no="+vo.getNo()+">");
			out.println(vo.getTitle());
			out.println("</a></td>");
			out.println("<td>"+vo.getSinger()+"</td>");
			out.println("<td>"+vo.getAlbum()+"</td>");
			out.println("</tr>");
			
		}
		out.println("</table>");
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td class=text-center>");
		out.println("<a href=MusicList?page="+(curpage>1?curpage-1:curpage)+" class=\"btn btn-sm btn-danger\">이전</a>");
		out.println(curpage +" page / "+totalpage+" pages");
		out.println("<a href=MusicList?page="+(curpage<totalpage?curpage+1:curpage)+" class=\"btn btn-sm btn-success\">다음</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>"); // HTML이 종료
	}

}








