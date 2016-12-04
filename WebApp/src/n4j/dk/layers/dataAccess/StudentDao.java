package n4j.dk.layers.dataAccess;

public interface StudentDao {
	
	// operation should be in form of --- Database operation_Form Operation
	<T,U> T dataAccess(String operation,U input,Class <T> outputType);
	<T> T[] multiRecordsGetter(String operation);
	

}
