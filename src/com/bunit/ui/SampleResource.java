package com.bunit.ui;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sample")
public class SampleResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public MyJaxbBean getMyBean() {
		return new MyJaxbBean("Agamemnon", 32);
	}
	
	@GET
	@Produces("application/json")
	@Path("/hi")
	public String getHello() {
		return "hi";
	}
}
