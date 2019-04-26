package controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import domain.ClassInformation;
import service.StudentClassImpl;

@Path("/class")
public class ClassController {
private StudentClassImpl studentclass;
	
	public ClassController() {
		this.studentclass = new StudentClassImpl();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ClassInformation> getUsers(
			@QueryParam("courseCode") String courseCode, 
			@QueryParam("courseName") String courseName,
			@QueryParam("schedule") String schedule, 
			@QueryParam("location") String location,
			@QueryParam("instructor") String instructor,
			@QueryParam("units") Long units,
			@QueryParam("classSize") String classSize) {

		try {
			List<ClassInformation> users;
			
			if (StringUtils.isAllBlank(courseCode, courseName, schedule, location, instructor)) {
				users = studentclass.findAllClass();
			} else {
				users = studentclass.findByCourse(courseCode, courseName, schedule, location, instructor);
			}
						
			return users;
			
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ClassInformation getUser(@PathParam("id") String id) {

		try {
			Long longId = Long.parseLong(id);
			ClassInformation user = studentclass.findClass(longId);
			return user;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(ClassInformation user) {

		try {
			studentclass.addClass(user);
			String result = "User saved : " + user.getCourseCode() + " " +  user.getUnits() + " " + user.getClassSize()+ " " +
			user.getCourseName() + " " + user.getSchedule() + ""
			+ "" + user.getLocation() + "" + user.getInstructor() ;
			return Response.status(201).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(ClassInformation user) {

		try {
			studentclass.upsertClass(user);
			String result = "User updated : " + user.getCourseCode() + " " + user.getCourseName() + " " + user.getSchedule() + ""
					+ "" + user.getLocation() + "" + user.getInstructor() + " " + user.getUnits() + " " + user.getClassSize();
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}

	@DELETE
	@Path("{id}")
	public Response deleteUser(@PathParam("id") String id) {

		try {
			Long longId = Long.parseLong(id);
			studentclass.deleteClass(longId);
			String result = "User deleted";
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
}
