package n4j.dk.layers.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import n4j.dk.layers.modelClass.StudentProfile;

//Database operation - Select and Form Operation - Login Form
public class SelectData_Login {
	Connection con = null;
	boolean isSuccess;

	// Get the DBConnection from MysqlDbConnection
	public StudentProfile signIn(int regNo, String password) {
		try {
			con = MysqlDbConnection.getDatabaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (con != null) {
			return executeQuery1(con, regNo, password);
		} else {
			return null;
		}
	}

	public StudentProfile[] getAllStudents() {
		try {
			con = MysqlDbConnection.getDatabaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (con != null) {
			return executeQuery2();
		} else {
			return null;
		}
	}

	// Issue SQL Queries via Connection - JDBC(3rd step)
	private StudentProfile executeQuery1(Connection con, int regNo, String password) {
		PreparedStatement pstmt1 = null;
		ResultSet rsForUserDetails = null;
		StudentProfile studentProfile = null;

		// Query to Get User Details using reg no and password
		String selectUserDetails = "Select * from " + DatabaseProperties.getProp().getProperty("student_table") + " si"
				+ "," + DatabaseProperties.getProp().getProperty("guardian_table") + " gi" + ","
				+ DatabaseProperties.getProp().getProperty("student_otherDetails") + " soi"
				+ " where si.reg_no = gi.reg_no and si.reg_no = soi.regno and soi.regno = ? and soi.password = ?";
		try {
			pstmt1 = con.prepareStatement(selectUserDetails);
			pstmt1.setInt(1, regNo);
			pstmt1.setString(2, password);
			rsForUserDetails = pstmt1.executeQuery();

			if (rsForUserDetails.next()) {
				studentProfile = new StudentProfile();
				studentProfile.setRegNo(regNo);
				studentProfile.setFirstName(rsForUserDetails.getString("firstName"));
				studentProfile.setMiddleName(rsForUserDetails.getString("middleName"));
				studentProfile.setLastName(rsForUserDetails.getString("lastName"));
				studentProfile.setGuardianFirstName(rsForUserDetails.getString("gfirstName"));
				studentProfile.setGuardianMiddleName(rsForUserDetails.getString("gmiddleName"));
				studentProfile.setGuardianLastName(rsForUserDetails.getString("glastName"));
				studentProfile.setIsAdmin(rsForUserDetails.getString("isadmin"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
			}
			if (rsForUserDetails != null) {
				try {
					rsForUserDetails.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return studentProfile;
	} // End of ExecuteQuery1

	private StudentProfile[] executeQuery2() {

		// Query to Get all students details (Except All Admins)
		String selectAllStudentsDetails = "Select * from " + DatabaseProperties.getProp().getProperty("student_table")
				+ " si" + "," + DatabaseProperties.getProp().getProperty("guardian_table") + " gi" + ","
				+ DatabaseProperties.getProp().getProperty("student_otherDetails") + " soi"
				+ " where si.reg_no = gi.reg_no and si.reg_no = soi.regno and soi.isadmin = 'N'";

		int index = 0;
		Statement stmt = null;
		ResultSet rsForStudentDetails = null;
		StudentProfile studentProfile[] = new StudentProfile[30];

		try {
			stmt = con.createStatement();
			rsForStudentDetails = stmt.executeQuery(selectAllStudentsDetails);

			while (rsForStudentDetails.next()) {
				studentProfile[index] = new StudentProfile();
				studentProfile[index].setRegNo(Integer.parseInt(rsForStudentDetails.getString("reg_no")));
				studentProfile[index].setFirstName(rsForStudentDetails.getString("firstName"));
				studentProfile[index].setMiddleName(rsForStudentDetails.getString("middleName"));
				studentProfile[index].setLastName(rsForStudentDetails.getString("lastName"));
				studentProfile[index].setGuardianFirstName(rsForStudentDetails.getString("gfirstName"));
				studentProfile[index].setGuardianMiddleName(rsForStudentDetails.getString("gmiddleName"));
				studentProfile[index].setGuardianLastName(rsForStudentDetails.getString("glastName"));

				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rsForStudentDetails != null) {
				try {
					rsForStudentDetails.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return studentProfile;
	}
}