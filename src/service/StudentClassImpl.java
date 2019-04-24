package service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import dbconnection.DBConnection;
import dbconnection.DBConnectionImpl;
import domain.ClassInformation;
import domain.StudentInformation;

public class StudentClassImpl implements StudentClass {

	DBConnection connect;

	public StudentClassImpl() {
		this.connect = DBConnectionImpl.getInstance();
	}

	//	---------- Student Information ----------   //
	@Override
	public List<ClassInformation> findAllClass() {
		return connect.findAllClass();
	}

	@Override
	public ClassInformation findClass(Long id) {
		return connect.findClass(id);
	}

	@Override
	public List<ClassInformation> findByCourse(String courseCode, String courseName, String schedule, String location,
			String instructor) {
		return connect.findByCourse(courseCode, courseName, schedule, location, instructor);
	}

	@Override
	public void addClass(ClassInformation user) {
		if (validate(user)) {
			connect.addClass(user);
		} else {
			throw new IllegalArgumentException("Fields CourseCode and CourseName cannot be blank.");
		}
	}

	@Override
	public void upsertClass(ClassInformation user) {
		if (validate(user)) {
			if (user.getId() != null && user.getId() >= 0) {
				connect.updateClass(user);
			} else {
				connect.addClass(user);
			}
		} else {
			throw new IllegalArgumentException("Fields CourseCode and CourseName cannot be blank.");
		}
	}

	@Override
	public void deleteClass(Long id) {
		connect.deleteClass(id);
	}

	private boolean validate(ClassInformation user) {
		return !StringUtils.isAnyBlank(user.getCourseCode(), user.getCourseName(), user.getSchedule(),
				user.getInstructor(), user.getLocation());

	}

  //	---------- Student Information ----------   //

	public List<StudentInformation> findAllStudent() {
			return connect.findAllStudent();
	}

	public StudentInformation findStudent(Long studentID) {
			return connect.findStudent(studentID);
	}

	public List<StudentInformation> findByName(String firstName, String middleName, String lastName, String course) {
			return connect.findByName(firstName, middleName, lastName, course);
	}

	public void addStudent(StudentInformation student) {

	}

	public void upsertStudent(StudentInformation user) {
		
	}

	public void deleteStudent(Long studentID) {
		
	}
	
	private boolean validate(StudentInformation student) {
		return !StringUtils.isAnyBlank(student.getFirstName(), student.getMiddleName(), student.getLastName(),
				student.getCourse());

	}

}
