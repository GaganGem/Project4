package in.co.rays.project4.beans;

/**
 * role JavaBean encapsulates role attributes
 * @author Gagan
 *
 */
public class RoleBean extends BaseBean{
	
	private static final long serialVersionUID = 1L;

	public static final long ADMIN=1;
	public static final long STUDENT=2;
	public static final long COLLEGE_SCHOOL=3;
	public static final long KIOSK=4;
	
	private String name;
	private String description;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKey() {
		return id+"";
	}
	public String getValue() {
		return name;
	}

}
