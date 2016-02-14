package com.bunit.ui.xml.to;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="SampleAction")
public class SampleAction {
	
	@XmlElement(name = "ActionId")
	public Integer actionId;
	
	@XmlElement(name = "ActionName")
	public String ActionName;
	
	@XmlElement(name = "ActionDescription")
	public String ActionDescription;
	
	@XmlElement(name = "ScenarioId")
	public Integer ScenarioId;
	
	@XmlElement(name = "ActionType")
	public String ActionType;
	
	@XmlElement(name = "ActionGroup")
	public String ActionGroup;
	
	@XmlElement(name = "ActionDate")
	public Date ActionDate;
	
	@XmlElement(name = "Type")
	public String Type;
	
	@XmlElement(name = "Status")
	public String Status;

	public SampleAction() {} // JAXB needs this

	public SampleAction(Integer actionId, String actionName,
			String actionDescription, Integer scenarioId, String actionType,
			String actionGroup, Date actionDate, String type, String status) {
		super();
		this.actionId = actionId;
		ActionName = actionName;
		ActionDescription = actionDescription;
		ScenarioId = scenarioId;
		ActionType = actionType;
		ActionGroup = actionGroup;
		ActionDate = actionDate;
		Type = type;
		Status = status;
	}
}
