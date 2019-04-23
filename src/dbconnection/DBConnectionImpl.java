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
		data.setDatabase("jdbc:hsqldb:mem:EnrollmentSystem");
		data.setUser("username");
		data.setPassword("password");

		createTable();
		insertInitUsers();

	}

	private void createTable() {
		String createSql = "CREATE TABLE CollegeEnrollment " + "(id INTEGER IDENTITY PRIMARY KEY, " + " coursecode VARCHAR(255), "
				+ " coursename VARCHAR(255), " + " schedule VARCHAR(255), " + " location VARCHAR(255) , " + " instructor VARCHAR(255),"
						+ " units INTEGER, " + " classsize INTEGER)";

		try (Connection conn = data.getConnection(); Statement stmt = conn.createStatement()) {

			stmt.executeUpdate(createSql);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void insertInitUsers() {

		add(new ClassInformation("English101","English", "MWF","ROOM101","Mr. Casinto", null, null));
		add(new ClassInformation("Filipino101","Filipino", "TTh","ROOM102","Mr. Park", null, null));
		
	}

	@Override
	public List<ClassInformation> findAll() {

		return findByName(null, null, null, null, null);
	}

	@Override
	public ClassInformation find(Long id) {

		ClassInformation user = null;

		if (id != null) {
			String sql = "SELECT * FROM CollegeEnrollment where id = ?";
			try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setInt(1, id.intValue());
				ResultSet results = ps.executeQuery();

				if (results.next()) {
					user = new ClassInformation(Long.valueOf(results.getInt("id")), results.getString("coursecode"),
							results.getString("coursename"),results.getString("schedule"),results.getString("location"),
							results.getString("instructor"),Long.valueOf(results.getInt("units")),Long.valueOf(results.getInt("classsize")));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return user;
	}

	@Override
	public List<ClassInformation> findByName(String courseCode, String courseName, String schedule, String location, 
			String instructor) {
		List<ClassInformation> users = new ArrayList<>();

		String sql = "SELECT * FROM CollegeEnrollment WHERE coursecode LIKE ? AND coursename LIKE ?";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, createSearchValue(courseCode));
			ps.setString(2, createSearchValue(courseName));
			
			ResultSet results = ps.executeQuery();

			while (results.next()) {
				ClassInformation user = new ClassInformation(Long.valueOf(results.getInt("id")), results.getString("coursecode"),
						results.getString("coursename"),results.getString("schedule"),results.getString("location"),
						results.getString("instructor"),Long.valueOf(results.getInt("units")),Long.valueOf(results.getInt("classsize")));
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
	public void add(ClassInformation user) {
		
		String insertSql = "INSERT INTO CollegeEnrollment (coursecode, coursename, schedule, location, instructor, units, classsize) VALUES (?, ?, ?, ?, ?, ?, ?)";

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
	public void update(ClassInformation user) {
		String updateSql = "UPDATE CollegeEnrollment SET coursecode = ?, coursename = ?, schedule = ?, location = ?, instructor = ?, units = ?, classsize = ? "
							+ " WHERE id = ?";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setString(1, user.getCourseCode());
			ps.setString(2, user.getCourseName());
			ps.setString(3, user.getSchedule());
			ps.setString(4, user.getLocation());
			ps.setString(5, user.getInstructor());
			ps.setLong(6, user.getUnits());
			ps.setLong(7, user.getClassSize());
			ps.setLong(8, user.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Long id) {
		String updateSql = "DELETE FROM CollegeEnrollment WHERE id = ?";

		try (Connection conn = data.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setLong(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	

}
