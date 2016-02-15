package com.bunit.ui;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bunit.ui.xml.to.Action;
import com.bunit.ui.xml.to.Scenario;
import com.bunit.ui.xml.util.BuintUtil;

@Path("/bunit")
public class BuintUiResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public MyJaxbBean getMyBean() {
		return new MyJaxbBean("Agamemnon", 32);
	}
	
	@GET
	@Produces("application/json")
	@Path("/newScenario")
	public String createNewScenario() {
		
		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + "\\BUNIT\\BRMTestScenario001";
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
		
		BuintUtil buintUtil = new BuintUtil();
		buintUtil.jaxbObjectToXML(action, fileName);
		
		return "New Scenario created.";
	}
	
	@GET
	@Produces("application/json")
	@Path("/get_actions")
	public List<Action> getActions() throws Exception {
		
		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + "\\BUNIT\\ACTION_LIBRARY";
		
		System.out.println("Getting actoins from path : " + path);
		
		File directory = new File(path);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        BuintUtil buintUtil = new BuintUtil();
        
        List<Action> action = new ArrayList<Action>();
        
        for(File file : fList) {
        	action.add(buintUtil.convertXmlToObject(file));
        }
        
		return action;
	}
	
	@GET
	@Produces("application/json")
	@Path("/open_scenario")
	public Scenario openScenario() throws Exception {
		
		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + "\\BUNIT\\BRMTestScenario001\\";
		
		System.out.println("Getting actoins from path : " + path);
		File directory = new File(path);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        Scenario scenario = null;
        BuintUtil buintUtil = new BuintUtil();
        File file = fList[0];
        scenario = buintUtil.convertXmlToScenario(file);
        
		return scenario;
	}
}
