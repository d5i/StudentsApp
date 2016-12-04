package n4j.dk.layers.application;

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
public class ChnagePwdServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/*
		 * Get Data from the HTML "ChangePassword.html"
		 */
		String regNo = req.getParameter("regNo");
		String password = req.getParameter("oldPassword");
		String newPassword = req.getParameter("newPassword");
		String retypeNewPassword = req.getParameter("retypeNewPassword");

		/*
		 * Validate Data
		 */
		String responseString;
		DataValidation dataValidation = new DataValidation();

		responseString = dataValidation.regNoValidation(regNo);
		if (checkValidationResult(responseString)) {
			writeResponse(req, resp, responseString);
			return;
		}

		responseString = dataValidation.passwordValidation(password);
		if (checkValidationResult(responseString)) {
			writeResponse(req, resp, responseString);
			return;
		}

		if (newPassword.equals(retypeNewPassword)) {
			/*
			 * Pass Data to the Business Layer
			 */
			StudentProfile student = new StudentProfile();
			student.setRegNo(Integer.parseInt(regNo));
			student.setPassword(password);
			student.setNewPassword(newPassword);

			ProcessData processDataObj = new ProcessData();
			boolean status = processDataObj.processData("UpdateData_ChangePwd", student, Boolean.class);

			if (status) {
				responseString = "Password Changed Successfully";
			} else {
				responseString = "Error in Chaging Password";
			}
		} 
		writeResponse(req, resp, responseString);

	} // end of doPost

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
	} // end // writeResponse

	private boolean checkValidationResult(String responseString) {
		if (responseString != null)
			return true;
		return false;
	} // end of checkValidationResult
} // end of Class
