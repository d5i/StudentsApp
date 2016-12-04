package n4j.dk.layers.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import n4j.dk.layers.modelClass.StudentProfile;

// Database operation - Insert and Form Operation - Submit
public class InsertData_Submit {

	Connection con = null;
	boolean isSuccess;

	// Get the DBConnection from MysqlDbConnection
	public boolean storeData(StudentProfile studentProfile) {
		try {
			con = MysqlDbConnection.getDatabaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (con != null) {
			isSuccess = executeQuery(con, studentProfile);
		} else {
			isSuccess = false;
		}
		return isSuccess;
	}

	// Issue SQL Queries via Connection - JDBC(3rd step)
	private boolean executeQuery(Connection con, StudentProfile studentProfile) {
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;

		// Load Data into 3 Tables
		try {
			String query1 = "Insert into " + DatabaseProperties.getProp().getProperty("student_table")
					+ " (reg_no,firstName,middleName,lastName) " + "VALUES (?,?,?,?);";

			pstmt1 = con.prepareStatement(query1);
			pstmt1.setInt(1, studentProfile.getRegNo());
			pstmt1.setString(2, studentProfile.getFirstName());
			pstmt1.setString(3, studentProfile.getMiddleName());
			pstmt1.setString(4, studentProfile.getLastName());
			@SuppressWarnings("unused")
			int count = pstmt1.executeUpdate();

			String query2 = "Insert into " + DatabaseProperties.getProp().getProperty("guardian_table")
					+ " (reg_no,gfirstName,gmiddleName,glastName) " + "VALUES (?,?,?,?);";
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setInt(1, studentProfile.getRegNo());
			pstmt2.setString(2, studentProfile.getGuardianFirstName());
			pstmt2.setString(3, studentProfile.getGuardianMiddleName());
			pstmt2.setString(4, studentProfile.getGuardianLastName());
			count = pstmt2.executeUpdate();

			String query3 = "Insert into " + DatabaseProperties.getProp().getProperty("student_otherDetails")
					+ " (regno,isadmin,password) " + "VALUES (?,?,?);";
			pstmt3 = con.prepareStatement(query3);
			pstmt3.setInt(1, studentProfile.getRegNo());
			pstmt3.setString(2, studentProfile.getIsAdmin());
			pstmt3.setString(3, studentProfile.getPassword());
			count = pstmt3.executeUpdate();

			return isSuccess = true;
		} catch (SQLException e) {
			e.printStackTrace();
			return isSuccess = false;
		} finally {
			// Close All JDBC Objects - JDBC(5th step)
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
