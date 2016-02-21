package com.bunit.service;

import java.util.List;
import java.util.Map;

import com.bunit.xml.to.Action;
import com.bunit.xml.to.Scenario;
import com.bunit.xml.to.Source;

public interface BUnitService {

	public Map<String, List<Source>> getActions() throws Exception;

	public List<?> createNewScenario() throws Exception;

	public Scenario editScenarioInput(String scenarioId, Action action) throws Exception;

	public Scenario dragScenario(String scenarioId, Action action) throws Exception;

	public Scenario deleteScenario(String scenarioId, Action action) throws Exception;

	public Scenario openScenario(String scenarioName) throws Exception;

	public List<String> getScenario();

}
