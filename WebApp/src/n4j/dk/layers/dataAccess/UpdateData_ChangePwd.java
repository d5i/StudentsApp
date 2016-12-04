package n4j.dk.layers.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Database operation - Update and Form Operation - Change Password
public class UpdateData_ChangePwd {
	Connection con = null;
	String status;

	// Get the DBConnection from MysqlDbConnection
	public String changePwd(int regNo, String password, String newPassword) {
		try {
			con = MysqlDbConnection.getDatabaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (con != null) {
			return executeQuery(con, regNo, password, newPassword);
		} else {
			return status = "error";
		}
	}

	// Issue SQL Queries via Connection - JDBC(3rd step)
	private String executeQuery(Connection con, int regNo, String password, String newPassword) {
		PreparedStatement pstmt = null;
		String query = "UPDATE " + DatabaseProperties.getProp().getProperty("student_otherDetails")
				+ " SET password = ? where regno = ? AND password = ?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(3, password);
			pstmt.setInt(2, regNo);
			pstmt.setString(1, newPassword);
			int count = pstmt.executeUpdate();

			// Process Result - JDBC(4th step)
			if (count != 0) {
				return status = "success";
			} else {
				return status = "fail";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return status = "error";
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
