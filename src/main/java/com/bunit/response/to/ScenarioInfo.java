package com.bunit.response.to;

public class ScenarioInfo {
	
	private String ScenarioID;
	
	private String ActionID;
	
	private String ActionDescription;
	
	private String InputFlist;
	
	private String OutputFlist;
	
	private String Status;
	
	private String runButton;
	
	private String deleteButton;

	public String getScenarioID() {
		return ScenarioID;
	}

	public void setScenarioID(String scenarioID) {
		ScenarioID = scenarioID;
	}

	public String getActionID() {
		return ActionID;
	}

	public void setActionID(String actionID) {
		ActionID = actionID;
	}

	public String getActionDescription() {
		return ActionDescription;
	}

	public void setActionDescription(String actionDescription) {
		ActionDescription = actionDescription;
	}

	public String getInputFlist() {
		return InputFlist;
	}

	public void setInputFlist(String inputFlist) {
		InputFlist = inputFlist;
	}

	public String getOutputFlist() {
		return OutputFlist;
	}

	public void setOutputFlist(String outputFlist) {
		OutputFlist = outputFlist;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getRunButton() {
		return runButton;
	}

	public void setRunButton(String runButton) {
		this.runButton = runButton;
	}

	public String getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(String deleteButton) {
		this.deleteButton = deleteButton;
	}

	
}
