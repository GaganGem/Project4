package in.co.rays.project4.exception;

/**
 * DatabaseException is prpogated by DAO classes when an unhandled Database
 * exception occurred
 * @author Gagan
 *
 */
public class DataBaseException extends Exception {
	
	/**
	 * @param msg
	 * error message
	 */
	public DataBaseException(String Msg) {
		super(Msg);
	}
}
