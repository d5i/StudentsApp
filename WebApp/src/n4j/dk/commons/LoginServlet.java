package n4j.dk.commons;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("Inside Get Method");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get Form Input Data
		String name = req.getParameter("name");
		String password = req.getParameter("password");

		// Set Response
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		String htmlres = "<html>" + "<title> Hello </title>" + "<body> Name : " + name + "<br>" + "Password : "
				+ password + "</body>" + "</html>";
		out.println(htmlres);
	}

}
