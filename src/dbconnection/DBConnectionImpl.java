package dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hsqldb.jdbc.JDBCDataSource;

import domain.ClassInformation;
import domain.InstructorInformation;
import domain.ScheduleInformation;
import domain.StudentInformation;

public class DBConnectionImpl implements DBConnection {

	private JDBCDataSource data;
	public ResultSet result;
	private static DBConnectionImpl INSTANCE;

	static public DBConnectionImpl getInstance() {

		DBConnectionImpl instance;
		if (INSTANCE != null) {
			instance = INSTANCE;
		} else {
			instance = new DBConnectionImpl();
			INSTANCE = instance;
		}

		return instance;
	}

	private DBConnectionImpl() {
		connect();
	}

	private void connect() {
		data = new JDBCDataSource();
		data.setDatabase("jdbc:hsqldb:hsql://localhost/college");
		data.setUser("SA");
		data.setPassword("");
	}

	//    ---------- Class Information ----------     //

	@Override
	public List<ClassInformation> findAllClass() {

		return findByCourse(null, null, null, null, null);
	}

	@Override
	public ClassInformation findClass(Long id) {

		ClassInformation user = null;
		String sql = "SELECT * FROM CLASS where id = ?";
			try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setLong(1, id);
				ResultSet results = ps.executeQuery();

				if (results.next()) {
					user = new ClassInformation(Long.valueOf(results.getInt("id")),Long.valueOf(results.getInt("units")),
							Long.valueOf(results.getInt("classsize")), results.getString("coursecode"),
							results.getString("coursename"), results.getString("schedule"),
							results.getString("location"), results.getString("instructor"));
				
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

			return user;
	}

	@Override
	public List<ClassInformation> findByCourse(String courseCode, String courseName, String schedule, String location,
			String instructor) {
		List<ClassInformation> users = new ArrayList<>();

//		String sql = "SELECT * FROM CLASS c "
//		            + "INNER JOIN SCHEDULE s "
//					+ "ON c.id = s.classid " 
//		            + "WHERE s.studentid = (SELECT studentid FROM STUDENT WHERE studentid = 0)";
		
		String sql = "SELECT * FROM STUDENT WHERE courseCode LIKE ? AND courseName LIKE ?";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, createSearchValue(courseCode));
			ps.setString(2, createSearchValue(courseName));

			ResultSet results = ps.executeQuery();

			while (results.next()) {
				ClassInformation user = new ClassInformation(Long.valueOf(results.getInt("id")),Long.valueOf(results.getInt("units")),
						Long.valueOf(results.getInt("classsize")), results.getString("coursecode"),
						results.getString("coursename"), results.getString("schedule"),
						results.getString("location"), results.getString("instructor"));
				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return users;
	}

	private String createSearchValue(String string) {

		String value;

		if (StringUtils.isBlank(string)) {
			value = "%";
		} else {
			value = string;
		}

		return value;
	}

	@Override
	public void addClass(ClassInformation user) {

		String insertSql = "INSERT INTO CLASS (coursecode, coursename, schedule, location, instructor, units, classsize) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {

			ps.setString(1, user.getCourseCode());
			ps.setString(2, user.getCourseName());
			ps.setString(3, user.getSchedule());
			ps.setString(4, user.getLocation());
			ps.setString(5, user.getInstructor());
			ps.setLong(6, user.getUnits());
			ps.setLong(7, user.getClassSize());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateClass(ClassInformation user) {
		String updateSql = "UPDATE CLASS SET coursecode = ?, coursename = ?, schedule = ?, location = ?, instructor = ?"
				+ " WHERE id = ?";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setString(1, user.getCourseCode());
			ps.setString(2, user.getCourseName());
			ps.setString(3, user.getSchedule());
			ps.setString(4, user.getLocation());
			ps.setString(5, user.getInstructor());
			ps.setLong(6, user.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteClass(Long id) {
		String updateSql = "DELETE FROM CLASS WHERE id = ?";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setLong(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	// ---------- Student Information ----------//
	@Override
	public List<StudentInformation> findAllStudent() {
		return findByName(null, null,null, null);
	}
	@Override
	public StudentInformation findStudent(Long studentID) {
		StudentInformation student = null;

		if (studentID != null) {
			String sql = "SELECT * FROM STUDENT where studentid = ?";
			try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setInt(1, studentID.intValue());
				ResultSet results = ps.executeQuery();

				if (results.next()) {
					student = new StudentInformation(Long.valueOf(results.getInt("studentid")), results.getString("firstname"),
							results.getString("middlename"), results.getString("lastname"),
							results.getString("course"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return student;
	}
	@Override
	public List<StudentInformation> findByName(String firstName, String middleName, String lastName, String course) {
		List<StudentInformation> students = new ArrayList<>();

		//String sql = "SELECT * FROM STUDENT WHERE firstname LIKE ? AND lastname LIKE ?";
		
		String sql = "SELECT * FROM STUDENT s "
				+ " INNER JOIN SCHEDULE sc ON " 
				+ " s.studentid = sc.studentid" 
				+ " WHERE sc.classid = (SELECT id FROM CLASS WHERE id = 0)";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

//			ps.setString(1, createSearchValue(firstName));
//			ps.setString(2, createSearchValue(lastName));

			ResultSet results = ps.executeQuery();

			while (results.next()) {
				StudentInformation student = new StudentInformation(Long.valueOf(results.getInt("studentid")), Long.valueOf(results.getInt("totalunits")),
						results.getString("firstname"), results.getString("middlename"), results.getString("lastname"),
						results.getString("course"));
				students.add(student);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return students;
	}
	@Override
	public void addStudent(StudentInformation student) {
		String insertSql = "INSERT INTO STUDENT (firstname, middlename, lastname, course, totalunits) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {

			ps.setString(1, student.getFirstName());
			ps.setString(2, student.getMiddleName());
			ps.setString(3, student.getLastName());
			ps.setString(4, student.getCourse());
			ps.setLong(5, student.getUnits());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	 
	@Override
	public void updateStudent(StudentInformation student) {
		String updateSql = "UPDATE STUDENT SET firstname = ?, middlename = ?, lastname = ?, course = ?, totalunits = ? "
				+ " WHERE studentid = ?";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setString(1, student.getFirstName());
			ps.setString(2, student.getMiddleName());
			ps.setString(3, student.getLastName());
			ps.setString(4, student.getCourse());
			ps.setLong(5, student.getUnits());
			ps.setLong(6, student.getStudentID());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	@Override
	
	public void deleteStudent(Long studentID) {
		String updateSql = "DELETE FROM STUDENT WHERE studentid = ?";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setLong(1, studentID);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//  ---------- Instructor Information ----------     //
	@Override
	public List<InstructorInformation> findAllInstructor() {
		return findByInstructorName(null, null, null);
	}

	public List<InstructorInformation> findByInstructorName(String firstName, String middleName, String lastName){
		List<InstructorInformation> instructors = new ArrayList<>();

		String sql = "SELECT * FROM INSTRUCTOR WHERE firstname LIKE ? AND lastname LIKE ?";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, createSearchValue(firstName));
			ps.setString(2, createSearchValue(lastName));

			ResultSet results = ps.executeQuery();

			while (results.next()) {
				InstructorInformation instructor = new InstructorInformation(Long.valueOf(results.getInt("id")), results.getString("firstname"),
						results.getString("middlename"), results.getString("lastname"));
				instructors.add(instructor);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return instructors;
	}
	@Override
	public void addInstructor(InstructorInformation instructor) {
		String insertSql = "INSERT INTO INSTRUCTOR (firstname, middlename, lastname) VALUES (?, ?, ?)";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {

			ps.setString(1, instructor.getFirstName());
			ps.setString(2, instructor.getMiddleName());
			ps.setString(3, instructor.getLastName());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	@Override
	public void updateInstructor(InstructorInformation instructor) {
		String updateSql = "UPDATE INSTRUCTOR SET firstname = ?, middlename = ?, lastname = ? "
				+ " WHERE id = ?";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setString(1, instructor.getFirstName());
			ps.setString(2, instructor.getMiddleName());
			ps.setString(3, instructor.getLastName());
			ps.setLong(4, instructor.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	//  ---------- Schedule Information ----------     //


	public ScheduleInformation findByClassSchedule(Long studentID)
	{
		ScheduleInformation schedule  = null;
		ClassInformation classInfo = null;

		if (studentID != null) {
			String sql = "SELECT id, coursecode, coursename, schedule, instructor, units, classsize, location FROM CLASS  " + 
					"INNER JOIN SCHEDULE ON CLASS.id = SCHEDULE.classid " + 
					"WHERE SCHEDULE.studentid = (SELECT studentid FROM STUDENT WHERE studentid = ?)";
			
			
			try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setInt(1, studentID.intValue());
				
				ResultSet results = ps.executeQuery();

				if (results.next()) {
					classInfo = new ClassInformation(Long.valueOf(results.getInt("id")),Long.valueOf(results.getInt("units")),
							Long.valueOf(results.getInt("classsize")), results.getString("coursecode"),
							results.getString("coursename"), results.getString("schedule"),
							results.getString("location"), results.getString("instructor"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return schedule;
	}

	public ScheduleInformation findByStudent(Long classID)
	{
		ScheduleInformation schedule  = null;
		StudentInformation student = null;

		if (classID != null) {
			String sql = "Select studentid,  firstname, middlename, lastname, course, totalunits From STUDENT s "
					+ " Inner Join SCHEDULE sc ON " + " s.studentid = sc.studentid" + 
		              " Where sc.classid = (Select id From CLASS Where id = ?)";
			
			try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setInt(1, classID.intValue());
				
				ResultSet results = ps.executeQuery();

				if (results.next()) {
					student = new StudentInformation(Long.valueOf(results.getInt("studentid")), results.getString("firstname"),
							results.getString("middlename"), results.getString("lastname"),
							results.getString("course"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return schedule;
	}
	

	public void addSchedule(ScheduleInformation schedule)
	{
		
	}

	public void deleteSchedule(ScheduleInformation schedule)
	{
		
	}
}
