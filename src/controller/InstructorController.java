package controller;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import domain.InstructorInformation;
import service.StudentClassImpl;

@Path("/instructor")
public class InstructorController {
	private StudentClassImpl studentclass;
	
	public InstructorController(){
		this.studentclass = new StudentClassImpl();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<InstructorInformation> getUsers(
			@QueryParam("firstName") String firstName, 
			@QueryParam("middleName") String middleName,
			@QueryParam("lastName") String lastName) {

		try {
			List<InstructorInformation> instructors;
			
			if (StringUtils.isAllBlank(firstName, lastName)) {
				instructors = studentclass.findAllInstructor();
			} else {
				instructors = studentclass.findByInstructorName(firstName, middleName, lastName);
			}
						
			return instructors;
			
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addInstructor(InstructorInformation instructor) {

		try {
			studentclass.addInstructor(instructor);
			String result = "User saved : " + instructor.getFirstName() + " " + instructor.getMiddleName() + " " + instructor.getLastName();
			return Response.status(201).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateInstructor(InstructorInformation instructor) {

		try {
			studentclass.upsertInstructor(instructor);
			String result = "User updated : " + instructor.getFirstName()+ " " + instructor.getMiddleName() + " " + instructor.getLastName();
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}



}
