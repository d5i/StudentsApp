package n4j.dk.layers.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import n4j.dk.layers.modelClass.StudentProfile;

//Database operation - Update and Form Operation - Edit Profile
public class UpdateData_EditProfile {
	Connection con = null;
	boolean isSuccess;

	// Get the DBConnection from MysqlDbConnection
	public boolean update(StudentProfile studentProfile) {
		try {
			con = MysqlDbConnection.getDatabaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (con != null) {
			return executeQuery(con, studentProfile);
		} else {
			return isSuccess = false;
		}
	}

	private boolean executeQuery(Connection con, StudentProfile studentProfile) {
		// Update Profile Data
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;

		String query1 = "UPDATE " + DatabaseProperties.getProp().getProperty("student_table")
				+ " SET firstName = ?,middleName = ?,lastName = ? where reg_no = ?";

		String query2 = "UPDATE " + DatabaseProperties.getProp().getProperty("guardian_table")
				+ " SET gfirstName = ?,gmiddleName = ?,glastName = ? where reg_no = ?";

		try {
			pstmt1 = con.prepareStatement(query1);
			pstmt1.setString(1, studentProfile.getFirstName());
			pstmt1.setString(2, studentProfile.getMiddleName());
			pstmt1.setString(3, studentProfile.getLastName());
			pstmt1.setInt(4, studentProfile.getRegNo());

			int count = pstmt1.executeUpdate();

			if (count == 0) {
				return isSuccess = false;
			}

			pstmt2 = con.prepareStatement(query2);
			pstmt2.setString(1, studentProfile.getGuardianFirstName());
			pstmt2.setString(2, studentProfile.getGuardianMiddleName());
			pstmt2.setString(3, studentProfile.getGuardianLastName());
			pstmt2.setInt(4, studentProfile.getRegNo());

			count = pstmt2.executeUpdate();

			// Process Result - JDBC(4th step)
			if (count != 0) {
				return isSuccess = true;
			} else {
				return isSuccess = false;
			}
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
		}
	}
}
