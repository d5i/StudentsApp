package n4j.dk.commons;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GetCookies extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookie = req.getCookies();
		PrintWriter out = resp.getWriter();
		String responseString;

		if (cookie == null) {
			responseString = "Oops!! your browser seems to have cookies disabled. "
					+ "Make sure cookies are enabled and try opening a new browser window";
			String htmlResp = "<html><style>body {font-family: Segoe UI;font-size: 15;}</style><body>"
					+ "<fieldset><legend>Cookies Disabled</legend>" + responseString + "</fieldset></body></html>";
			out.println(htmlResp);

		} else {
			responseString = "Cookies are Present";
			String htmlResp = "<html><style>body {font-family: Segoe UI;font-size: 15;}</style><body>"
					+ responseString + "</body></html>";
			out.println(htmlResp);

		}
	}
}
