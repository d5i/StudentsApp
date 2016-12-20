package n4j.dk.layers.application;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import n4j.dk.layers.dataAccess.DatabaseProperties;

public class DataValidation {

	// Get Database Configurations Properties File Object
	Properties prop = DatabaseProperties.getProp();

	// Get Status Code Properties File Object
	Properties scProp = StatusCodeProperties.getProp();

	// Registration Number Validations

	public String regNoValidation(String regNo) {
		int size = Integer.parseInt(prop.getProperty("regNoSize"));

		if (regNo.length() == 0) {
			// Return status code = 10
			return scProp.getProperty("10");
		}

		if (regNo.length() > size) {
			// Return Status Code = 1
			return scProp.getProperty("1");
		}
		Pattern pat = Pattern.compile("[0-9]+");
		Matcher m = pat.matcher(regNo);
		if (!(m.matches())) {
			// Return Status Code = 2
			return scProp.getProperty("2");
		}
		return null;
	}

	public String firstNameValidation(String name) {
		int size = Integer.parseInt(prop.getProperty("firstNameSize"));

		if (name.length() == 0) {
			// Return status code = 11
			return scProp.getProperty("11");
		}

		if (name.length() > size) {
			return scProp.getProperty("3");
		}
		return null;
	}

	public String middleNameValidation(String name) {
		int size = Integer.parseInt(prop.getProperty("middleNameSize"));
		if (name.length() == 0) {
			// Return status code = 12
			return scProp.getProperty("12");
		}

		if (name.length() > size) {
			return scProp.getProperty("4");
		}
		return null;
	}

	public String lastNameValidation(String name) {
		int size = Integer.parseInt(prop.getProperty("lastNameSize"));
		if (name.length() == 0) {
			// Return status code = 13
			return scProp.getProperty("13");
		}

		if (name.length() > size) {
			return scProp.getProperty("5");
		}
		return null;
	}

	public String guardianFirstNameValidation(String name) {
		if (name.length() == 0) {
			// Return status code = 14
			return scProp.getProperty("14");
		}

		int size = Integer.parseInt(prop.getProperty("guardianFirstNameSize"));
		if (name.length() > size) {
			return scProp.getProperty("6");
		}
		return null;
	}

	public String guardianMiddleNameValidation(String name) {
		if (name.length() == 0) {
			// Return status code = 15
			return scProp.getProperty("15");
		}

		int size = Integer.parseInt(prop.getProperty("guardianMiddleNameSize"));
		if (name.length() > size) {
			return scProp.getProperty("7");
		}
		return null;
	}

	public String guardianLastNameValidation(String name) {
		if (name.length() == 0) {
			// Return status code = 16
			return scProp.getProperty("16");
		}

		int size = Integer.parseInt(prop.getProperty("guardianLastNameSize"));
		if (name.length() > size) {
			return scProp.getProperty("8");
		}
		return null;
	}

	public String passwordValidation(String pass) {
		if (pass.length() == 0) {
			// Return status code = 17
			return scProp.getProperty("17");
		}

		int size = Integer.parseInt(prop.getProperty("passwordSize"));
		if (pass.length() > size) {
			return scProp.getProperty("9");
		}
		return null;
	}

	public String isAdminValidation(String isAdmin) {
		if (isAdmin.equals("others")) {
			// Return status code = 18
			System.out.println("HEllo World ");
			return scProp.getProperty("18");
		}
		return null;
	}
}
