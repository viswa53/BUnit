package com.bunit.resource;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.bunit.response.to.EditedOutputList;
import com.bunit.response.to.InputFlistResponse;
import com.bunit.response.to.ScenarioInfo;
import com.bunit.response.to.ScenarioResponse;
import com.bunit.service.BUnitService;
import com.google.gson.Gson;

@Controller
@Path("/bunit")
public class BuintUiResource {
	
	public static final Logger LOGGER = Logger.getLogger(BuintUiResource.class);

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
	public ScenarioResponse openScenario(@PathParam("scenario_name") String  scenarioName) throws Exception {
		if(scenarioName == null || scenarioName.isEmpty()) {
			LOGGER.warn("No Scenario found");
			return null;
		}

		return bUnitService.openScenario(scenarioName);
	}
	
	@GET
	@Produces("application/json")
	@Path("/open_scenario_info/{scenario_name}")
	public List<String> openScenarioInfo(@PathParam("scenario_name") String  scenarioName) throws Exception {
		if(scenarioName == null || scenarioName.isEmpty()) {
			LOGGER.warn("No Scenario found");
			return null;
		}

		return bUnitService.openScenarioInfo(scenarioName);
	}
	
	@GET
	@Produces("application/json")
	@Path("/get_scenario")
	public List<String> getScenario() throws Exception {

		return bUnitService.getScenario();
	}

	@GET
	@Produces("application/json")
	@Path("/drag/{action_id}/{scenario_id}")
	public ScenarioResponse dragScenario(@PathParam("action_id") String actionId, @PathParam("scenario_id") String scenarioId) 
			throws Exception {
		if(actionId == null || actionId.isEmpty() || scenarioId == null || scenarioId.isEmpty()) {
			LOGGER.warn("No actionId or senarioId is null");
			return null;
		}

		return bUnitService.dragScenario(actionId, scenarioId);
	}
	
	@GET
	@Produces("application/json")
	@Path("/delete/{action_id}/{scenario_id}")
	public ScenarioResponse deleteScenario(@PathParam("action_id") String actionId, @PathParam("scenario_id") String scenarioId) 
			throws Exception {

		return bUnitService.deleteScenario(actionId, scenarioId);
	}
	
	@GET
	@Produces("application/json")
	@Path("/get_input_flist/{action_id}/{scenario_id}")
	public List<InputFlistResponse> getInputFList(@PathParam("action_id") String actionId, 
			@PathParam("scenario_id") String scenarioId) 
			throws Exception {
		
		return bUnitService.getInputFList(actionId, scenarioId);
	}
	
	@GET
	@Produces("application/json")
	@Path("/get_output_flist/{action_id}/{scenario_id}")
	public List<InputFlistResponse> getOutputFList(@PathParam("action_id") String actionId,
			@PathParam("scenario_id") String scenarioId) 
			throws Exception {
		
		return bUnitService.getOutputFList(actionId, scenarioId);
	}

	@POST
	@Produces("application/json")
	@Path("/edit_scenario/output/{action_id}/{scenario_id}")
	public String editScenarioInput(@PathParam("action_id") String actionId, @PathParam("scenario_id") String scenarioId, 
			@RequestBody EditedOutputList editedOutputList) 
			throws Exception{

		return bUnitService.editScenarioOutput(actionId, scenarioId, editedOutputList);
	}

	@POST
	@Produces("application/json")
	@Path("/edit_scenario/input/{action_id}/{scenario_id}")
	public String editScenarioOutput(@PathParam("action_id") String actionId, @PathParam("scenario_id") String scenarioId, 
			@RequestBody EditedOutputList editedOutputList) 
			throws Exception{

		return bUnitService.editScenarioInput(actionId, scenarioId, editedOutputList);
	}
	
	@GET
	@Produces("application/json")
	@Path("/empty")
	public ScenarioResponse emptyResponse() 
			throws Exception{
		ScenarioResponse scenarioResponse = new ScenarioResponse();
		scenarioResponse.setTotal(7);
		List<ScenarioInfo> rows = new ArrayList<ScenarioInfo>();
		// <a href="#" class="easyui-linkbutton" iconCls="icon-cancel">Cancel</a>
		ScenarioInfo scenarioInfo =new ScenarioInfo();
		scenarioInfo.setActionID("<a href='#' class='easyui-linkbutton' iconCls='icon-cancel'></a>");
		rows.add(scenarioInfo);
		rows.add(new ScenarioInfo());
		rows.add(new ScenarioInfo());
		rows.add(new ScenarioInfo());
		rows.add(new ScenarioInfo());
		rows.add(new ScenarioInfo());
		rows.add(new ScenarioInfo());
		rows.add(new ScenarioInfo());
		rows.add(new ScenarioInfo());
		rows.add(new ScenarioInfo());
		
		scenarioResponse.setRows(rows);

		return scenarioResponse;
	}
	
	@GET
	@Produces("application/json")
	@Path("/get_logs/{scenario_id}")
	public List<String> editLogFiles(@PathParam("scenario_id")String scenarioId) 
			throws Exception{

		return bUnitService.getLogs(scenarioId);
	}
	
	@GET
	@Produces("application/json")
	@Path("/run_scenario/{scenario_id}")
	public String runScenario(@PathParam("scenario_id")String scenarioId) 
			throws Exception{
		
		bUnitService.runScenario(scenarioId);
		
		return "Successfully ran scenario";
	}
}