package in.co.rays.project4.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * user JavaBean encapsulates user attributes
 * @author Gagan
 *
 */
public class UserBean extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	
	public static final String ACTIVE = "Active";
	public static final String IN_ACTIVE = "Inactive";
	
	private String firstName;
	private String lastName;
	private String login;
	private String pass;
	private Date dob;
	private String mobileNo;
	private long roleId;
	private int unSuccessfulLogin;
	private String gender;
	private Timestamp lastLogin;
	private String lock;
	private String registeredIp;
	private String lastLoginIp;

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public int getUnSuccessfulLogin() {
		return unSuccessfulLogin;
	}
	public void setUnSuccessfulLogin(int unSuccessfulLogin) {
		this.unSuccessfulLogin = unSuccessfulLogin;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getLock() {
		return lock;
	}
	public void setLock(String lock) {
		this.lock = lock;
	}
	public String getRegisteredIp() {
		return registeredIp;
	}
	public void setRegisteredIp(String registeredIp) {
		this.registeredIp = registeredIp;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	public String getKey() {
		return id+"";
	}

	public String getValue() {
		return firstName;
	}
}
