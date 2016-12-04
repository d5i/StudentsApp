package n4j.dk.layers.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;

import n4j.dk.layers.business.ProcessData;
import n4j.dk.layers.modelClass.StudentProfile;

@SuppressWarnings("serial")
public class ShowAllStudentsServlet extends HttpServlet {

	protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {

		StudentProfile[] studentProfile = null;
		StringBuffer htmlResponse = new StringBuffer();

		// Get PrintWriter Object
		PrintWriter out = resp.getWriter();

		// Get Static HTML Code From File
		BufferedReader textReader = null;

		FileReader reader = new FileReader("F:\\Lab\\JAVA_EE\\WebApp\\WebContent\\LoginSuccessForAdmin.html");
		textReader = new BufferedReader(reader);
		String aLine;
		while ((aLine = textReader.readLine()) != null) {
			htmlResponse.append(aLine);
		}
		// Get Student Data from the Database
		ProcessData processData = new ProcessData();
		studentProfile = processData.processForGetStudentsData();

		for (int index = 0; index < studentProfile.length; index++) {
			htmlResponse.append("<tr>");
			if (studentProfile[index] == null) {
				break;
			} else {
				htmlResponse.append("<td>" + studentProfile[index].getRegNo());
				htmlResponse.append("<td>" + studentProfile[index].getFirstName());
				htmlResponse.append("<td>" + studentProfile[index].getMiddleName());
				htmlResponse.append("<td>" + studentProfile[index].getLastName());
				htmlResponse.append("<td>" + studentProfile[index].getGuardianFirstName());
				htmlResponse.append("<td>" + studentProfile[index].getGuardianMiddleName());
				htmlResponse.append("<td>" + studentProfile[index].getGuardianLastName());

				htmlResponse.append("<td align=center> <a href = " + "http://localhost:8080/WebApp/editDelete?regNo="
						+ studentProfile[index].getRegNo() + "&action=edit" + ">"
						+ "<img src='edit.png' height='15px' width='15px' alt='Edit'></img></a>");

				htmlResponse.append("<td align=center> <a href = " + "http://localhost:8080/WebApp/editDelete?regNo="
						+ studentProfile[index].getRegNo() + "&action=delete" + ">"
						+ "<img src='delete.png' height='15px' width='15px' alt='Delete'></img></a>");
				htmlResponse.append("<tr>");
			}
		}

		htmlResponse.append("</table>");
		htmlResponse.append(
				"<div id=\"footerDiv\"><hr id=\"coolBorder\">" + "<marquee behavior=\"scroll\" direction=\"left\">"
						+ "Copyright 2016StudentsApp. All rights reserved.</marquee>" + "</div>");
		htmlResponse.append("</body>");
		htmlResponse.append("</html");
		writeResponse(out, htmlResponse.toString());

		textReader.close();

	} // End Of doGet

	private void writeResponse(PrintWriter out, String responseString) throws IOException {
		out.print(responseString);
	} // end of writeResponse

}
