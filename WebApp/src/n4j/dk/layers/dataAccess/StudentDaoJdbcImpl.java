package n4j.dk.layers.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import n4j.dk.layers.modelClass.StudentProfile;

public class StudentDaoJdbcImpl implements StudentDao {

	Connection con = null;

	boolean isSuccess;
	PreparedStatement pstmt1 = null;
	PreparedStatement pstmt2 = null;
	PreparedStatement pstmt3 = null;
	ResultSet rs = null;

	@Override
	public <T, U> T dataAccess(String operation, U inputValue, Class<T> type) {

		try {
			// Get Database Connection using Utility Class -- MysqlDbConnection
			con = MysqlDbConnection.getDatabaseConnection();
			switch (operation) {
			case "InsertData_Submit":
				StudentProfile st = (StudentProfile) inputValue;
				return type.cast(insertDataForFormSubmit(st));
			case "SelectData_Search":
				int regNo = (int) inputValue;
				return type.cast(selectDataForSearch(regNo));
			case "DeleteData_DeleteProfile":
				regNo = (int) inputValue;
				return type.cast(deleteProfile(regNo));
			case "UpdateData_EditProfile":
				st = (StudentProfile) inputValue;
				return type.cast(updateDataForEditProfile(st));
			case "UpdateData_ChangePwd":
				st = (StudentProfile) inputValue;
				return type.cast(updateDataForChangePwd(st));
			case "SelectData_Login":
				st = (StudentProfile) inputValue;
				return type.cast(selectDataForLogin(st));
			default:
				return type.cast(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return type.cast(false);
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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private StudentProfile selectDataForLogin(StudentProfile student) throws SQLException {
		String selectUserDetails = "Select * from " + DatabaseProperties.getProp().getProperty("student_table") + " si"
				+ "," + DatabaseProperties.getProp().getProperty("guardian_table") + " gi" + ","
				+ DatabaseProperties.getProp().getProperty("student_otherDetails") + " soi"
				+ " where si.reg_no = gi.reg_no and si.reg_no = soi.regno and soi.regno = ? and soi.password = ?";

		pstmt1 = con.prepareStatement(selectUserDetails);
		pstmt1.setInt(1, student.getRegNo());
		pstmt1.setString(2, student.getPassword());
		rs = pstmt1.executeQuery();
		StudentProfile studentProfile = null;
		if (rs.next()) {
			studentProfile = new StudentProfile();
			studentProfile.setRegNo(student.getRegNo());
			studentProfile.setFirstName(rs.getString("firstName"));
			studentProfile.setMiddleName(rs.getString("middleName"));
			studentProfile.setLastName(rs.getString("lastName"));
			studentProfile.setGuardianFirstName(rs.getString("gfirstName"));
			studentProfile.setGuardianMiddleName(rs.getString("gmiddleName"));
			studentProfile.setGuardianLastName(rs.getString("glastName"));
			studentProfile.setIsAdmin(rs.getString("isadmin"));

		}
		return studentProfile;
	}

	private boolean insertDataForFormSubmit(StudentProfile student) throws SQLException {
		// Load Data into 3 Tables
		String query1 = "Insert into " + DatabaseProperties.getProp().getProperty("student_table")
				+ " (reg_no,firstName,middleName,lastName) " + "VALUES (?,?,?,?);";
		pstmt1 = con.prepareStatement(query1);
		pstmt1.setInt(1, student.getRegNo());
		pstmt1.setString(2, student.getFirstName());
		pstmt1.setString(3, student.getMiddleName());
		pstmt1.setString(4, student.getLastName());
		@SuppressWarnings("unused")
		int count = pstmt1.executeUpdate();

		String query2 = "Insert into " + DatabaseProperties.getProp().getProperty("guardian_table")
				+ " (reg_no,gfirstName,gmiddleName,glastName) " + "VALUES (?,?,?,?);";
		pstmt2 = con.prepareStatement(query2);
		pstmt2.setInt(1, student.getRegNo());
		pstmt2.setString(2, student.getGuardianFirstName());
		pstmt2.setString(3, student.getGuardianMiddleName());
		pstmt2.setString(4, student.getGuardianLastName());
		count = pstmt2.executeUpdate();

		String query3 = "Insert into " + DatabaseProperties.getProp().getProperty("student_otherDetails")
				+ " (regno,isadmin,password) " + "VALUES (?,?,?);";
		pstmt3 = con.prepareStatement(query3);
		pstmt3.setInt(1, student.getRegNo());
		pstmt3.setString(2, student.getIsAdmin());
		pstmt3.setString(3, student.getPassword());
		count = pstmt3.executeUpdate();

		return isSuccess = true;
	}

	private StudentProfile selectDataForSearch(int regNo) throws SQLException {
		String query = "Select * from " + DatabaseProperties.getProp().getProperty("student_table") + " si" + ","
				+ DatabaseProperties.getProp().getProperty("guardian_table") + " gi"
				+ " where si.reg_no = gi.reg_no and si.reg_no = ?";

		pstmt1 = con.prepareStatement(query);
		pstmt1.setInt(1, regNo);
		ResultSet rs = pstmt1.executeQuery();

		// Get Data from ResultSet and set into model class object
		if (rs.next()) {
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
	}

	private boolean deleteProfile(int regNo) throws SQLException {

		String query1 = "DELETE FROM " + DatabaseProperties.getProp().getProperty("student_table")
				+ " where reg_no = ?";

		String query2 = "DELETE FROM " + DatabaseProperties.getProp().getProperty("guardian_table")
				+ " where reg_no = ?";

		String query3 = "DELETE FROM " + DatabaseProperties.getProp().getProperty("student_otherDetails")
				+ " where regno = ?";

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
		if (count != 0) {
			return isSuccess = true;
		} else {
			return isSuccess = false;
		}
	}

	private boolean updateDataForEditProfile(StudentProfile studentProfile) throws SQLException {
		String query1 = "UPDATE " + DatabaseProperties.getProp().getProperty("student_table")
				+ " SET firstName = ?,middleName = ?,lastName = ? where reg_no = ?";

		String query2 = "UPDATE " + DatabaseProperties.getProp().getProperty("guardian_table")
				+ " SET gfirstName = ?,gmiddleName = ?,glastName = ? where reg_no = ?";

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
	}

	private boolean updateDataForChangePwd(StudentProfile student) throws SQLException {
		String query = "UPDATE " + DatabaseProperties.getProp().getProperty("student_otherDetails")
				+ " SET password = ? where regno = ? AND password = ?";

		pstmt1 = con.prepareStatement(query);
		pstmt1.setString(3, student.getPassword());
		pstmt1.setInt(2, student.getRegNo());
		pstmt1.setString(1, student.getNewPassword());
		int count = pstmt1.executeUpdate();

		if (count != 0) {
			return isSuccess = true;
		} else {
			return isSuccess = false;
		}
	}

	@Override
	public <T> T[] multiRecordsGetter(String operation) {
		return null;
	}

	// Close All JDBC Objects - JDBC(5th step)

}
