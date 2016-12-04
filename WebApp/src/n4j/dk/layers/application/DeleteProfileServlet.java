package n4j.dk.layers.application;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import n4j.dk.layers.business.ProcessData;

@SuppressWarnings("serial")
public class DeleteProfileServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String responseString;
		// Get Reg No from Form
		int regNo = Integer.parseInt(req.getParameter("regNo"));

		// Pass RegNo to Business Layer
		ProcessData processDataObj = new ProcessData();
		boolean status = processDataObj.processData("DeleteData_DeleteProfile", regNo, Boolean.class);
		
		// Get PrintWriter Object
		PrintWriter out = resp.getWriter();

		if (status) {
			responseString = "Profile deleted successfully For Register No. " + regNo +"<br>";
			writeResponse(out, responseString);

			RequestDispatcher dispatcher = req.getRequestDispatcher("showStudents");
			dispatcher.include(req, resp);

		} else {
			responseString = "Error in Profile Delete";
			writeResponse(out, responseString);
		}
	}

	private void writeResponse(PrintWriter out, String responseString) throws IOException {
		String htmlResp = "<html><style>body {font-family: Segoe UI;font-size: 15;font-weight:bold;}</style><body>" + responseString
				+ "</body></html>";
		out.print(htmlResp);
	}

}
