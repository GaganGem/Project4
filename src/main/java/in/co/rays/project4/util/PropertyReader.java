package in.co.rays.project4.util;

import java.util.ResourceBundle;

/**
 * Read the property values from application properties 
 * file using Resource Bundle
 * @author Gagan
 *
 */
public class PropertyReader {

	private static ResourceBundle rb = ResourceBundle.getBundle("resourceBundle/res");

	/**
     * Return value of key
     *
     * @param key
     * @return val
     */
	public static String getValue(String key) {
		try {
			return rb.getString(key);
		} catch (Exception e) {
			return key;
		}
	}

	/**
     * Gets String after placing param values
     *
     * @param key
     * @param param
     * @return String
     */
	public static String getValue(String key, String param) {
		String msg = getValue(key);
		msg = msg.replace("{0}", param);
		return msg;
	}

	/**
     * Gets String after placing params values
     *
     * @param key
     * @param params
     * @return String
     */
	public static String getValue(String key, String[] params) {
		String msg = getValue(key);
		for (int i = 0; i < params.length; i++) {
			msg = msg.replace("{" + i + "}", params[i]);
		}
		return msg;
	}

	public static void main(String[] args) {
		  String[] params = { "Roll No" };
	        System.out.println(PropertyReader.getValue("error.require", params));

	}
}
