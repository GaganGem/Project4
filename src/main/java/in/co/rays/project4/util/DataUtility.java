package in.co.rays.project4.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * data Uility class to formate data
 * @author Gagan
 *
 */
public class DataUtility {
	
	/**
	 * Application time data formate
	 */
	public static final String APP_DATE_FORMAT = "dd-MM-yyyy";
	public static final String APP_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

	/**
	 * Applicaton time data formate
	 */
	protected static SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT);
	protected static SimpleDateFormat timeFormatter;

	/**
	 * getString(String s) Trims and trailing and leading spaces of a String
	 * 
	 * @param val
	 * @return val
	 */
	public static String getString(String val) {
		return (val==null)?"" : val.trim();
	}

	/**
	 * Converts and Object to String
	 * 
	 * @param val
	 *            :value
	 * @return String
	 */
	public static String getStringData(Object val) {
		if (val != null) {
			return val.toString();
		}
		return "";
	}
	
	public static void main(String[] args) {
		System.out.println(getStringData(null));
	}

	/**
	 * 
	 * Converts String InTo Integer
	 * 
	 * @param val
	 *            :value
	 * @return int
	 */
	public static int getInt(String val) {
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * 
	 * Converts String InTo Long
	 * 
	 * @param val
	 *            :value
	 * @return Long
	 */
	public static long getLong(String val) {
		try {
			return Long.parseLong(val);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Convert String in to Date
	 * 
	 * @param val
	 *            :value
	 * @return Date
	 */
	public static Date getDate(String val) {
		try {
			return formatter.parse(val);
		} catch (Exception e) {
			return null;
		}
	}

	 /**
	 * convert string to date
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {
		try {
			return formatter.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	 /**
	 * convert date and time
	 * @param date * 	
	 *  * @param day
	 * @return
	 */
	public static Date getDate(Date date, int day) {
		return null;
	}

	/**
	 * convert timestamp to string
	 * @param val
	 * @return timestamp
	 */
	public static Timestamp getTimestamp(String val) {
		try {
			return new Timestamp(timeFormatter.parse(val).getTime());
		} catch (Exception e) {
			return null;
		}
	}
	
	/** 
	 * convert timestamp in to long
	 * @param l
	 * @return timestamp
	 */
	public static Timestamp getTimestamp(long l) {

        Timestamp timeStamp = null;
        try {
            timeStamp = new Timestamp(l);
        } catch (Exception e) {
            return null;
        }
        return timeStamp;
    }

	  /**
			 * convert timestamp in to string
			 * @return timestamp
			 */
    public static Timestamp getCurrentTimestamp() {
        Timestamp timeStamp = null;
        try {
            timeStamp = new Timestamp(new Date().getTime());
        } catch (Exception e) {
        }
        return timeStamp;

    }

    /**
	 * convert timestamp timestamp to long
	 * @param tm
	 * @return
	 */
    public static long getTime(Timestamp tm) {
        try {
            return tm.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

}
