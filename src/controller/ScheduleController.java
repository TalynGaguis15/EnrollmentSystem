package controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import domain.ScheduleInformation;
import service.StudentClassImpl;

@Path("/schedule")
public class ScheduleController {
private StudentClassImpl studentclass;

	public ScheduleController() {
		this.studentclass = new StudentClassImpl();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ScheduleInformation getUser(@PathParam("studentID") String studentID) {

		try {
			Long longId = Long.parseLong(studentID);
			ScheduleInformation schedule = studentclass.findByClassSchedule(longId);
			return schedule;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}

}
