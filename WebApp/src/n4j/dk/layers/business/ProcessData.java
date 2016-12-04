package n4j.dk.layers.business;

import n4j.dk.layers.modelClass.StudentProfile;
import n4j.dk.layers.dataAccess.SelectData_Login;
import n4j.dk.layers.dataAccess.StudentDao;
import n4j.dk.layers.dataAccess.StudentDaoJdbcImpl;

public class ProcessData {

	public StudentProfile[] processForGetStudentsData() {
		SelectData_Login selectObj = new SelectData_Login();
		return selectObj.getAllStudents();
	}
	
	public <T, U> T processData(String operation, U inputValue, Class<T> type) {
		StudentDao dao = new StudentDaoJdbcImpl();
		return dao.dataAccess(operation,inputValue,type);
	}
}
