package in.co.rays.project4.beans;

/**
 * course JavaBean encapsulates course attributes
 * @author Gagan
 *
 */
public class CourseBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private String courseName;
	private String description;
	private String duration;

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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return courseName;
	}
}
