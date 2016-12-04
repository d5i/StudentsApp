package n4j.dk.layers.application;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HeaderLinksServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Get PrintWriter Object
		PrintWriter out = resp.getWriter();

		// Get Query StringData
		String clickedLink = req.getParameter("body");

		// Include Header Part
		RequestDispatcher dispatcher = req.getRequestDispatcher("Header.html");
		dispatcher.include(req, resp);

		// Include BoodyPart
		if (clickedLink.equals("search")) {
			dispatcher = req.getRequestDispatcher("Search.html");
			dispatcher.include(req, resp);
		} else if (clickedLink.equals("createProfile")) {
			dispatcher = req.getRequestDispatcher("CreateProfile.html");
			dispatcher.include(req, resp);
		} else if (clickedLink.equals("changePassword")) {
			dispatcher = req.getRequestDispatcher("ChangePwd.html");
			dispatcher.include(req, resp);
		} else {

		}

		// Include Footer Part
		dispatcher = req.getRequestDispatcher("Footer.html");
		dispatcher.include(req, resp);
	}

}
