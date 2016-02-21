package com.bunit.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bunit.service.BUnitService;
import com.bunit.util.BuintUtil;
import com.bunit.xml.to.Action;
import com.bunit.xml.to.Scenario;
import com.bunit.xml.to.Source;

@Service
public class BUnitServiceImpl implements BUnitService {

	@Autowired
	private BuintUtil buintUtil;

	@Value("${SCENARIO_FILE_PATH}")
	private String scenarioFilePath;

	@Value("${ACTION_LIBRARY_FILE_PATH}")
	private String actionLibraryFilePath;

	public Map<String, List<Source>> getActions() throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + actionLibraryFilePath;

		System.out.println("Getting actoins from path : " + path);

		File directory = new File(path);
		//get all the files from a directory
		File[] fList = directory.listFiles();
		BuintUtil buintUtil = new BuintUtil();

		Map<String, List<Source>> map =  new HashMap<String, List<Source>>();

		for(File file : fList) {

			Action action = buintUtil.convertXmlToAction(file);
			action.actionFileName = file.getName();

			Source source = new Source();
			source.setId(action.DATA.ID);
			source.setDesc(action.DATA.DESCRIPTION);
			source.setScenarioId(action.DATA.SCENARIOID);
			source.setAction(action);

			if(map.containsKey(action.DATA.GROUP)) {
				List<Source> sources = map.get(action.DATA.GROUP);
				sources.add(source);
			} else {
				List<Source> newSource = new ArrayList<Source>();
				newSource.add(source);

				map.put(action.DATA.GROUP, newSource);
			}

		}
		return map;
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

		String fileName = path + "\\" + genericFileName + (++fileCount) + ".xml";

		///BUNIT/BRMTestScenario001
		Scenario action = new Scenario("10", null, "sample Desc1", null, null, null);

		File newScenarioFile = buintUtil.jaxbScenarioToXML(action, fileName);

		java.nio.file.Path paths = Paths.get(newScenarioFile.getAbsoluteFile().toURI());
		BasicFileAttributes view = Files.getFileAttributeView(paths, BasicFileAttributeView.class)
				.readAttributes();
		List fileInfo = new ArrayList();
		fileInfo.add(newScenarioFile.getName());
		fileInfo.add(view.creationTime().toMillis());

		return fileInfo;
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

	public Scenario dragScenario(String scenarioId, Action action) throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioId;

		File scenarioFileName = new File(path);

		Scenario scenario = buintUtil.convertXmlToScenario(scenarioFileName);
		List<Action> scenarioActionList = scenario.ACTIONLIST.ACTION;

		scenarioActionList.add(action);

		String actionFileName = tomcatHome + actionLibraryFilePath + "\\" + action.actionFileName;

		action = buintUtil.convertXmlToAction(new File(actionFileName));
		action.DATA.SCENARIOID = scenario.SCENARIOID;
		System.out.println(action);
		System.out.println("***************" + actionFileName);
		buintUtil.convertActionToXml(action, actionFileName);

		buintUtil.jaxbScenarioToXML(scenario, path);
		return scenario;
	}

	public Scenario deleteScenario(String scenarioId, Action action) throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioId;

		File scenarioFileName = new File(path);

		Scenario scenario = buintUtil.convertXmlToScenario(scenarioFileName);
		List<Action> scenarioActionList = scenario.ACTIONLIST.ACTION;

		for(int index = 0; index < scenarioActionList.size(); index++) {
			Action scenarioAction = scenarioActionList.get(index);
			if(scenarioAction.ID.equals(action.ID)) {
				scenarioActionList.remove(index);
				break;
			}
		}

		buintUtil.jaxbScenarioToXML(scenario, path);
		return scenario;
	}

	public Scenario openScenario(String scenarioName) throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + scenarioFilePath + "\\" +scenarioName;

		System.out.println("Getting scenario from path : " + path);
		File directory = new File(path);

		return buintUtil.convertXmlToScenario(directory);
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
