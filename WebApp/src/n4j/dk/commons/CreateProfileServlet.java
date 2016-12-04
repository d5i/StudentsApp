package n4j.dk.commons;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Driver;

@SuppressWarnings("serial")
public class CreateProfileServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Get the Form Data
		String isAdmin;
		String regNo = req.getParameter("regNo");
		String firstName = req.getParameter("firstName");
		String middleName = req.getParameter("middleName");
		String lastName = req.getParameter("lastName");
		String guardianFirstName = req.getParameter("guardianFirstName");
		String guardianMiddleName = req.getParameter("guardianMiddleName");
		String guardianLastName = req.getParameter("guardianLastName");
		String password = req.getParameter("password");
		if (req.getParameter("admin").equals("YES")) {
			isAdmin = "Y";
		} else {
			isAdmin = "N";
		}

		// 2. Store FormData into Tables
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		try {
			// i. Load the MySQL Driver
			Driver driverRef = new Driver();
			DriverManager.registerDriver(driverRef);

			// Reading properties file for configuration values
			String filePath = "F:\\Lab\\JAVA_EE\\WebApp\\WebContent\\MySQLConf.properties";
			FileReader reader = new FileReader(filePath);

			// Converting physical file to Property Object
			Properties prop = new Properties();
			prop.load(reader);

			String database = prop.getProperty("database");

			// ii. Get the DB Connection via Driver
			String dbUrl = "jdbc:mysql://localhost:3306/" + database;
			con = DriverManager.getConnection(dbUrl, prop);

			// iii. Issue SQL Queries via Connection
			String query1 = "Insert into " + prop.getProperty("student_table")
					+ " (reg_no,firstName,middleName,lastName) " + "VALUES (?,?,?,?);";
			String query2 = "Insert into " + prop.getProperty("guardian_table")
					+ " (reg_no,gfirstName,gmiddleName,glastName) " + "VALUES (?,?,?,?);";
			String query3 = "Insert into " + prop.getProperty("student_otherDetails") + " (regno,isadmin,password) "
					+ "VALUES (?,?,?);";

			pstmt1 = con.prepareStatement(query1);
			pstmt1.setInt(1, Integer.parseInt(regNo));
			pstmt1.setString(2, firstName);
			pstmt1.setString(3, middleName);
			pstmt1.setString(4, lastName);
			pstmt1.executeUpdate();

			pstmt2 = con.prepareStatement(query2);
			pstmt2.setInt(1, Integer.parseInt(regNo));
			pstmt2.setString(2, guardianFirstName);
			pstmt2.setString(3, guardianMiddleName);
			pstmt2.setString(4, guardianLastName);
			pstmt2.executeUpdate();

			pstmt3 = con.prepareStatement(query3);
			pstmt3.setInt(1, Integer.parseInt(regNo));
			pstmt3.setString(2, isAdmin);
			pstmt3.setString(3, password);
			pstmt3.executeUpdate();

			// iv. Process the results
			System.out.println("Profile Created Successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in creating Profile");
		} finally {
			// v. Close all JDBC Objects
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt1 != null) {
				try {
					pstmt1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (pstmt2 != null) {
					try {
						pstmt2.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (pstmt3 != null) {
					try {
						pstmt3.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
