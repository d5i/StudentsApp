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
public class EditDeleteServlet extends HttpServlet {

	StudentProfile studentProfile = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Get Query String Parameters
		String regNo = req.getParameter("regNo");
		String action = req.getParameter("action");

		// Get all data from the Database based on Reg No
		ProcessData processDataObj = new ProcessData();
		StudentProfile studentProfile = processDataObj.processData("SelectData_Search", Integer.parseInt(regNo), StudentProfile.class);

		String firstName = studentProfile.getFirstName();
		String middleName = studentProfile.getMiddleName();
		String lastName = studentProfile.getLastName();
		String guardianFirstName = studentProfile.getGuardianFirstName();
		String guardianMiddleName = studentProfile.getGuardianMiddleName();
		String guardianLastName = studentProfile.getGuardianLastName();

		// Get PrintWriter Object
		PrintWriter out = resp.getWriter();

		FileReader reader = null;
		BufferedReader textReader = null;
		if (action.equals("edit")) {

			RequestDispatcher dispatcher = req.getRequestDispatcher("Header.html");
			dispatcher.include(req, resp);

			// Reading HTML File to generate Edit Form
			reader = new FileReader("F:\\Lab\\JAVA_EE\\WebApp\\WebContent\\EditProfile.html");
			textReader = new BufferedReader(reader);
			StringBuffer htmlResponse = new StringBuffer();
			String aLine;
			while ((aLine = textReader.readLine()) != null) {
				htmlResponse.append(aLine);
			}

			// Replacing placeholder values with Input Values
			String responseString = htmlResponse.toString();
			responseString = responseString.replace("#regNo", regNo + "");
			responseString = responseString.replace("#firstName", firstName);
			responseString = responseString.replace("#middleName", middleName);
			responseString = responseString.replace("#lastName", lastName);
			responseString = responseString.replace("#guardianFirstName", guardianFirstName);
			responseString = responseString.replace("#guardianMiddleName", guardianMiddleName);
			responseString = responseString.replace("#guardianLastName", guardianLastName);

			writeResponse(out, responseString);

			dispatcher = req.getRequestDispatcher("Footer.html");
			dispatcher.include(req, resp);

		} else if (action.equals("delete")) {

			RequestDispatcher dispatcher = req.getRequestDispatcher("Header.html");
			dispatcher.include(req, resp);

			// Reading HTML File to generate Delete Form
			reader = new FileReader("F:\\Lab\\JAVA_EE\\WebApp\\WebContent\\DeleteProfile.html");
			textReader = new BufferedReader(reader);
			StringBuffer htmlResponse = new StringBuffer();
			String aLine;
			while ((aLine = textReader.readLine()) != null) {
				htmlResponse.append(aLine);
			}

			// Replacing placeholder values with Input Values
			String responseString = htmlResponse.toString();
			responseString = responseString.replace("#regNo", regNo);
			responseString = responseString.replace("#firstName", firstName);
			responseString = responseString.replace("#middleName", middleName);
			responseString = responseString.replace("#lastName", lastName);
			responseString = responseString.replace("#guardianFirstName", guardianFirstName);
			responseString = responseString.replace("#guardianMiddleName", guardianMiddleName);
			responseString = responseString.replace("#guardianLastName", guardianLastName);

			writeResponse(out, responseString);

			dispatcher = req.getRequestDispatcher("Footer.html");
			dispatcher.include(req, resp);

		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher("Header.html");
			dispatcher.include(req, resp);

			writeResponse(out, "Unsupported Action!!!");

			dispatcher = req.getRequestDispatcher("Footer.html");
			dispatcher.include(req, resp);
		}
		textReader.close();
		reader.close();
	}

	private void writeResponse(PrintWriter out, String responseString) throws IOException {
		out.print(responseString);
	}
}
