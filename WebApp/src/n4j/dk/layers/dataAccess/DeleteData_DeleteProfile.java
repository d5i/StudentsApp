package n4j.dk.layers.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Database operation - Delete and Form Operation - Delete Profile
public class DeleteData_DeleteProfile {

	// Get the DBConnection from MysqlDbConnection
	Connection con = null;
	boolean isSuccess;

	// Get the DBConnection from MysqlDbConnection
	public boolean update(int regNo) {
		try {
			con = MysqlDbConnection.getDatabaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (con != null) {
			return executeQuery(con, regNo);
		} else {
			return isSuccess = false;
		}
	}

	private boolean executeQuery(Connection con, int regNo) {
		// Update Profile Data
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;

		String query1 = "DELETE FROM " + DatabaseProperties.getProp().getProperty("student_table") + " where reg_no = ?";

		String query2 = "DELETE FROM " + DatabaseProperties.getProp().getProperty("guardian_table")
				+ " where reg_no = ?";
		
		String query3 = "DELETE FROM " + DatabaseProperties.getProp().getProperty("student_otherDetails")
				+ " where regno = ?";

		try {
			pstmt1 = con.prepareStatement(query1);
			pstmt1.setInt(1, regNo);

			int count = pstmt1.executeUpdate();

			if (count == 0) {
				return isSuccess = false;
			}

			pstmt2 = con.prepareStatement(query2);
			pstmt2.setInt(1, regNo);

			count = pstmt2.executeUpdate();
			
			if (count == 0) {
				return isSuccess = false;
			}
			
			pstmt3 = con.prepareStatement(query3);
			pstmt3.setInt(1, regNo);

			count = pstmt3.executeUpdate();

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
