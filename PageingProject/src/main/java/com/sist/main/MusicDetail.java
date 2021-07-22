package com.sist.main;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.sist.dao.*;
@WebServlet("/GenieDetail")
public class MusicDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		// GenieDetail?no=10
		String no=request.getParameter("no");
		GenieDAO dao=new GenieDAO();
		GenieVO vo=dao.genieDetailData(Integer.parseInt(no));
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>"+vo.getTitle()+"</h1>");
		out.println("<iframe src=http://www.youtube.com/embed/"+vo.getKey()+" width=600 height=450></iframe>");
		// <iframe> <embed>
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

}








