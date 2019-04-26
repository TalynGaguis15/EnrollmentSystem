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

import domain.StudentInformation;
import service.StudentClassImpl;

@Path("/student")
public class StudentController {
	private StudentClassImpl studentclass;	
	
	public StudentController()
	{
		this.studentclass = new StudentClassImpl();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<StudentInformation> getStudents(
			@QueryParam("firstName") String firstName, 
			@QueryParam("middleName") String middleName,
			@QueryParam("lastName") String lastName, 
			@QueryParam("course") String course,
			@QueryParam("units") String units){

		try {
			List<StudentInformation> student;
			
			if (StringUtils.isAllBlank(firstName, middleName, lastName, course)) {
				student = studentclass.findAllStudent();
			} else {
				student = studentclass.findByName(firstName, middleName, lastName, course);
			}
						
			return student;
			
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public StudentInformation getStudent(@PathParam("studentID") String studentID) {

		try {
			Long longId = Long.parseLong(studentID);
			StudentInformation student = studentclass.findStudent(longId);
			return student;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addStudent(StudentInformation student) {

		try {
			studentclass.addStudent(student);
			String result = "User saved : " + student.getFirstName() + " " + student.getMiddleName() + " " + student.getLastName() 
					+ "" + student.getCourse()+ " " + student.getUnits();
			return Response.status(201).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateStudent(StudentInformation student) {

		try {
			studentclass.upsertStudent(student);
			String result = "User updated : " + student.getFirstName()+ " " + student.getMiddleName() + " " + student.getLastName() + ""
					+ "" + student.getCourse()+ " " + student.getUnits();
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}

	@DELETE
	@Path("{id}")
	public Response deleteStudent(@PathParam("studentid") String id) {

		try {
			Long longId = Long.parseLong(id);
			studentclass.deleteStudent(longId);
			String result = "Student deleted";
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
	

}
