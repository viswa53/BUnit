package com.bunit.resource;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bunit.service.BUnitService;
import com.bunit.util.BuintUtil;
import com.bunit.xml.to.Action;
import com.bunit.xml.to.Scenario;
import com.google.gson.Gson;

@Controller
@Path("/bunit")
public class BuintUiResource {

	@Autowired
	private BuintUtil buintUtil;

	@Autowired
	private BUnitService bUnitService;

	@GET
	@Produces("application/json")
	@Path("/new_scenario")
	public List<?> createNewScenario() throws Exception {

		return bUnitService.createNewScenario();
	}

	@GET
	@Produces("application/json")
	@Path("/get_actions")
	public Response getActions() throws Exception {

		Gson gson = new Gson();
		String jsonString = gson.toJson(bUnitService.getActions());

		return Response.ok().entity(jsonString).build();
	}

	@GET
	@Produces("application/json")
	@Path("/open_scenario/{scenario_name}")
	public Scenario openScenario(@PathParam("scenario_name") String  scenarioName) throws Exception {

		return bUnitService.openScenario(scenarioName);
	}
	
	@GET
	@Produces("application/json")
	@Path("/get_scenario")
	public List<String> getScenario() throws Exception {

		return bUnitService.getScenario();
	}

	@POST
	@Produces("application/json")
	@Path("/drag/{scenario_id}")
	public Scenario dragScenario(@PathParam("scenario_id") String scenarioId, Action action) 
			throws Exception {

		return bUnitService.dragScenario(scenarioId, action);
	}
	
	@POST
	@Produces("application/json")
	@Path("/delete/{scenario_id}")
	public Scenario deleteScenario(@PathParam("scenario_id") String scenarioId, Action action) 
			throws Exception {

		return bUnitService.deleteScenario(scenarioId, action);
	}

	@POST
	@Produces("application/json")
	@Path("/edit_scenario/input/{scenario_id}")
	public Scenario editScenarioInput(@PathParam("scenario_id") String scenarioId, Action action) 
			throws Exception{

		return bUnitService.editScenarioInput(scenarioId, action);
	}

	@POST
	@Produces("application/json")
	@Path("/edit_scenario/output/{scenario_id}")
	public Scenario editScenarioOutput(@PathParam("scenario_id") String scenarioId, Action action) 
			throws Exception{

		return bUnitService.editScenarioInput(scenarioId, action);
	}
}