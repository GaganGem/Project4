package in.co.rays.project4.beans;

/**
 * subject JavaBean encapsulates subject attributes
 * @author Gagan
 *
 */
public class SubjectBean extends BaseBean{

	private static final long serialVersionUID = 1L;

	private String subName;
	private long courseId;
	private String courseName;
	private String description;
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	
	public String getValue() {
		// TODO Auto-generated method stub
		return subName;
	}

}
