package dbconnection;

import java.util.List;

import domain.ClassInformation;

public interface DBConnection {

    public List<ClassInformation> findAll();
	
	public ClassInformation find(Long id);
	
	public List<ClassInformation> findByName(String courseCode, String courseName, String schedule, String location, String instructor);
	
	public void add(ClassInformation user);
	
	public void update(ClassInformation user);
	
	public void delete(Long id);


}
