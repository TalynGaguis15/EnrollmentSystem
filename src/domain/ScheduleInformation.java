package domain;

public class ScheduleInformation {
	
	private Long id, classID, studentID;
	
	public ScheduleInformation()
	{
		
	}
	public ScheduleInformation(Long classID, Long studentID) {
		this(null, classID, studentID);
	}

	public ScheduleInformation(Long id, Long classID, Long studentID) {
		this.id = id;
		this.classID = classID;
		this.studentID = studentID;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getClassID() {
		return classID;
	}
	public void setClassID(Long classID) {
		this.classID = classID;
	}
	public Long getStudentID() {
		return studentID;
	}
	public void setStudentID(Long studentID) {
		this.studentID = studentID;
	}
	
	

}
