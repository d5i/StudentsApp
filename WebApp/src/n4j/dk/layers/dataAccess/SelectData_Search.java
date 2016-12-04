package n4j.dk.layers.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;

import n4j.dk.layers.modelClass.StudentProfile;

//Database operation - Select and Form Operation - Search
public class SelectData_Search {
	Connection con = null;
	boolean isSuccess;

	// Get the DBConnection from MysqlDbConnection
	public StudentProfile searchData(int regNo) {
		try {
			con = MysqlDbConnection.getDatabaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (con != null) {
			return executeQuery(con, regNo);
		} else {
			return null;
		}
	}

	// Issue SQL Queries via Connection - JDBC(3rd step)
	private StudentProfile executeQuery(Connection con, int regNo) {
		PreparedStatement pstmt = null;
		String query = "Select * from " + DatabaseProperties.getProp().getProperty("student_table") + " si" + ","
				+ DatabaseProperties.getProp().getProperty("guardian_table") + " gi"
				+ " where si.reg_no = gi.reg_no and si.reg_no = ?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, regNo);
			ResultSet rs = pstmt.executeQuery();

			// Process Result - JDBC(4th step)
			if (rs.next()) {
				// Set Values in Model Class Object
				StudentProfile studentProfile = new StudentProfile();
				studentProfile.setRegNo(regNo);
				studentProfile.setFirstName(rs.getString("firstName"));
				studentProfile.setMiddleName(rs.getString("middleName"));
				studentProfile.setLastName(rs.getString("lastName"));
				studentProfile.setGuardianFirstName(rs.getString("gfirstName"));
				studentProfile.setGuardianMiddleName(rs.getString("gmiddleName"));
				studentProfile.setGuardianLastName(rs.getString("glastName"));
				return studentProfile;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			// Close All JDBC Objects - JDBC(5th step)
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
