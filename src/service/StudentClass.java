package service;

import java.util.List;

import domain.ClassInformation;

public interface StudentClass {
	public List<ClassInformation> findAll();
	
	public ClassInformation find(Long id);
	
	public List<ClassInformation> findByName(String courseCode, String courseName, String schedule, String location, String instructor, 
			Long units, Long classsize);
	
	public void add(ClassInformation user);
	
	public void upsert(ClassInformation user);
	
	public void delete(Long id);
}
