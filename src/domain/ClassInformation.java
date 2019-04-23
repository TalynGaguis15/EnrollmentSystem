package domain;

public class ClassInformation {

	Long id, units, classSize;
	private String courseCode, courseName, schedule, location, instructor;
	
	

	public ClassInformation() {
		
	}

	public ClassInformation(String courseCode, String courseName, String schedule, String location,
			String instructor, Long units, Long classSize) {
		this(null, courseCode, courseName, schedule, location, instructor, units, classSize);
	}

	public ClassInformation(Long id, String courseCode, String courseName, String schedule, String location,
			String instructor, Long units, Long classSize) {
		this.id = id;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.schedule = schedule;
		this.location = location;
		this.instructor = instructor;
		this.units = units;
		this.classSize = classSize;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUnits() {
		return units;
	}

	public void setUnits(Long units) {
		this.units = units;
	}

	public Long getClassSize() {
		return classSize;
	}

	public void setClassSize(Long classSize) {
		this.classSize = classSize;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	

}
