package dbconnection;

import java.util.List;

import domain.ClassInformation;
import domain.StudentInformation;

public interface DBConnection {

    // Class Information
	
	public List<ClassInformation> findAllClass();
	
	public ClassInformation findClass(Long id);
	
	public List<ClassInformation> findByCourse(String courseCode, String courseName, String schedule, String location, String instructor);
	
	public void addClass(ClassInformation user);
	
	public void updateClass(ClassInformation user);
	
	public void deleteClass(Long id);
	
	//Student Information
	
	public List<StudentInformation> findAllStudent();
	
	public StudentInformation findStudent(Long studentID);
	
	public List<StudentInformation> findByName(String firstName, String middleName, String lastName, String course);
	
	public void addStudent(StudentInformation student);
	
	public void updateStudent(StudentInformation student);
	
	public void deleteStudent(Long studentID);


}
