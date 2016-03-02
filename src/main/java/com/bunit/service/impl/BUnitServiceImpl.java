package com.bunit.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bunit.response.to.ActionInfo;
import com.bunit.response.to.ActionResponse;
import com.bunit.response.to.InputFlistResponse;
import com.bunit.response.to.ScenarioInfo;
import com.bunit.response.to.ScenarioResponse;
import com.bunit.service.BUnitService;
import com.bunit.util.BuintUtil;
import com.bunit.xml.to.AcctInfo;
import com.bunit.xml.to.Action;
import com.bunit.xml.to.ActionList;
import com.bunit.xml.to.BalInfo;
import com.bunit.xml.to.BillInfo;
import com.bunit.xml.to.FList;
import com.bunit.xml.to.NameInfo;
import com.bunit.xml.to.Products;
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
			actionInfo.setActionId("<div style='cursor: pointer;' id='" + action.DATA.ID + "' draggable='true' ondragstart='OnDragStart(event)'>" + action.DATA.ID + "</div>");
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
		String scenarioPath = path + "\\" + scenarioId;
		
		File scenarioPathFile = new File(scenarioPath);

		if (!scenarioPathFile.exists()) {
			if (scenarioPathFile.mkdirs()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		
		String fileName = scenarioPath + "\\" + scenarioId  + ".xml";
		
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
		
		String scenarioPathLocation = tomcatHome + scenarioFilePath + "\\" + scenarioId;
		String scenarioPath = tomcatHome + scenarioFilePath + "\\" + scenarioId + "\\" +scenarioId + ".xml";
		
		System.out.println("Action path : "+ path);
		System.out.println("Scenario path : "+ scenarioPath);
		
		File[] actionFiles = new File(path).listFiles();
		
		List<ScenarioInfo> scenarioInfos = new ArrayList<ScenarioInfo>();
		for(File actionFile : actionFiles) {
			Action action = buintUtil.convertXmlToAction(actionFile);
			
			System.out.println("Action ID : "+ action.DATA.ID + " File name : "+ actionFile.getName());
			if(actionId.equals(action.DATA.ID)) {
				
				Scenario scenario = buintUtil.convertXmlToScenario(new File(scenarioPath));
				buintUtil.convertActionToXml(action, scenarioPathLocation + "\\" + action.DATA.ID + ".xml");
				
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
				for(Action action2Res : scenario.ACTIONLIST.ACTION) {
					
					ScenarioInfo scenarioInfo = new ScenarioInfo();
					scenarioInfo.setActionID(action2Res.ID);
					scenarioInfo.setActionDescription(action2Res.DESCRIPTION);
					scenarioInfo.setInputFlist("<div style='cursor: pointer; text-decoration: underline;' class='inputList' id="+action2Res.ID+" onclick='inputListSelectedRow()'>InputFList</div>");
					scenarioInfo.setOutputFlist("<div style='cursor: pointer; text-decoration: underline;'  class='outputList'  id="+action2Res.ID+" onclick='outputListSelectedRow()'>OutputFlist</div>");
					scenarioInfo.setRunButton("<button  type='button' class='btn runBtn btn-primary'>Run</button>");
					scenarioInfo.setDeleteButton("<div class='deleteIconDiv'><img src='img/delete.png' class='deleteIcon' onclick='deleteSelectedRow()'/></div>");
					scenarioInfo.setScenarioID(scenario.SCENARIOID);
					scenarioInfo.setStatus(scenario.STATUS);
					
//					scenarioInfo.setDelete("<button  type='button' class='btn btn-primary' onclick='deleteRowInGrid()>Run</button>");
					
					scenarioInfos.add(scenarioInfo);
					
				}
				/*ScenarioInfo scenarioInfo = new ScenarioInfo();
				scenarioInfo.setActionID(action.DATA.ID);
				scenarioInfo.setActionDescription(action.DATA.DESCRIPTION);
				scenarioInfo.setScenarioID(scenario.SCENARIOID);
				scenarioInfo.setStatus(scenario.STATUS);
				scenarioInfo.setInputFlist("<div style='cursor: pointer; text-decoration: underline;' class='inputList' id="+action.ID+" onclick='inputListSelectedRow()'>InputFList</div>");
				scenarioInfo.setOutputFlist("<div style='cursor: pointer; text-decoration: underline;'  class='outputList'  id="+action.ID+" onclick='outputListSelectedRow()'>OutputFlist</div>");
				scenarioInfo.setButton("<button  type='button' class='btn btn-primary'>Run</button>");
								
				scenarioInfos.add(scenarioInfo);*/
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
		String scenarioBasePath = tomcatHome + scenarioFilePath + "\\" + scenarioId;
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioId + "\\" + scenarioId + ".xml";

		File scenarioFileName = new File(path);

		Scenario scenario = buintUtil.convertXmlToScenario(scenarioFileName);
		List<Action> scenarioActionList = scenario.ACTIONLIST.ACTION;

		for(int index = 0; index < scenarioActionList.size(); index++) {
			Action scenarioAction = scenarioActionList.get(index);
			if(scenarioAction.ID.equals(actionId)) {
				scenarioActionList.remove(index);
				new File(scenarioBasePath + "\\" + actionId + ".xml").delete();
			}
		}

		buintUtil.jaxbScenarioToXML(scenario, path);
		
		List<ScenarioInfo> scenarioInfos = new ArrayList<ScenarioInfo>();
		ScenarioResponse scenarioResponse = new ScenarioResponse();
		for(Action action : scenarioActionList) {
			
			ScenarioInfo scenarioInfo = new ScenarioInfo();
			scenarioInfo.setActionID(action.ID);
			scenarioInfo.setActionDescription(action.DESCRIPTION);
			scenarioInfo.setInputFlist("<div style='cursor: pointer; text-decoration: underline;' class='inputList' id="+action.ID+" onclick='inputListSelectedRow()'>InputFList</div>");
			scenarioInfo.setOutputFlist("<div style='cursor: pointer; text-decoration: underline;'  class='outputList'  id="+action.ID+" onclick='outputListSelectedRow()'>OutputFlist</div>");
			scenarioInfo.setRunButton("<button  type='button' class='btn runBtn btn-primary'>Run</button>");
			scenarioInfo.setDeleteButton("<div class='deleteIconDiv'><img src='img/delete.png' class='deleteIcon' onclick='deleteSelectedRow()'/></div>");
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
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioName + "\\" + scenarioName + ".xml";

		System.out.println("Getting scenario from path : " + path);
		File directory = new File(path);
		
		Scenario scenario = buintUtil.convertXmlToScenario(directory);
		List<ScenarioInfo> scenarioInfos = new ArrayList<ScenarioInfo>();
		ScenarioResponse scenarioResponse = new ScenarioResponse();
		if(scenario.ACTIONLIST != null &&  scenario.ACTIONLIST.ACTION != null) {
		for(Action action : scenario.ACTIONLIST.ACTION) {
			
			ScenarioInfo scenarioInfo = new ScenarioInfo();
			scenarioInfo.setActionID(action.ID);
			scenarioInfo.setActionDescription(action.DESCRIPTION);
			scenarioInfo.setInputFlist("<div style='cursor: pointer; text-decoration: underline;' class='inputList' id="+action.ID+" onclick='inputListSelectedRow()'>InputFList</div>");
			scenarioInfo.setOutputFlist("<div style='cursor: pointer; text-decoration: underline;'  class='outputList'  id="+action.ID+" onclick='outputListSelectedRow()'>OutputFlist</div>");
			scenarioInfo.setRunButton("<button  type='button' class='btn runBtn btn-primary'>Run</button>");
			scenarioInfo.setDeleteButton("<div class='deleteIconDiv'><img src='img/delete.png' class='deleteIcon' onclick='deleteSelectedRow()'/></div>");
			scenarioInfo.setScenarioID(scenario.SCENARIOID);
			scenarioInfo.setStatus(scenario.STATUS);
			
//			scenarioInfo.setDelete("<button  type='button' class='btn btn-primary' onclick='deleteRowInGrid()>Run</button>");
			
			scenarioInfos.add(scenarioInfo);
		}
		}
		
		scenarioResponse.setRows(scenarioInfos);
		scenarioResponse.setTotal(scenarioInfos.size());

		return scenarioResponse;
	}
	
	public List<String> openScenarioInfo(String scenarioName) throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioName + "\\" + scenarioName + ".xml";

		System.out.println("Getting scenario from path : " + path);
		File directory = new File(path);
		
		Scenario scenario = buintUtil.convertXmlToScenario(directory);
		
		List<String> scenarioInfo = new ArrayList<String>();
		scenarioInfo.add(scenario.SCENARIOID);
		scenarioInfo.add(scenario.DATE);

		return scenarioInfo;
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
	
	public List<InputFlistResponse> getInputFList(String actionId, String scenarioId) throws Exception {
		
		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioId + "\\" + actionId + ".xml";

		System.out.println("Getting actoins from path : " + path);

		File directory = new File(path);
		//get all the files from a directory
//		File[] fListFiles = directory.listFiles();

		if(directory != null) {
			Action action = buintUtil.convertXmlToAction(directory);
			
			if(action.DATA.ID.equals(actionId)) {
				
				FList fList =  action.INPUT.FLIST;

				List<Products>  productsList = fList.DEAL_INFO.PRODUCTS;
				
				InputFlistResponse inputFlistResponse = new InputFlistResponse();
				inputFlistResponse.setName("FLIST");
				
				List<InputFlistResponse> inputFlistResponseChilds = new ArrayList<InputFlistResponse>();
				
				InputFlistResponse POID = new InputFlistResponse();
				POID.setName("POID");
				POID.setDefaultValue(fList.POID);
				inputFlistResponseChilds.add(POID);
				
				InputFlistResponse PROGRAM_NAME = new InputFlistResponse();
				PROGRAM_NAME.setName("PROGRAM_NAME");
				PROGRAM_NAME.setDefaultValue(fList.PROGRAM_NAME);
				inputFlistResponseChilds.add(PROGRAM_NAME);
				
				InputFlistResponse SERVICE_OBJ = new InputFlistResponse();
				SERVICE_OBJ.setName("SERVICE_OBJ");
				SERVICE_OBJ.setDefaultValue(fList.SERVICE_OBJ);
				inputFlistResponseChilds.add(SERVICE_OBJ);
				
				InputFlistResponse dealInfo = new InputFlistResponse();
				dealInfo.setName("DEAL_INFO");
				
				InputFlistResponse products = new InputFlistResponse();
				products.setName("products");
				
				for(Products prod: productsList) {
					
					InputFlistResponse product = new InputFlistResponse();
					product.setName("product");
					List<InputFlistResponse> children = new ArrayList<InputFlistResponse>();
					InputFlistResponse usageEndOffset = new InputFlistResponse();
					usageEndOffset.setName("USAGE_END_OFFSET");
					usageEndOffset.setDefaultValue(prod.USAGE_END_OFFSET);//<div id ='edit'>03/20/2010</div>
					usageEndOffset.setEditField("<div id='USAGE_END_OFFSET' class ='edit'></div>");
					
					InputFlistResponse usageEndUnit = new InputFlistResponse();
					usageEndUnit.setName("USAGE_END_UNIT");
					usageEndUnit.setDefaultValue(prod.USAGE_END_UNIT);
					usageEndUnit.setEditField("<div id='USAGE_END_UNIT' class ='edit'></div>");
					
					children.add(usageEndOffset);
					children.add(usageEndUnit);
					
					product.setChildren(children);
					
					List<InputFlistResponse> productsChildren = new ArrayList<InputFlistResponse>();
					productsChildren.add(product);
					
					products.setChildren(productsChildren);
				}
				
				List<InputFlistResponse> dealInfoChildren = new ArrayList<InputFlistResponse>();
				dealInfoChildren.add(products);
				
				dealInfo.setChildren(dealInfoChildren);
				
				inputFlistResponseChilds.add(dealInfo);
				inputFlistResponse.setChildren(inputFlistResponseChilds);
				
				return Arrays.asList(inputFlistResponse);
			}
		}

		return null;
	}
	
	public List<InputFlistResponse> getOutputFList(String actionId, String scenarioId) throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioId + "\\" + actionId + ".xml";

		System.out.println("Getting actoins from path : " + path);

		File directory = new File(path);
		//get all the files from a directory
//		File[] fList = directory.listFiles();

		if(directory != null) {
			Action action = buintUtil.convertXmlToAction(directory);

			if(action.DATA.ID.equals(actionId)) {
				
				FList outputlist = action.OUTPUT.FLIST;
				
				//FLIST respos
				InputFlistResponse  flistResponse = new InputFlistResponse();
				flistResponse.setName("FLIST");
				
				//AcctInfo start
				AcctInfo acttInfo = outputlist.ACCTINFO;
				
				InputFlistResponse  acttInfoResponse = new InputFlistResponse();
				acttInfoResponse.setName("ACCTINFO");
				
				InputFlistResponse POIDResponse = new InputFlistResponse();
				POIDResponse.setName("POID");
				POIDResponse.setDefaultValue(acttInfo.POID);
				
				acttInfoResponse.setChildren(Arrays.asList(POIDResponse));
				//AcctInfo end
				
				
				//BAL_INFO start 
				BalInfo balInfo = outputlist.BAL_INFO;
				//BAL_INFO
				InputFlistResponse balInfoObjResponse = new InputFlistResponse();
				balInfoObjResponse.setName("BAL_INFO");
				
				//BILLINFO_OBJ
				InputFlistResponse billinfoObjResponse = new InputFlistResponse();
				billinfoObjResponse.setName("BILLINFO_OBJ");
				billinfoObjResponse.setDefaultValue(balInfo.BILLINFO_OBJ);
				
				//NAME
				InputFlistResponse nameResponse = new InputFlistResponse();
				nameResponse.setName("NAME");
				nameResponse.setDefaultValue(balInfo.NAME);
				
				//POID
				InputFlistResponse billPoidResponse = new InputFlistResponse();
				billPoidResponse.setName("POID");
				billPoidResponse.setDefaultValue(balInfo.POID);
				
				balInfoObjResponse.setChildren(Arrays.asList(billinfoObjResponse, nameResponse, billPoidResponse));
				//BAL_INFO end
				
				//BILLINFO start
				BillInfo billInfo = outputlist.BILLINFO;
				InputFlistResponse billInfoObjResponse = new InputFlistResponse();
				billInfoObjResponse.setName("BILLINFO");
				
				//BAL_GRP_OBJ
				InputFlistResponse balGrpObjResponse = new InputFlistResponse();
				balGrpObjResponse.setName("BAL_GRP_OBJ");
				balGrpObjResponse.setDefaultValue(billInfo.BAL_GRP_OBJ);
				
				//BILLINFO_ID
				InputFlistResponse billinfoIdResponse = new InputFlistResponse();
				billinfoIdResponse.setName("BILLINFO_ID");
				billinfoIdResponse.setDefaultValue(billInfo.BILLINFO_ID);
				
				//BILL_WHEN
				InputFlistResponse billWhenResponse = new InputFlistResponse();
				billWhenResponse.setName("BILL_WHEN");
				billWhenResponse.setDefaultValue(billInfo.BILL_WHEN);
				
				//CURRENCY
				InputFlistResponse currencyResponse = new InputFlistResponse();
				currencyResponse.setName("CURRENCY");
				currencyResponse.setDefaultValue(billInfo.CURRENCY);
				
				//CURRENCY_SECONDARY
				InputFlistResponse currencySecondaryResponse = new InputFlistResponse();
				currencySecondaryResponse.setName("CURRENCY_SECONDARY");
				currencySecondaryResponse.setDefaultValue(billInfo.CURRENCY_SECONDARY);
				
				//EFFECTIVE_T
				InputFlistResponse effectiveTResponse = new InputFlistResponse();
				effectiveTResponse.setName("EFFECTIVE_T");
				effectiveTResponse.setDefaultValue(billInfo.EFFECTIVE_T);
				
				//PAYINFO_OBJ
				InputFlistResponse PAYINFO_OBJ = new InputFlistResponse();
				PAYINFO_OBJ.setName("PAYINFO_OBJ");
				PAYINFO_OBJ.setDefaultValue(billInfo.PAYINFO_OBJ);
				
				//STATUS
				InputFlistResponse STATUS = new InputFlistResponse();
				STATUS.setName("STATUS");
				STATUS.setDefaultValue(billInfo.STATUS);
				
				//PAY_TYPE
				InputFlistResponse PAY_TYPE = new InputFlistResponse();
				PAY_TYPE.setName("PAY_TYPE");
				PAY_TYPE.setDefaultValue(billInfo.PAY_TYPE);
				
				//POID
				InputFlistResponse POID = new InputFlistResponse();
				POID.setName("POID");
				POID.setDefaultValue(billInfo.POID);
				
				billInfoObjResponse.setChildren(Arrays.asList(balGrpObjResponse, billinfoIdResponse, billWhenResponse, currencyResponse, currencySecondaryResponse, 
						effectiveTResponse, PAYINFO_OBJ, STATUS, PAY_TYPE, POID));
				//BILLINFO ends
				
				//NAMEINFO starts
				
				NameInfo nameInfo = outputlist.NAMEINFO; 
				InputFlistResponse NAMEINFO = new InputFlistResponse();
				NAMEINFO.setName("NAMEINFO");
				
				//ADDRESS
				InputFlistResponse ADDRESS = new InputFlistResponse();
				ADDRESS.setName("ADDRESS");
				ADDRESS.setDefaultValue(nameInfo.ADDRESS);
				
				//CANON_COUNTRY
				InputFlistResponse CANON_COUNTRY = new InputFlistResponse();
				CANON_COUNTRY.setName("CANON_COUNTRY");
				CANON_COUNTRY.setDefaultValue(nameInfo.CANON_COUNTRY);
				
				//CITY
				InputFlistResponse CITY = new InputFlistResponse();
				CITY.setName("CITY");
				CITY.setDefaultValue(nameInfo.CITY);
				
				//COMPANY
				InputFlistResponse COMPANY = new InputFlistResponse();
				COMPANY.setName("COMPANY");
				COMPANY.setDefaultValue(nameInfo.COMPANY);
				
				//CONTACT_TYPE
				InputFlistResponse CONTACT_TYPE = new InputFlistResponse();
				CONTACT_TYPE.setName("CONTACT_TYPE");
				CONTACT_TYPE.setDefaultValue(nameInfo.CONTACT_TYPE);
				
				//COUNTRY
				InputFlistResponse COUNTRY = new InputFlistResponse();
				COUNTRY.setName("COUNTRY");
				COUNTRY.setDefaultValue(nameInfo.COUNTRY);
				
				//ELEMENT_ID
				InputFlistResponse ELEMENT_ID = new InputFlistResponse();
				ELEMENT_ID.setName("ELEMENT_ID");
				ELEMENT_ID.setDefaultValue(nameInfo.ELEMENT_ID);
				
				//EMAIL_ADDR
				InputFlistResponse EMAIL_ADDR = new InputFlistResponse();
				EMAIL_ADDR.setName("EMAIL_ADDR");
				EMAIL_ADDR.setDefaultValue(nameInfo.EMAIL_ADDR);
				
				//FIRST_NAME
				InputFlistResponse FIRST_NAME = new InputFlistResponse();
				FIRST_NAME.setName("FIRST_NAME");
				FIRST_NAME.setDefaultValue(nameInfo.FIRST_NAME);
				
				//LAST_NAME
				InputFlistResponse LAST_NAME = new InputFlistResponse();
				LAST_NAME.setName("LAST_NAME");
				LAST_NAME.setDefaultValue(nameInfo.LAST_NAME);
				
				//MIDDLE_NAME
				InputFlistResponse MIDDLE_NAME = new InputFlistResponse();
				MIDDLE_NAME.setName("MIDDLE_NAME");
				MIDDLE_NAME.setDefaultValue(nameInfo.MIDDLE_NAME);
				
				//SALUTATION
				InputFlistResponse SALUTATION = new InputFlistResponse();
				SALUTATION.setName("SALUTATION");
				SALUTATION.setDefaultValue(nameInfo.SALUTATION);
				
				//STATE
				InputFlistResponse STATE = new InputFlistResponse();
				STATE.setName("STATE");
				STATE.setDefaultValue(nameInfo.STATE);
				
				//ZIP
				InputFlistResponse ZIP = new InputFlistResponse();
				ZIP.setName("ZIP");
				ZIP.setDefaultValue(nameInfo.ZIP);
				
				NAMEINFO.setChildren(Arrays.asList(ADDRESS, CANON_COUNTRY, CITY, COMPANY, CONTACT_TYPE, COUNTRY, 
						ELEMENT_ID, EMAIL_ADDR, FIRST_NAME, LAST_NAME, MIDDLE_NAME, SALUTATION, STATE, ZIP));
				
				
				flistResponse.setChildren(Arrays.asList(acttInfoResponse, balInfoObjResponse, billInfoObjResponse, NAMEINFO));
				
				return Arrays.asList(flistResponse);
			}
		}

		return null;
	}
}
