package n4j.dk.layers.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import n4j.dk.layers.business.ProcessData;
import n4j.dk.layers.modelClass.StudentProfile;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	StudentProfile studentProfile = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the Form Data
		String regNo = req.getParameter("regNo");
		String password = req.getParameter("password");
		String rememberMe = req.getParameter("rememberMe");

		// Get PrintWriter Object
		PrintWriter out = resp.getWriter();

		/*
		 * Validate the Data
		 */
		String responseString;
		DataValidation dataValidation = new DataValidation();

		responseString = dataValidation.regNoValidation(regNo);
		if (checkValidationResult(responseString)) {
			writeResponse(out, responseString);
			return;
		}

		responseString = dataValidation.passwordValidation(password);
		if (checkValidationResult(responseString)) {
			writeResponse(out, responseString);
			return;
		}

		// Pass Data to Business Layer
		StudentProfile student = new StudentProfile();
		student.setRegNo(Integer.parseInt(regNo));
		student.setPassword(password);
		
		ProcessData processDataObj = new ProcessData();
		studentProfile = processDataObj.processData("SelectData_Login", student,StudentProfile.class);

		// Process Results
		if (studentProfile == null) {

			/*
			 * Generating Failure Response
			 */
			String htmlResp = "<html><style>body {font-family: Segoe UI;font-size: 12;}</style><body><font color='red'>"
					+ "Invalid Reg. No/Password!!" + "</font></body></html>";
			out.print(htmlResp);

			RequestDispatcher dispatcher = req.getRequestDispatcher("/loginPage");
			dispatcher.include(req, resp);
		} else {
			if (rememberMe != null) {
				/*
				 * Creating Cookie and Passing in response
				 */
				Cookie regNoCookie = new Cookie("rememberMe", regNo);
				regNoCookie.setMaxAge(24 * 7 * 60 * 60);
				resp.addCookie(regNoCookie);

			}
			RequestDispatcher dispatcher = req.getRequestDispatcher("Header.html");
			dispatcher.include(req, resp);

			// Write All Students Data in the Body Part
			BufferedReader textReader = null;
			FileReader reader = new FileReader("F:\\Lab\\JAVA_EE\\WebApp\\WebContent\\ResponseForSearch.html");
			textReader = new BufferedReader(reader);
			StringBuffer htmlResponse = new StringBuffer();
			String aLine;
			while ((aLine = textReader.readLine()) != null) {
				htmlResponse.append(aLine);
			}
			htmlResponse.append("<td>" + studentProfile.getRegNo());
			htmlResponse.append("<td>" + studentProfile.getFirstName());
			htmlResponse.append("<td>" + studentProfile.getMiddleName());
			htmlResponse.append("<td>" + studentProfile.getLastName());
			htmlResponse.append("<td>" + studentProfile.getGuardianFirstName());
			htmlResponse.append("<td>" + studentProfile.getGuardianMiddleName());
			htmlResponse.append("<td>" + studentProfile.getGuardianLastName());
			htmlResponse.append("</table>");
			htmlResponse.append("<br>");

			if (studentProfile.getIsAdmin().equals("Y")) {
				htmlResponse.append("<a href = \"http://localhost:8080/WebApp/showStudents\">Click Here</a>"
						+ " to view all students");
			}
			htmlResponse.append("<br></body>");
			htmlResponse.append("</html");
			textReader.close();
			writeResponse(out, htmlResponse.toString());

			dispatcher = req.getRequestDispatcher("Footer.html");
			dispatcher.include(req, resp);
		}
	} // end of doPost

	/*
	 * For Home Page Implementation
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean isCookiePresent = false;
		PrintWriter out = resp.getWriter();

		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie rcvdCookie : cookies) {
				// If Cookie is present
				if (rcvdCookie.getName().equals("rememberMe")) {
					isCookiePresent = true;
				}
			}
		}
		if (isCookiePresent) {
			// Include Header Part
			RequestDispatcher dispatcher = req.getRequestDispatcher("Header.html");
			dispatcher.include(req, resp);

			// Write All Students Data in the Body Part
			BufferedReader textReader = null;
			FileReader reader = new FileReader("F:\\Lab\\JAVA_EE\\WebApp\\WebContent\\ResponseForSearch.html");
			textReader = new BufferedReader(reader);
			StringBuffer htmlResponse = new StringBuffer();
			String aLine;
			while ((aLine = textReader.readLine()) != null) {
				htmlResponse.append(aLine);
			}
			htmlResponse.append("<td>" + studentProfile.getRegNo());
			htmlResponse.append("<td>" + studentProfile.getFirstName());
			htmlResponse.append("<td>" + studentProfile.getMiddleName());
			htmlResponse.append("<td>" + studentProfile.getLastName());
			htmlResponse.append("<td>" + studentProfile.getGuardianFirstName());
			htmlResponse.append("<td>" + studentProfile.getGuardianMiddleName());
			htmlResponse.append("<td>" + studentProfile.getGuardianLastName());
			htmlResponse.append("</table>");
			htmlResponse.append("<br>");

			if (studentProfile.getIsAdmin().equals("Y")) {
				htmlResponse.append("<a href = \"http://localhost:8080/WebApp/showStudents\">Click Here</a>"
						+ " to view all students");
			}
			htmlResponse.append("<br></body>");
			htmlResponse.append("</html");
			textReader.close();
			writeResponse(out, htmlResponse.toString());

			dispatcher = req.getRequestDispatcher("Footer.html");
			dispatcher.include(req, resp);
		} else {
			resp.sendRedirect("/loginPage");
		}
	}

	private void writeResponse(PrintWriter out, String responseString) throws IOException {
		String htmlResp = "<html><style>body {font-family: Segoe UI;font-size: 12;}</style><body>" + responseString
				+ "</font></body></html>";
		out.print(htmlResp);
	} // end of writeResponse

	private boolean checkValidationResult(String responseString) {
		if (responseString != null)
			return true;
		return false;
	} // end of checkValidationResult

}
