package in.co.rays.project4.exception;

/**
 * ApplicationException is propogated from Service classes when an business
 * logic exception occurered.
 * @author Gagan
 *
 */
public class ApplicationException extends Exception{

	/**
	 * @param msg
	 *      : Error message
	 */
	 public ApplicationException(String Msg){
		 super(Msg);
	 }
}
