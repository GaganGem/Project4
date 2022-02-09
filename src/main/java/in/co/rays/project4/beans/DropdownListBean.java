package in.co.rays.project4.beans;

/**
 * DropdownList interface is implemented by Beans those are used to create 
 * drop down list on HTML pages
 * @author Gagan
 *
 */
public interface DropdownListBean {
	
	/**
	 * return key of list element
	 * @return key
	 */
	public String getKey();
	
	
	/**
	 * display list of key element
	 * @return value
	 */
	public String getValue();
}
