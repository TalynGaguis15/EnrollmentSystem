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
import service.StudentClass;
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
			@QueryParam("classsize") Long classsize ) {

		try {
			List<ClassInformation> users;
			
			if (StringUtils.isAllBlank(courseCode, courseName, schedule, location, instructor)) {
				users = studentclass.findAll();
			} else {
				users = studentclass.findByName(courseCode, courseName, schedule, location, instructor, units, classsize);
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
			ClassInformation user = studentclass.find(longId);
			return user;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(ClassInformation user) {

		try {
			studentclass.add(user);
			String result = "User saved : " + user.getCourseCode() + " " + user.getCourseName() + " " + user.getSchedule() + ""
					+ "" + user.getLocation() + "" + user.getInstructor() + "" + user.getUnits() + "" + user.getClassSize();
			return Response.status(201).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(ClassInformation user) {

		try {
			studentclass.upsert(user);
			String result = "User updated : " + user.getCourseCode() + " " + user.getCourseName() + " " + user.getSchedule() + ""
					+ "" + user.getLocation() + "" + user.getInstructor() + "" + user.getUnits() + "" + user.getClassSize();
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
			studentclass.delete(longId);
			String result = "User deleted";
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
}
