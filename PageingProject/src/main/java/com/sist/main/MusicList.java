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
		ArrayList<GenieVO> list=dao.genieListData(1);
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
			out.println("<tr>");
			out.println("<td class=text-center>"+vo.getNo()+"</td>");
			out.println("<td class=text-center><img src="+vo.getPoster()+" width=30 height=30></td>");
			out.println("<td>"+vo.getTitle()+"</td>");
			out.println("<td>"+vo.getSinger()+"</td>");
			out.println("<td>"+vo.getAlbum()+"</td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>"); // HTML�� ����
	}

}








