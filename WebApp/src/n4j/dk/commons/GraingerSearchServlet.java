package n4j.dk.commons;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GraingerSearchServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get Form Data
		String keyowrd = req.getParameter("keyword");

		// Redirect it to www.grainger.com
		resp.sendRedirect("https://www.grainger.com/search?searchBar=true&searchQuery=" + keyowrd);
	}
}
