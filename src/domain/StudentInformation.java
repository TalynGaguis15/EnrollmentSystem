package domain;

public class StudentInformation {
	
	Long studentID;
	private String firstName, middleName, lastName, Course;
	public StudentInformation()
	{
		
	}
	public StudentInformation(String firstName, String middleName, String lastName, String course)
	{
		this(null, firstName, middleName,  lastName, course );
	}
	
	public StudentInformation(Long studentID, String firstName, String middleName, String lastName, String course) {
		this.studentID = studentID;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		Course = course;
	}
	public Long getStudentID() {
		return studentID;
	}
	public void setStudentID(Long studentID) {
		this.studentID = studentID;
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
	
	
	
	

}
