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
    // �������� �����ϴ� ��� (GET/POST) => doGet(),doPost()
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ���� �޼ҵ� => Ŭ���̾�Ʈ�� ��û�� ���� => �ڹٹ��� (HTML)�� ���� �������� ����
		// 1. �����ϴ� ������ ���� => HTML/XML => ȭ�� ��� (HTML) , ����(XML)
		response.setContentType("text/html;charset=UTF-8");
		// 1-1 response(����=> �������� ����), request(Ŭ���̾�Ʈ�� ������ ���� ������ ���)
		// 2. �������� �о �� �ִ� �޸� ���� 
		PrintWriter out=response.getWriter();
		// 3. out�޸𸮿� HTML�� ����ϸ� => �������� �о ��� 
		out.println("<html>"); 
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>row{margin:0px auto}</style>"); // ȭ�� ��� ���� 
		out.println("</head>"); // CSS,JavaScript��� => ����ϴ� ������ �ƴϴ� 
		out.println("<body>");
		out.println("<div class=container>"); // full ȭ��
		out.println("<div class=row>");
		out.println("<h1 class=text-center>���� Top 100</h1>");
		MusicDAO dao=new MusicDAO();
		ArrayList<GenieVO> list=dao.genieAllData();
		out.println("<table class=table>");
		out.println("<tr class=danger>");
		out.println("<th class=text-center>��ȣ</th>");
		out.println("<th class=text-center></th>");
		out.println("<th class=text-center></th>");
		out.println("<th class=text-center>���</th>");
		out.println("<th class=text-center>������</th>");
		out.println("<th class=text-center>�ٹ�</th>");
		out.println("</tr>");
		// ������ ��� 
		for(GenieVO vo:list)
		{
			out.println("<tr>");
			out.println("<td class=text-center>"+vo.getNo()+"</td>");
			String s="";
			if(vo.getState().trim().equals("����"))
			{
				s="<font color=gray>-</font>";
			}
			else if(vo.getState().trim().equals("���"))
			{
				s="<font color=blue>��</font> "+vo.getIdcerment();
			}
			else if(vo.getState().trim().equals("�ϰ�"))
			{
				s="<font color=red>��</font> "+vo.getIdcerment();
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
		out.println("</body>"); // ȭ�鿡 ����ϴ� ����
		out.println("</html>");
	}

}









