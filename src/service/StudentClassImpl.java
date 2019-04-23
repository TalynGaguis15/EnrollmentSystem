package service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import dbconnection.DBConnection;
import dbconnection.DBConnectionImpl;
import domain.ClassInformation;

public class StudentClassImpl implements StudentClass {
	
	 DBConnection connect;

	public StudentClassImpl() {
		this.connect = DBConnectionImpl.getInstance();
		//this.userDao = UserHashMapDaoImpl.getInstance();
	}
	
	@Override
	public List<ClassInformation> findAll() {
		return connect.findAll();
	}

	@Override
	public ClassInformation find(Long id) {
		return connect.find(id);
	}

	@Override
	public List<ClassInformation> findByName(String courseCode, String courseName, String schedule, String location, String instructor, 
			Long units, Long classsize) {
		return connect.findByName(courseCode, courseName, schedule, location, instructor);
	}

	@Override
	public void add(ClassInformation user) {
		if (validate(user)) {
			connect.add(user);
		} else {
			throw new IllegalArgumentException("Fields CourseCode and CourseName cannot be blank.");
		}
	}

	@Override
	public void upsert(ClassInformation user) {
		if (validate(user)) {
			if(user.getId() != null && user.getId() >= 0) {
				connect.update(user);
			} else {
				connect.add(user);
			}
		} else {
			throw new IllegalArgumentException("Fields CourseCode and CourseName cannot be blank.");
		}
	}

	@Override
	public void delete(Long id) {
		connect.delete(id);
	}
	
	private boolean validate(ClassInformation user) {
		return !StringUtils.isAnyBlank(user.getCourseCode(), user.getCourseName(), user.getSchedule(), 
				user.getInstructor(), user.getLocation() );
	}

	

}
