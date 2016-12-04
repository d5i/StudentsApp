package n4j.dk.layers.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

public class MysqlDbConnection {

	static Connection con = null;

	public static Connection getDatabaseConnection() throws SQLException {

		try {
			// i. Load the MySQL Driver
			Driver driverRef = new Driver();
			DriverManager.registerDriver(driverRef);

			// ii. Get the DB Connection via Driver
			String dbUrl = DatabaseProperties.getProp().getProperty("dbUrl");
			con = DriverManager.getConnection(dbUrl, DatabaseProperties.getProp());

			return con;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
