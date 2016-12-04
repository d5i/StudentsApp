package n4j.dk.commons;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CreateCookieServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie myNameCookie = new Cookie("myName","n4j");
		Cookie myLocationCookie = new Cookie("myLocation","Banaglore");
		
		myLocationCookie.setMaxAge(24*7*60*60);
		
		resp.addCookie(myNameCookie);
		resp.addCookie(myLocationCookie);
		
		PrintWriter out = resp.getWriter();
		out.print("Cookies Created");
	}
}
