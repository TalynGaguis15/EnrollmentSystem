package dbconnection;

import java.util.List;

import domain.ClassInformation;
import domain.StudentInformation;
import domain.InstructorInformation;
import domain.ScheduleInformation;

public interface DBConnection {

	// ----------- Class Information ---------- //

	public List<ClassInformation> findAllClass();

	public ClassInformation findClass(Long id);

	public List<ClassInformation> findByCourse(String courseCode, String courseName, String schedule, String location,
			String instructor);
	
	public List<ClassInformation> findStudentSchedule(Long studentID);

	public void addClass(ClassInformation user);

	public void updateClass(ClassInformation user);

	public void deleteClass(Long id);

	// ---------- Student Information ------------ //

	public List<StudentInformation> findAllStudent();

	public StudentInformation findStudent(Long studentID);

	public List<StudentInformation> findByName(String firstName, String middleName, String lastName, String course);
	
	public List<StudentInformation> findScheduleStudents(Long classID);

	public void addStudent(StudentInformation student);

	public void updateStudent(StudentInformation student);

	public void deleteStudent(Long studentID);

	//  ---------- Instructor Information ------------     //
	
	public List<InstructorInformation> findAllInstructor();

	public List<InstructorInformation> findByInstructorName(String firstName, String middleName, String lastName);

	public void addInstructor(InstructorInformation instructor);

	public void updateInstructor(InstructorInformation instructor);
	
	//  ---------- Student Schedule Information ------------     //
	


	public void addSchedule(ScheduleInformation schedule);

	public void deleteSchedule(ScheduleInformation schedule);
	

}
