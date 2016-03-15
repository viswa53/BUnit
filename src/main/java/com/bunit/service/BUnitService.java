package com.bunit.service;

import java.util.List;

import com.bunit.response.to.ActionResponse;
import com.bunit.response.to.EditedOutputList;
import com.bunit.response.to.InputFlistResponse;
import com.bunit.response.to.ScenarioResponse;

public interface BUnitService {

	public ActionResponse getActions() throws Exception;

	public List<?> createNewScenario() throws Exception;

	public String editScenarioOutput(String actionId, String scenarioId, EditedOutputList editedOutputList) throws Exception;

	public ScenarioResponse dragScenario(String actionId, String scenarioId) throws Exception;

	public ScenarioResponse deleteScenario(String actionId, String scenarioId) throws Exception;

	public ScenarioResponse openScenario(String scenarioName) throws Exception;

	public List<String> getScenario();
	
	public List<InputFlistResponse> getInputFList(String actionId, String scenarioId) throws Exception;
	
	public List<InputFlistResponse> getOutputFList(String actionId, String scenarioId) throws Exception;

	public List<String> openScenarioInfo(String scenarioName) throws Exception;
	
	public List<String> getLogs(String scenarioId);
	
	public String editScenarioInput(String actionId, String scenarioId, EditedOutputList editedOutputList) throws Exception;
	
	public void runScenario(String scenarioId) throws Exception;
}
