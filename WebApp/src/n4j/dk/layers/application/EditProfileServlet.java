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
public class EditProfileServlet extends HttpServlet {

	StudentProfile studentProfile = new StudentProfile();
	String isAdmin;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

		// Get PrintWriter Object
		PrintWriter out = resp.getWriter();

		/*
		 * Validate Data
		 */
		String responseString;
		DataValidation dataValidation = new DataValidation();

		responseString = dataValidation.firstNameValidation(firstName);
		if (checkValidationResult(responseString)) {
			writeResponse(out, responseString);
			return;
		}

		responseString = dataValidation.middleNameValidation(middleName);
		if (checkValidationResult(responseString)) {
			writeResponse(out, responseString);
			return;
		}

		responseString = dataValidation.lastNameValidation(lastName);
		if (checkValidationResult(responseString)) {
			writeResponse(out, responseString);
			return;
		}

		responseString = dataValidation.guardianFirstNameValidation(guardianFirstName);
		if (checkValidationResult(responseString)) {
			writeResponse(out, responseString);
			return;
		}

		responseString = dataValidation.guardianMiddleNameValidation(guardianMiddleName);
		if (checkValidationResult(responseString)) {
			writeResponse(out, responseString);
			return;

		}

		responseString = dataValidation.guardianLastNameValidation(guardianLastName);
		if (checkValidationResult(responseString)) {
			writeResponse(out, responseString);
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

		/*
		 * Pass Model Class Object to Business Layer
		 */
		ProcessData processDataObj = new ProcessData();
		boolean status = processDataObj.processData("UpdateData_EditProfile", studentProfile, Boolean.class);

		if (status) {
			responseString = "Profile edited successfully for Register No. " + studentProfile.getRegNo() + "<br>";
			writeResponse(out, responseString);

			RequestDispatcher dispatcher = req.getRequestDispatcher("showStudents");
			dispatcher.include(req, resp);

		} else {
			responseString = "Error in Profile update";
			writeResponse(out, responseString);
		}
	}

	private void writeResponse(PrintWriter out, String responseString) throws IOException {
		String htmlResp = "<html><style>body {font-family: Segoe UI;font-size: 12;font-weight:bold}</style><body>"
				+ responseString + "</body></html>";
		out.println(htmlResp);

	}

	private boolean checkValidationResult(String responseString) {
		if (responseString != null)
			return true;
		return false;
	}
}
