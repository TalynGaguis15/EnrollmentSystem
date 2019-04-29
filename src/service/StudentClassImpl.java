package service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import dbconnection.DBConnection;
import dbconnection.DBConnectionImpl;
import domain.ClassInformation;
import domain.InstructorInformation;
import domain.ScheduleInformation;
import domain.StudentInformation;

public class StudentClassImpl implements StudentClass {

	DBConnection connect;

	public StudentClassImpl() {
		this.connect = DBConnectionImpl.getInstance();
	}

	// ---------- Student Information ---------- //
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

	public List<ClassInformation> findStudentSchedule(Long studentID) {
		return connect.findStudentSchedule(studentID);
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

	// ---------- Student Information ---------- //

	public List<StudentInformation> findAllStudent() {
		return connect.findAllStudent();
	}

	public StudentInformation findStudent(Long studentID) {
		return connect.findStudent(studentID);
	}

	public List<StudentInformation> findByName(String firstName, String middleName, String lastName, String course) {
		return connect.findByName(firstName, middleName, lastName, course);
	}

	public List<StudentInformation> findScheduleStudents(Long classID) {
		return connect.findScheduleStudents(classID);
	}

	public void addStudent(StudentInformation student) {
		if (validate(student)) {
			connect.addStudent(student);
		} else {
			throw new IllegalArgumentException("Fields FirstName and LastName cannot be blank.");
		}
	}

	public void upsertStudent(StudentInformation student) {
		if (validate(student)) {
			if (student.getStudentID() != null && student.getStudentID() >= 0) {
				connect.updateStudent(student);
			} else {
				connect.addStudent(student);
			}
		} else {
			throw new IllegalArgumentException("Fields CourseCode and CourseName cannot be blank.");
		}
	}

	public void deleteStudent(Long studentID) {
		connect.deleteStudent(studentID);
	}

	private boolean validate(StudentInformation student) {
		return !StringUtils.isAnyBlank(student.getFirstName(), student.getLastName(), student.getCourse());

	}

	// ---------- Instructor Information ---------- //

	public List<InstructorInformation> findAllInstructor() {
		return connect.findAllInstructor();
	}

	public List<InstructorInformation> findByInstructorName(String firstName, String middleName, String lastName) {
		return connect.findByInstructorName(firstName, middleName, lastName);
	}

	public void addInstructor(InstructorInformation instructor) {
		if (validate(instructor)) {
			connect.addInstructor(instructor);
		} else {
			throw new IllegalArgumentException("Fields FirstName and LastName cannot be blank.");
		}
	}

	public void upsertInstructor(InstructorInformation instructor) {
		if (validate(instructor)) {
			if (instructor.getId() != null && instructor.getId() >= 0) {
				connect.updateInstructor(instructor);
			} else {
				connect.addInstructor(instructor);
			}
		} else {
			throw new IllegalArgumentException("Fields FirstName and LastName cannot be blank.");
		}
	}

	private boolean validate(InstructorInformation instructor) {
		return !StringUtils.isAnyBlank(instructor.getFirstName(), instructor.getLastName());

	}

	// ---------- Schedule Information ---------- //

	public void addSchedule(ScheduleInformation schedule) {
			connect.addSchedule(schedule);
	}

	public void deleteScheduleStudent(Long scheduleID, Long studentID) {
			connect.deleteScheduleStudent(scheduleID, studentID);
	}

}
