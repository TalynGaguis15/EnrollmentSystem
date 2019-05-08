package domain;

public class StudentInformation {

	Long studentID, units;
	private String firstName, middleName, lastName, Course, date;

	public StudentInformation() {

	}

	public StudentInformation(Long units, String firstName, String middleName, String lastName, String course, String date) {
		this(null, units, firstName, middleName, lastName, course, date);
	}

	public StudentInformation(Long studentID, Long units, String firstName, String middleName, String lastName,
			String course, String date) {
		this.studentID = studentID;
		this.units = units;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		Course = course;
		this.date = date;
	}

	public Long getStudentID() {
		return studentID;
	}

	public void setStudentID(Long studentID) {
		this.studentID = studentID;
	}

	public Long getUnits() {
		return units;
	}

	public void setUnits(Long units) {
		this.units = units;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCourse() {
		return Course;
	}

	public void setCourse(String course) {
		Course = course;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	


}
