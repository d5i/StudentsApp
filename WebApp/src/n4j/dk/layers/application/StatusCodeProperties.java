package n4j.dk.layers.application;

import java.io.FileReader;
import java.util.Properties;

public class StatusCodeProperties {

	static Properties prop = null;

	public static Properties getProp() {
		if (prop == null) {
			try {
				prop = new Properties();
				String filePath = "F:\\Lab\\JAVA_EE\\WebApp\\WebContent\\StatusCodes.properties";
				// Reading properties file on given location
				FileReader reader = new FileReader(filePath);
				// Converting physical file to Property Object
				prop.load(reader);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return prop;
	}

}
