package n4j.dk.layers.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import n4j.dk.layers.business.ProcessData;
import n4j.dk.layers.modelClass.StudentProfile;

@SuppressWarnings("serial")
public class StudentSearchServlet extends HttpServlet {

	StudentProfile studentProfile;
	ProcessData processData = new ProcessData();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Get Search Form Data
		String regNo = req.getParameter("regNo");

		// Validate Data
		DataValidation dataValidation = new DataValidation();
		String responseString = dataValidation.regNoValidation(regNo);
		if (checkValidationResult(responseString)) {
			writeResponse(req, resp, responseString);
			return;
		}

		// Pass regNo to Business Layer
		ProcessData processDataObj = new ProcessData();
		StudentProfile studentProfile = processDataObj.processData("SelectData_Search",  Integer.parseInt(regNo), StudentProfile.class);

		// Process Results
		BufferedReader textReader = null;
		if (studentProfile != null) {
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
			htmlResponse.append("</body>");
			htmlResponse.append("</html");

			writeResponse(req, resp, htmlResponse.toString());

			textReader.close();
		} else {
			writeResponse(req, resp, "Reg No does not exist!!");

		}
	}

	private void writeResponse(HttpServletRequest req, HttpServletResponse resp, String responseString)
			throws IOException, ServletException {

		// Get PrintWriter Object
		PrintWriter out = resp.getWriter();

		RequestDispatcher dispatcher = req.getRequestDispatcher("Header.html");
		dispatcher.include(req, resp);

		String htmlResp = "<html><style>body {font-family: Segoe UI;font-size: 17;}</style><body>" + responseString
				+ "</body></html>";
		out.print(htmlResp);

		dispatcher = req.getRequestDispatcher("Footer.html");
		dispatcher.include(req, resp);
	}

	private boolean checkValidationResult(String responseString) {
		if (responseString != null)
			return true;
		return false;
	}
}
