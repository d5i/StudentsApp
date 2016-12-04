package n4j.dk.layers.application;

import n4j.dk.layers.business.ProcessData;
import n4j.dk.layers.modelClass.StudentProfile;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class StudentDataServlet extends HttpServlet {

	StudentProfile studentProfile = new StudentProfile();
	String isAdmin;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/*
		 * Get Data from the HTML Form
		 */
		String regNo = req.getParameter("regNo");
		String firstName = req.getParameter("firstName");
		String middleName = req.getParameter("middleName");
		String lastName = req.getParameter("lastName");
		String guardianFirstName = req.getParameter("guardianFirstName");
		String guardianMiddleName = req.getParameter("guardianMiddleName");
		String guardianLastName = req.getParameter("guardianLastName");
		String password = req.getParameter("password");
		if (req.getParameter("admin").equals("yes")) {
			isAdmin = "Y";
		} else if (req.getParameter("admin").equals("no")) {
			isAdmin = "N";
		} else {
			isAdmin = "others";
		}

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
		responseString = dataValidation.firstNameValidation(firstName);
		if (checkValidationResult(responseString)) {
			writeResponse(req, resp, responseString);
			return;
		}

		responseString = dataValidation.middleNameValidation(middleName);
		if (checkValidationResult(responseString)) {
			writeResponse(req, resp, responseString);
			return;
		}

		responseString = dataValidation.lastNameValidation(lastName);
		if (checkValidationResult(responseString)) {
			writeResponse(req, resp, responseString);
			return;
		}

		responseString = dataValidation.guardianFirstNameValidation(guardianFirstName);
		if (checkValidationResult(responseString)) {
			writeResponse(req, resp, responseString);
			return;
		}

		responseString = dataValidation.guardianMiddleNameValidation(guardianMiddleName);
		if (checkValidationResult(responseString)) {
			writeResponse(req, resp, responseString);
			return;

		}

		responseString = dataValidation.guardianLastNameValidation(guardianLastName);
		if (checkValidationResult(responseString)) {
			writeResponse(req, resp, responseString);
			return;
		}

		responseString = dataValidation.passwordValidation(password);
		if (checkValidationResult(responseString)) {
			writeResponse(req, resp, responseString);
			return;
		}

		responseString = dataValidation.isAdminValidation(isAdmin);
		if (checkValidationResult(responseString)) {
			writeResponse(req, resp, responseString);
			return;
		}

		/*
		 * Store Data into Model Class
		 */
		studentProfile.setRegNo(Integer.parseInt(regNo.toString()));
		studentProfile.setFirstName(firstName);
		studentProfile.setMiddleName(middleName);
		studentProfile.setLastName(lastName);
		studentProfile.setGuardianFirstName(guardianFirstName);
		studentProfile.setGuardianMiddleName(guardianMiddleName);
		studentProfile.setGuardianLastName(guardianLastName);
		studentProfile.setIsAdmin(isAdmin);
		studentProfile.setPassword(password);

		/*
		 * Pass Model Class Object to Business Layer
		 */
		ProcessData processDataObj = new ProcessData();
		boolean isSuccess = processDataObj.processData("InsertData_Submit", studentProfile, Boolean.class);

		if (isSuccess) {
			writeResponse(req, resp, "Student Profile Created Successfully");
		} else {
			writeResponse(req, resp, "Error in creating Student Profile");
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
