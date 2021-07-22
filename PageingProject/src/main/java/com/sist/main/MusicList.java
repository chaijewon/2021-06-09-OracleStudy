package com.sist.main;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;
import com.sist.dao.*;
// => ��� (4��) => ĸ�ĺ� 
@WebServlet("/MusicList")
public class MusicList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ��µ� HTML�� ���������� �о� ���� 
		// ���� (������=> HTML ����)
		response.setContentType("text/html;charset=UTF-8");
		// ���������� HTML�� �д� ��ġ�� ���� 
		PrintWriter out=response.getWriter();
		// ������ �б� 
		GenieDAO dao=new GenieDAO();
		//1. ����ڰ� ��û�� �������� �޴´� 
		// MusicList?page=1
		// http://localhost:8080/PageingProject/MusicList
		String strPage=request.getParameter("page");
		// ó���� ����ÿ� �������� ���� ���Ѵ� 
		// MusicList   ==> null
		// MusicList?page=  => " " => strPage.equals(" ")
		if(strPage==null)
			strPage="1"; // default 
		int curpage=Integer.parseInt(strPage);
		ArrayList<GenieVO> list=dao.genieListData(curpage);
		// �������� 
		int totalpage=dao.genieTotalPage();
		out.println("<html>");
		out.println("<head>");
		// CSS��� / JavaScript��� 
		/*
		 *    CSS : �ΰ��� ��� 
		 *          = �ζ��� CSS => <img style="color:orange">
		 *          = ���� CSS  => <style type="text/css">
		 *                         row{
		 *                         }
		 *                         </style>
		 *          = �ܺ� CSS  => ������ ����� <link href="���ϸ�">
		 *                                              ======= ����,����
		 *    <script type="text/javascript"> ES5.0 => jquery,ajax
		 *    <script type="text/babel"> ES6.0 => react,vue
		 */
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>.row{margin:0px auto;width:1200px}</style>");
		/*
		 *    style => selector  
		 *    �±׸� {
		 *       ��Ÿ�� ���� 
		 *    }
		 *    ��)
		 *        body{
		 *           background-color:red
		 *           ================
		 *             �Ӽ� 
		 *        }
		 *    <a class="aaa"> => �������� �ߺ� (�ѹ��� ��� �±׸� ��Ÿ�� ����)
		 *    .aaa{
		 *    
		 *    }
		 *    <a id="bbb"> => �ߺ��� ���� ���� (�±� �Ѱ��� ����)
		 *    #bbb{
		 *    }
		 *    
		 *    class / id => �����ο� (�±� ����) => ������ ����
		 */
		out.println("</head>");
		out.println("<body>");
		// ȭ�鿡 ����ϴ� �±� 
		out.println("<div style=\"height:50px\">"); // html�� ���� ÷���Ҷ� ������ ������ �ȵȴ� 
		// ���� ""
		/*
		 *   <button class="btn btn-sm btn-danger">
		 */
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("<h1 class=text-center>���Ϲ��� Top200</h1>");
		out.println("<table class=table>");
		out.println("<tr class=warning>");
		out.println("<th class=text-center>����</th>");
		out.println("<th class=text-center></th>");
		out.println("<th class=text-center>���</th>");
		out.println("<th class=text-center>������</th>");
		out.println("<th class=text-center>�ٹ�</th>");
		out.println("</tr>");// Ÿ��Ʋ�� 
		for(GenieVO vo:list)
		{
			
			// GenieDetail�̵� => �ʿ��� �����͸� �Ѱ��ش� ?����=��
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
		out.println("<a href=MusicList?page="+(curpage>1?curpage-1:curpage)+" class=\"btn btn-sm btn-danger\">����</a>");
		out.println(curpage +" page / "+totalpage+" pages");
		out.println("<a href=MusicList?page="+(curpage<totalpage?curpage+1:curpage)+" class=\"btn btn-sm btn-success\">����</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>"); // HTML�� ����
	}

}








