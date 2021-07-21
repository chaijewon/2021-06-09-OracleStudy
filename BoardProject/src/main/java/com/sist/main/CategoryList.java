package com.sist.main;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.data.*;
@WebServlet("/CategoryList")
public class CategoryList extends HttpServlet {
	private static final long serialVersionUID = 1L;
    // service (����) => GET=> doGet()/POST => doPost() doPost()+doGet()=service() 
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // 1. �������� ������ ��� ( html => text/html  / xml => text/xml , json(����:kotlin) => text/plain)
		 response.setContentType("text/html;charset=UTF-8");
		 // ASC (������) => 1byte, Unicode (�ڹ�,�ѱ�) => 2byte
		 // 2. �޸𸮿� ��� => HTML�� ��� �Ѵ� => �������� �о� ���� �޸� ���� Ȯ�� 
		 PrintWriter out=response.getWriter();
		 // 3. �޸� ������ HTML�� ����Ѵ� 
		 out.println("<html>");
		 out.println("<head>");
		 // ���� => CSS / JavaScript
		 out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		 out.println("<style type=text/css>.row{margin:0px auto;width:1200px}</style>"); // ȭ�� ��� ���� 
		 
		 out.println("</head>");
		 // ȭ�� ��� ��ġ => �����͸� ������ �´� (����Ŭ)
		 FoodDAO dao=new FoodDAO();
		 ArrayList<FoodCategoryVO> list=dao.foodCategoryListData();
		 // cno = 1~12 (12)
		 // cno = 13~18 (6)
		 // cno = 19~30 (12)
		 out.println("<body>");
		 out.println("<div class=container>");
		 out.println("<div class=row>");
		 out.println("<h3 style=\"color:orange\">�ϰ� ���� ���� ����Ʈ</h3>");
		 out.println("<hr>");
		 /*
		  * <div class="col-md-4">
			    <div class="thumbnail">
			      <a href="/w3images/lights.jpg">
			        <img src="/w3images/lights.jpg" alt="Lights" style="width:100%">
			        <div class="caption">
			          <p>Lorem ipsum...</p>
			        </div>
			      </a>
			    </div>
			  </div>
		  */
		 for(int i=0;i<12;i++)
		 {
			 FoodCategoryVO vo=list.get(i);
			 out.println("<div class=\"col-md-3\">");// -3 => 12 => �����ٿ� ��� 
			 out.println("<div class=\"thumbnail\">");
			 out.println("<a href=\"FoodList?cno="+vo.getCno()+"\">");
			 out.println("<img src=\""+vo.getPoster()+"\" title="+vo.getSubject()+" style=\"width:100%\">");
			 out.println("<div class=\"caption\">");
			 out.println("<p>"+vo.getTitle()+"</p>");
			 out.println("</div>");
			 out.println("</a>");
			 out.println("</div>");
			 out.println("</div>");
		 }
		 out.println("<h3 style=\"color:orange\">������ �α� ����</h3>");
		 out.println("<hr>");
		 for(int i=12;i<18;i++)
		 {
			 FoodCategoryVO vo=list.get(i);
			 out.println("<div class=\"col-md-4\">");// -3 => 12 => �����ٿ� ��� 
			 out.println("<div class=\"thumbnail\">");
			 out.println("<a href=\"FoodList?cno="+vo.getCno()+"\">");
			 out.println("<img src=\""+vo.getPoster()+"\" title="+vo.getSubject()+" style=\"width:100%\">");
			 out.println("<div class=\"caption\">");
			 out.println("<p>"+vo.getTitle()+"</p>");
			 out.println("</div>");
			 out.println("</a>");
			 out.println("</div>");
			 out.println("</div>");
		 }
		 out.println("<h3 style=\"color:orange\">�޴��� �α� ����</h3>");
		 out.println("<hr>");
		 for(int i=18;i<30;i++)
		 {
			 FoodCategoryVO vo=list.get(i);
			 out.println("<div class=\"col-md-3\">");// -3 => 12 => �����ٿ� ��� 
			 out.println("<div class=\"thumbnail\">");
			 out.println("<a href=\"FoodList?cno="+vo.getCno()+"\">");
			 out.println("<img src=\""+vo.getPoster()+"\" title="+vo.getSubject()+" style=\"width:100%\">");
			 out.println("<div class=\"caption\">");
			 out.println("<p>"+vo.getTitle()+"</p>");
			 out.println("</div>");
			 out.println("</a>");
			 out.println("</div>");
			 out.println("</div>");
		 }
		 out.println("</div>");
		 out.println("</div>");
		 // ������ ȭ�� ��� �κ�
		 /*
		  *   1. �̹���  <img src="��θ�" width="" height="">
		  *   2. ��ũ   <a href="�̵��� ��ġ">
		  *   3. ����   <h1>~<h6>  => <h1> ���� ũ�� 
		  *   4. �Է�â  : <input type=""> 
		  *                type=text   ===> ���� ���ڿ� (id�Է�,�̸��Է�...)
		  *                type=password
		  *                type=radio
		  *                type=checkbox
		  *                type=file
		  *                type=button
		  *                type=submit
		  *                type=reset>
		  *               <select> : �޺��ڽ� 
		  *               <textarea>
		  *   5. ȭ�� ���� : <div> <span>
		  *   6. ��� 
		  *        <table> , <ul> <ol> <dl>
		  *   7. ������ <br> , �ܶ� <p> , ����:<form>
		  *        
		  */
		 out.println("</body>");
		 out.println("</html>");
		 
	}

}







