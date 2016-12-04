package n4j.dk.layers.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class RememberMeServlet extends HttpServlet {

	protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {
		// Check Cookies Present
		Cookie[] cookies = req.getCookies();
		String regNo = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("rememberMe")) {
					regNo = cookie.getValue();
				}
			}
		}

		BufferedReader textReader = null;

		FileReader reader = new FileReader("F:\\Lab\\JAVA_EE\\WebApp\\WebContent\\loginForm.html");
		textReader = new BufferedReader(reader);
		StringBuffer htmlResponse = new StringBuffer();
		String aLine;
		while ((aLine = textReader.readLine()) != null) {
			htmlResponse.append(aLine);
		}
		String response = htmlResponse.toString();
		if (regNo != null)
			response = response.replace("#regNo", regNo);
		else 
			response = response.replace("#regNo", "");
		
		PrintWriter out = resp.getWriter();
		out.print(response);
		textReader.close();

	}
}
