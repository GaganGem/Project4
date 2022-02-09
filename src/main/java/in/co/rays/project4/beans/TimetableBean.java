package in.co.rays.project4.beans;

import java.util.Date;

/**
 * imeTable JavaBean encapsulates TimeTable attributes
 * @author Gagan
 *
 */
public class TimetableBean extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	
	private long courseId;
	private String courseName;
	private long subId;
	private String subName;
	private String sem;
	private Date examDate;
	private String examTime;
	
	
	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public String getSem() {
		return sem;
	}

	public void setSem(String sem) {
		this.sem = sem;
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

	public long getSubId() {
		return subId;
	}

	public void setSubId(long subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	
	public String getValue() {
		// TODO Auto-generated method stub
		return courseName;
	}
}
