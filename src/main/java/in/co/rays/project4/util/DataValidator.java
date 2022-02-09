package in.co.rays.project4.util;

import java.text.ParseException;
import java.util.Date;

/**
 * 
 * @author Gagan
 *
 */
public class DataValidator {
	/**
	 * Checks if value is Null
	 *
	 *
	 */
	public static boolean isNull(String val) {
		if (val == null || val.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if value is NOT Null
	 *
	 * 
	 */
	public static boolean isNotNull(String val) {
		return !isNull(val);
	}

	/**
	 * Checks if value is an Integer
	 *
	 * 
	 */

	public static boolean isInteger(String val) {

		if (isNotNull(val)) {
			try {
				Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Checks if value is Long
	 *
	 *
	 */
	public static boolean isLong(String val) {
		if (isNotNull(val)) {
			try {
				Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Checks if value is valid Email ID
	 *
	 * 
	 */
	public static boolean isEmail(String val) {

		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (isNotNull(val)) {
			try {
				return val.matches(emailreg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Checks if value is Date
	 *
	 *
	 */
	public static boolean isDate(String val) {

		Date d = null;
		if (isNotNull(val)) {
			d = DataUtility.getDate(val);
		}
		return d != null;
	}

	/**
	 * Test above methods
	 *
	 *
	 */
	public static void main(String[] args) {

		System.out.println("Not Null 2" + isNotNull("ABC"));
		System.out.println("Not Null 3" + isNotNull(null));
		System.out.println("Not Null 4" + isNull("123"));

		System.out.println("Is Int " + isInteger(null));
		System.out.println("Is Int " + isInteger("ABC1"));
		System.out.println("Is Int " + isInteger("123"));
		System.out.println("Is Int " + isNotNull("123"));
	}

	public static boolean isName(String name) {
		System.out.println("-------  isname in DV "+name);


		String namereg = "^[^-\\s][\\p{L} .']+$";
		

		//String sname = name.trim();

		if (isNotNull(name) && name.matches(namereg)) {

			return true;
		} else {
			return false;
		}
		
	}
	public static boolean isValidAge(String val)
	{
		System.out.println("------- isvalidage in dv "+val);
		
		boolean pass = false;
		if (isDate(val)) {
			Date cdate = new Date();
			try {
				Date userdate = DataUtility.formatter.parse(val);
				int age = cdate.getYear()-userdate.getYear();
				System.out.println("final age  "+age);
				if(age>=18){
					pass=true;
				}
			} catch (ParseException e) {
				
			}
		}
		
		return pass;
	}
	
	/**
	 * Checks if value of Password is in between 8 and 12 characters
	 * 
	 * @param val
	 * @return true or false
	 */
	public static boolean isPasswordLength(String val) {

		if (isNotNull(val) && val.length() >= 8 && val.length() <= 12) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean isPassword(String pass) {
		
		System.out.println("validate isPassword");
		String passreg = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
		//String passreg="^[0-9a-zA-Z]{5}$";
		//String spass = pass.trim();
		//int checkIndex = spass.indexOf(" ");
                                //checkIndex==-1
		if (isNotNull(pass) && pass.matches(passreg) ) {
			System.out.println("true");
			return true;
		}

		else {
			return false;
		}

	
	}
	public static boolean isRollNo(String roll) { // my method created
		
		String rollreg = "[a-zA-Z]{2}[0-9]{4}";
		//String sroll = roll.trim();

		System.out.println("------ isRollNo "+roll);	
		if (DataValidator.isNotNull(roll)) {

			boolean check = roll.matches(rollreg);
			System.out.println("isRollNo"+check);
			return check;
		}

		else {

			return false;
		}
	}

	/**
	 * Checks if value of Mobile No is 10
	 * 
	 * @param val :value
	 * @return boolean
	 */
	public static boolean isPhoneLength(String val) {
	
		if (isNotNull(val) && val.length() == 10) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Checks if value is valid Phone No.
	 * 
	 * @param val :val
	 * @return boolean
	 */
	public static boolean isPhoneNo(String val) {
		System.out.println("------ isphoneno "+val);

		String phonereg = "^[6-9][0-9]{9}$";
//		String phonereg = "^[6-9]{10}$";

		if (isNotNull(val)) {
			try {
				return val.matches(phonereg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}

	}
}
