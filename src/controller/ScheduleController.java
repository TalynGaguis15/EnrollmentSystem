package controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.ScheduleInformation;
import domain.ClassInformation;
import domain.InstructorInformation;
import domain.StudentInformation;
import service.StudentClassImpl;

@Path("/schedule")
public class ScheduleController {
private StudentClassImpl studentclass;

	public ScheduleController() {
		this.studentclass = new StudentClassImpl();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ClassInformation> getStudents(@QueryParam("studentid") String studentid) {

		try {
			List<ClassInformation> user;
			Long longId = Long.parseLong(studentid);
			user = studentclass.findStudentSchedule(longId);
			return user;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
	
	@GET
	@Path("{classID}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StudentInformation> getClass(@PathParam("classID") String classID) {

		try {
			List<StudentInformation> user;
			Long longId = Long.parseLong(classID);
			user = studentclass.findScheduleStudents(longId);
			return user;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSchedule(ScheduleInformation schedule) {

		try {
			studentclass.addSchedule(schedule);
			String result = "Schedule Saved : " + schedule.getId() + " " + schedule.getStudentID()+ " " + schedule.getClassID();
			return Response.status(201).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	@DELETE
	public Response deleteUser(@QueryParam("studentid") Long studentid,
							   @QueryParam("classid") Long classid) {

		try {
			studentclass.deleteScheduleStudent(studentid, classid);
			String result = "Schedule Deleted";
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
	

}
