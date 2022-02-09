package in.co.rays.project4.exception;

/**
 *  RecordNotFoundException thrown when a record not found occurred
 * @author Gagan
 *
 */
public class RecordNotFoundException extends Exception {
	
	/**
	 * @param msg
	 *      : Error message
	 */
	public RecordNotFoundException(String Msg) {
		super(Msg);
	}
}
