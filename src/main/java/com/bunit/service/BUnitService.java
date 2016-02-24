package com.bunit.service;

import java.util.List;

import com.bunit.response.to.ActionResponse;
import com.bunit.response.to.ScenarioResponse;
import com.bunit.xml.to.Action;
import com.bunit.xml.to.FList;
import com.bunit.xml.to.Scenario;

public interface BUnitService {

	public ActionResponse getActions() throws Exception;

	public List<?> createNewScenario() throws Exception;

	public Scenario editScenarioInput(String scenarioId, Action action) throws Exception;

	public ScenarioResponse dragScenario(String actionId, String scenarioId) throws Exception;

	public ScenarioResponse deleteScenario(String actionId, String scenarioId) throws Exception;

	public ScenarioResponse openScenario(String scenarioName) throws Exception;

	public List<String> getScenario();
	
	public FList getInputFList(String actionId) throws Exception;
	
	public FList getOutputFList(String actionId) throws Exception;
}
