package com.sist.main;
// tomcat=> 9���� => javax.
// tomcat=> 10���� => jakarta
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/*
 *   Servlet  => server+let : �������� ����Ǵ� ������ ���α׷� (�ڹ�=>����) => ������
 *               �ڹ� => �����ø��� �������� �ؾ� �Ѵ� => JSP
 *              // Applet (Application+let) , MIDlet (����Ͽ��� ����Ǵ� ������ ���α׷�)
 *                                             Kotlin
 *   ==>  Servlet ���� 
 *        �޸� �Ҵ� ===> ��Ĺ(����ڰ� URL�� ���ؼ� ��û)
 *        ========================================== �ڵ� ȣ�� (callback)
 *        init() : ȯ�漳�� (������ ���)
 *        doGet() / doPost() => ����� ���� (HTML)
 *        ����� ��û�ϴ� ��� 
 *         GET : URL�ڿ� ?����=�� (��û���� ����) => doGet() => ��������û,�ܼ��� ��û
 *         POST: ���� (���γ�Ʈ��ũ�� ���ؼ� ����) : �α���,ȸ������, �۾��� => doPost()
 *        destory() : �޸� ���� 
 *        
 *        Default => ȭ�� ��� (get)
 *                   �����͸� �޾Ƽ� ��� (post)
 *                   
 *        �߿���� 
 *          => �ڹ� 
 *              Ŭ���� <====> Ŭ���� 
 *                   �޼ҵ� (�Ű������� ������ ����)
 *          => ���� �����͸� �����Ҷ� URL�� �̿��Ѵ� ==> ?=>������ ���� 
 *             /BoardList?page=1 => �Ű����� ���� 
 */
import java.util.*;
import com.sist.dao.*;
/*
 *   <html> : ���� �±�
 *     <head>
 *        => ���� => CSS/JavaScript => ȭ�鿡 ����ϴ� �κ��� �ƴϴ� 
 *                       react/vue/ajax => 
 *     </head>
 *     <body>
 *        => ȭ�鿡 ����ϴ� �κ� 
 *     </body>
 *   </html>: �ݴ� �±�
 */
@WebServlet("/BoardList")
public class BoardList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request => ����� ��û�� �޴� ��쿡 ��� 
		// response => ����� ��û ó���Ŀ� ����(�������� HTML,Cookie����)
		// 1. �����ϴ� ���� => ������ (HTML/XML)
		response.setContentType("text/html;charset=UTF-8");
		// 2. HTML�� ���� �� �غ� => ��û�� ������� ���� 
		PrintWriter out=response.getWriter(); //out�̶�� �޸� ������ HTML��� => ������
		/*
		 *     <html>
				<head>
				</head>
				<body>
				<center><h1>�����Խ���</h1></center>
				</body>
				</html>
		 */
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>row{margin:0px auto;width:700px}</style>"); // ȭ�� ��� ���� 
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("<h1 class=text-center>�����Խ���</h1>");
		out.println("<table class=table>");
		out.println("<tr class=danger>");// info , danger , warning , 
		out.println("<th width=10%>��ȣ</th>");
		out.println("<th width=45%>����</th>");
		out.println("<th width=15%>�̸�</th>");
		out.println("<th width=20%>�ۼ���</th>");
		out.println("<th width=10%>��ȸ��</th>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
	}

}







