package in.co.rays.project4.exception;

/**
 * DuplicateRecordException thrown when a duplicate record occurred
 * @author Gagan
 *
 */
public class DuplicateException extends Exception {
	
	/**
	 * @param msg
	 * error msg
	 */
	public DuplicateException(String Msg) {
		super(Msg);
	}

}
