package com.bunit.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bunit.response.to.ActionInfo;
import com.bunit.response.to.ActionResponse;
import com.bunit.response.to.ScenarioInfo;
import com.bunit.response.to.ScenarioResponse;
import com.bunit.service.BUnitService;
import com.bunit.util.BuintUtil;
import com.bunit.xml.to.Action;
import com.bunit.xml.to.ActionList;
import com.bunit.xml.to.Scenario;

@Service
public class BUnitServiceImpl implements BUnitService {

	@Autowired
	private BuintUtil buintUtil;

	@Value("${SCENARIO_FILE_PATH}")
	private String scenarioFilePath;

	@Value("${ACTION_LIBRARY_FILE_PATH}")
	private String actionLibraryFilePath;

	public ActionResponse getActions() throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + actionLibraryFilePath;

		System.out.println("Getting actoins from path : " + path);

		File directory = new File(path);
		//get all the files from a directory
		File[] fList = directory.listFiles();
		BuintUtil buintUtil = new BuintUtil();

		ActionResponse source = new ActionResponse();
		List<ActionInfo> actionList = new ArrayList<ActionInfo>();
		for(File file : fList) {

			Action action = buintUtil.convertXmlToAction(file);
			
			ActionInfo actionInfo = new ActionInfo();
			actionInfo.setActionId(action.DATA.ID);
			actionInfo.setActionDesc(action.DATA.DESCRIPTION);
			actionInfo.setGroup(action.DATA.GROUP);
			
			actionList.add(actionInfo);
			
		}
		
		source.setRows(actionList);
		source.setTotal(actionList.size());
		
		return source;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<?> createNewScenario() throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + scenarioFilePath;
		System.out.println(path);

		File file = new File(path);

		if (!file.exists()) {
			if (file.mkdirs()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		String genericFileName = "BRMTestScenario";
		Integer fileCount =  file.list()  != null ? file.list().length : 0;
		
		String scenarioId = genericFileName + (++fileCount);
		String fileName = path + "\\" + scenarioId  + ".xml";
		
		//01/20/2016 10:10
		Date createDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm");
		dateFormat.format(createDate);
		///BUNIT/BRMTestScenario001
		Scenario scenario = new Scenario();
		scenario.SCENARIOID = scenarioId;
		scenario.DATE = dateFormat.format(createDate);

		buintUtil.jaxbScenarioToXML(scenario, fileName);
		List response = new ArrayList();
		response.add(scenarioId);
		response.add(dateFormat.format(createDate));
		
		return response;
	}

	public Scenario editScenarioInput(String scenarioId, Action action) throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioId;

		File scenarioFileName = new File(path);

		Scenario scenario = buintUtil.convertXmlToScenario(scenarioFileName);
		List<Action> scenarioActionList = scenario.ACTIONLIST.ACTION;

		for(int index = 0; index < scenarioActionList.size(); index++) {
			Action scenarioAction = scenarioActionList.get(index);
			if(scenarioAction.ID.equals(action.ID)) {
				scenarioActionList.set(index, action);
				break;
			}
		}

		buintUtil.jaxbScenarioToXML(scenario, path);
		return scenario;
	}

	public ScenarioResponse dragScenario(String actionId, String scenarioId) throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + actionLibraryFilePath;
		
		String scenarioPath = tomcatHome + scenarioFilePath + "\\" + scenarioId + ".xml";
		
		System.out.println("Action path : "+ path);
		System.out.println("Scenario path : "+ scenarioPath);
		
		File[] actionFiles = new File(path).listFiles();
		List<ScenarioInfo> scenarioInfos = new ArrayList<ScenarioInfo>();
		for(File actionFile : actionFiles) {
			Action action = buintUtil.convertXmlToAction(actionFile);
			
			System.out.println("Action ID : "+ action.DATA.ID + " File name : "+ actionFile.getName());
			if(actionId.equals(action.DATA.ID)) {
				
				Scenario scenario = buintUtil.convertXmlToScenario(new File(scenarioPath));
				
				List<Action> actionList = new ArrayList<Action>();
				Action action2Save = new Action();
				
				action2Save.ID = action.DATA.ID;
				action2Save.DESCRIPTION = action.DATA.DESCRIPTION;
				action2Save.STATUS = action.DATA.STATUS;
				actionList.add(action2Save);
				
				if(scenario.ACTIONLIST == null || scenario.ACTIONLIST.ACTION == null) {
					ActionList actionList2Save = new ActionList(actionList);
					scenario.ACTIONLIST = actionList2Save;
				} else {
					scenario.ACTIONLIST.ACTION.add(action2Save);
				}
				
				System.out.println(scenario.ACTIONLIST.ACTION);
				
				buintUtil.jaxbScenarioToXML(scenario, scenarioPath);
				
				ScenarioInfo scenarioInfo = new ScenarioInfo();
				scenarioInfo.setActionID(action.DATA.ID);
				scenarioInfo.setActionDescription(action.DATA.DESCRIPTION);
				scenarioInfo.setInputFlist("InputFList");
				scenarioInfo.setOutputFlist("OutputFlist");
				scenarioInfo.setButton("button");
				scenarioInfo.setScenarioID(scenario.SCENARIOID);
				scenarioInfo.setStatus(scenario.STATUS);
				
				scenarioInfos.add(scenarioInfo);
				break;
			}
		}
		
		ScenarioResponse scenarioResponse = new ScenarioResponse();
		scenarioResponse.setRows(scenarioInfos);
		scenarioResponse.setTotal(scenarioInfos.size());

		return scenarioResponse;
	}

	public ScenarioResponse deleteScenario(String actionId, String scenarioId) throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioId + ".xml";

		File scenarioFileName = new File(path);

		Scenario scenario = buintUtil.convertXmlToScenario(scenarioFileName);
		List<Action> scenarioActionList = scenario.ACTIONLIST.ACTION;

		for(int index = 0; index < scenarioActionList.size(); index++) {
			Action scenarioAction = scenarioActionList.get(index);
			if(scenarioAction.ID.equals(actionId)) {
				scenarioActionList.remove(index);
				break;
			}
		}

		buintUtil.jaxbScenarioToXML(scenario, path);
		
		List<ScenarioInfo> scenarioInfos = new ArrayList<ScenarioInfo>();
		ScenarioResponse scenarioResponse = new ScenarioResponse();
		for(Action action : scenarioActionList) {
			
			ScenarioInfo scenarioInfo = new ScenarioInfo();
			scenarioInfo.setActionID(action.ID);
			scenarioInfo.setActionDescription(action.DESCRIPTION);
			scenarioInfo.setInputFlist("InputFList");
			scenarioInfo.setOutputFlist("OutputFlist");
			scenarioInfo.setButton("button");
			scenarioInfo.setScenarioID(scenario.SCENARIOID);
			scenarioInfo.setStatus(scenario.STATUS);
			
			scenarioInfos.add(scenarioInfo);
		}
		
		scenarioResponse.setRows(scenarioInfos);
		scenarioResponse.setTotal(scenarioInfos.size());
		
		return scenarioResponse;
	}

	public ScenarioResponse openScenario(String scenarioName) throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + scenarioFilePath + "\\" +scenarioName;

		System.out.println("Getting scenario from path : " + path);
		File directory = new File(path);
		
		Scenario scenario = buintUtil.convertXmlToScenario(directory);
		List<ScenarioInfo> scenarioInfos = new ArrayList<ScenarioInfo>();
		ScenarioResponse scenarioResponse = new ScenarioResponse();
		for(Action action : scenario.ACTIONLIST.ACTION) {
			
			ScenarioInfo scenarioInfo = new ScenarioInfo();
			scenarioInfo.setActionID(action.ID);
			scenarioInfo.setActionDescription(action.DESCRIPTION);
			scenarioInfo.setInputFlist("<div style='cursor: pointer; text-decoration: underline;' class='inputList' id="+action.ID+" onclick='inputListSelectedRow()'>InputFList</div>");
			scenarioInfo.setOutputFlist("<div style='cursor: pointer; text-decoration: underline;'  class='outputList'  id="+action.ID+">OutputFlist</div>");
			scenarioInfo.setButton("<button  type='button' class='btn btn-primary'>Primary</button>");
			scenarioInfo.setScenarioID(scenario.SCENARIOID);
			scenarioInfo.setStatus(scenario.STATUS);
			
			scenarioInfos.add(scenarioInfo);
		}
		
		scenarioResponse.setRows(scenarioInfos);
		scenarioResponse.setTotal(scenarioInfos.size());

		return scenarioResponse;
	}

	public List<String> getScenario() {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + scenarioFilePath;

		List<String> fileNames = new ArrayList<String>();

		File directory = new File(path);
		File[] files = directory.listFiles();

		for(File file : files) {
			fileNames.add(file.getName());
		}

		return fileNames;
	}
}
