package com.bunit.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.bunit.response.to.ActionInfo;
import com.bunit.response.to.ActionResponse;
import com.bunit.response.to.EditedOutputList;
import com.bunit.response.to.InputFlistResponse;
import com.bunit.response.to.ScenarioInfo;
import com.bunit.response.to.ScenarioResponse;
import com.bunit.service.BUnitService;
import com.bunit.util.BuintUtil;
import com.bunit.util.KeyNameEnum;
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
	
	public static final Logger LOGGER = Logger.getLogger(BUnitServiceImpl.class);

	@Autowired
	private BuintUtil buintUtil;

	@Value("${SCENARIO_FILE_PATH}")
	private String scenarioFilePath;

	@Value("${ACTION_LIBRARY_FILE_PATH}")
	private String actionLibraryFilePath;
	
	@Value("${IS_LINUX}")
	private Boolean isLinux;
	
	@Value("${LINUX_SCENARIO_FILE_PATH}")
	private String linuxScenarioFilePath;

	@Value("${LINUX_ACTION_LIBRARY_FILE_PATH}")
	private String linuxActionLibraryFilePath;

	public ActionResponse getActions() throws Exception {

		String tomcatHome = System.getProperty("user.home");
		String path = tomcatHome + actionLibraryFilePath;
		
		if(isLinux) {
			path = tomcatHome + linuxActionLibraryFilePath;
		}

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

		String tomcatHome = System.getProperty("user.home");
		String path = tomcatHome + scenarioFilePath;
		System.out.println(path);
		
		if(isLinux) {
			path = tomcatHome + linuxScenarioFilePath;
		}

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
		
		if(isLinux) {
			scenarioPath = path + "/" + scenarioId;
		}
		
		File scenarioPathFile = new File(scenarioPath);

		if (!scenarioPathFile.exists()) {
			if (scenarioPathFile.mkdirs()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		
		String fileName = scenarioPath + "\\" + scenarioId  + ".xml";
		if(isLinux) {
			fileName = scenarioPath + "/" + scenarioId  + ".xml";
		}
		
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

	public String editScenarioOutput(String actionId, String scenarioId, EditedOutputList editedOutputList) throws Exception {
		String tomcatHome = System.getProperty("user.home");
		String scenarioPath = tomcatHome + scenarioFilePath + "\\" + scenarioId + "\\" +scenarioId + ".xml";
		String actionPath = tomcatHome + scenarioFilePath + "\\" + scenarioId + "\\" + actionId + ".xml"; 
		
		if(isLinux) {
			scenarioPath = tomcatHome + linuxScenarioFilePath + "/" + scenarioId + "/" +scenarioId + ".xml";
			actionPath = tomcatHome + linuxScenarioFilePath + "/" + scenarioId + "/" + actionId + ".xml";
		}
		
		addElementToXml(scenarioPath, editedOutputList.getValues());
		addElementToXml(actionPath, editedOutputList.getValues());
		
		System.out.println(editedOutputList.getValues());

		return "output FList edited successfully";
	}

	public ScenarioResponse dragScenario(String actionId, String scenarioId) throws Exception {
		
		if(actionId == null || actionId.isEmpty() || scenarioId == null || scenarioId.isEmpty()) {
			LOGGER.warn("No actionId or senarioId is null");
			return null;
		}

		String tomcatHome = System.getProperty("user.home");
		String path = tomcatHome + actionLibraryFilePath;
		String scenarioPathLocation = tomcatHome + scenarioFilePath + "\\" + scenarioId;
		String scenarioPath = tomcatHome + scenarioFilePath + "\\" + scenarioId + "\\" +scenarioId + ".xml";
		
		if(isLinux) {
			path = tomcatHome + linuxActionLibraryFilePath;
			scenarioPathLocation = tomcatHome + linuxScenarioFilePath + "/" + scenarioId;
			scenarioPath = tomcatHome + linuxScenarioFilePath + "/" + scenarioId + "/" +scenarioId + ".xml";
		}
		
		System.out.println("Action path : "+ path);
		System.out.println("Scenario path : "+ scenarioPath);
		
		File[] actionFiles = new File(path).listFiles();
		
		List<ScenarioInfo> scenarioInfos = new ArrayList<ScenarioInfo>();
		for(File actionFile : actionFiles) {
			Action action = buintUtil.convertXmlToAction(actionFile);
			if(action == null) {
				continue;
			}
			
			LOGGER.info("Action ID : "+ action.DATA.ID + " File name : "+ actionFile.getName());
			if(actionId.equals(action.DATA.ID)) {
				
				Scenario scenario = buintUtil.convertXmlToScenario(new File(scenarioPath));
				if(scenario == null) {
					continue;
				}
				
				/*if(isLinux) {
					buintUtil.convertActionToXml(action, scenarioPathLocation + "/" + action.DATA.ID + ".xml");
				} else {
					buintUtil.convertActionToXml(action, scenarioPathLocation + "\\" + action.DATA.ID + ".xml");
				}*/
				
				List<Action> actionList = new ArrayList<Action>();
				Action action2Save = new Action();
				
				action2Save.ID = action.DATA.ID;
				action2Save.DESCRIPTION = action.DATA.DESCRIPTION;
				action2Save.STATUS = action.DATA.STATUS;
				actionList.add(action2Save);
				
				if(scenario.ACTIONLIST == null || scenario.ACTIONLIST.ACTION == null) {
					ActionList actionList2Save = new ActionList(actionList);
					scenario.ACTIONLIST = actionList2Save;
					if(isLinux) {
						buintUtil.convertActionToXml(action, scenarioPathLocation + "/" + action.DATA.ID + ".xml");
					} else {
						buintUtil.convertActionToXml(action, scenarioPathLocation + "\\" + action.DATA.ID + ".xml");
					}
				} else {
					//TODO same id draged
					List<Action> actionsToCheck = scenario.ACTIONLIST.ACTION;
					Action actionIdToCHeck = actionsToCheck.get(actionsToCheck.size()-1);
					
					List<String> ids = new ArrayList<String>();
					for(Action act : actionsToCheck) {
						ids.add(act.ID);
					}
					int count = 0;
					for(String id : ids) {
						if(id.startsWith(actionId)){
							count++;
						}
					}
					
					System.out.println("Exception block out");
					if(actionIdToCHeck.ID.equals(actionId)) {
						System.out.println("Exception block");
						throw new Exception("Same Id Dragged.");
					} else if(actionIdToCHeck.ID.equals(actionId+count)) {
							System.out.println("exception 2 : " + actionId+count);
							throw new Exception("Same Id Dragged.");
					}
					
					String actionIdToSave = action.DATA.ID;
					if(count > 0) {
						actionIdToSave = action.DATA.ID + (count+1);
					}
					action2Save.ID = actionIdToSave;
					scenario.ACTIONLIST.ACTION.add(action2Save);
					
					if(isLinux) {
						buintUtil.convertActionToXml(action, scenarioPathLocation + "/" + actionIdToSave + ".xml");
					} else {
						buintUtil.convertActionToXml(action, scenarioPathLocation + "\\" + actionIdToSave + ".xml");
					}
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
					
					scenarioInfos.add(scenarioInfo);
					
				}
				break;
			}
		}
		
		ScenarioResponse scenarioResponse = new ScenarioResponse();
		scenarioResponse.setRows(scenarioInfos);
		scenarioResponse.setTotal(scenarioInfos.size());

		return scenarioResponse;
	}

	public ScenarioResponse deleteScenario(String actionId, String scenarioId) throws Exception {

		String tomcatHome = System.getProperty("user.home");
		String scenarioBasePath = tomcatHome + scenarioFilePath + "\\" + scenarioId;
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioId + "\\" + scenarioId + ".xml";
		
		if(isLinux) {
			scenarioBasePath = tomcatHome + linuxScenarioFilePath + "/" + scenarioId;
			path = tomcatHome + linuxScenarioFilePath + "/" + scenarioId + "/" + scenarioId + ".xml";
		}
		
		File scenarioFileName = new File(path);

		Scenario scenario = buintUtil.convertXmlToScenario(scenarioFileName);
		List<Action> scenarioActionList = scenario.ACTIONLIST.ACTION;
		
		for(int index = 0; index < scenarioActionList.size(); index++) {
			Action scenarioAction = scenarioActionList.get(index);
			if(scenarioAction.ID.equals(actionId)) {
				scenarioActionList.remove(index);
			if(isLinux) {
				new File(scenarioBasePath + "/" + actionId + ".xml").delete();
			} else {
				new File(scenarioBasePath + "\\" + actionId + ".xml").delete();
			}
				
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
		if(scenarioName == null || scenarioName.isEmpty()) {
			LOGGER.warn("No scenario found.");
			return null;
		}

		String tomcatHome = System.getProperty("user.home");
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioName + "\\" + scenarioName + ".xml";
		
		if(isLinux) {
			path = tomcatHome + linuxScenarioFilePath + "/" + scenarioName + "/" + scenarioName + ".xml";
		}
		
		System.out.println("Getting scenario from path : " + path);
		File directory = new File(path);
		
		Scenario scenario = buintUtil.convertXmlToScenario(directory);
		if(scenario == null) {
			LOGGER.warn("No scenario found for : " + scenarioName);
			return null;
		}
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

				scenarioInfos.add(scenarioInfo);
			}
		}
		
		scenarioResponse.setRows(scenarioInfos);
		scenarioResponse.setTotal(scenarioInfos.size());

		return scenarioResponse;
	}
	
	public List<String> openScenarioInfo(String scenarioName) throws Exception {
		if(scenarioName == null || scenarioName.isEmpty()) {
			LOGGER.warn("No Scenario found");
			return null;
		}

		String tomcatHome = System.getProperty("user.home");
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioName + "\\" + scenarioName + ".xml";
		if(isLinux) {
			path = tomcatHome + linuxScenarioFilePath + "/" + scenarioName + "/" + scenarioName + ".xml";
		}

		LOGGER.info("Getting scenario from path : " + path);
		File directory = new File(path);
		
		Scenario scenario = buintUtil.convertXmlToScenario(directory);
		if(scenario == null) {
			LOGGER.warn("No Scenario found for : " + scenarioName);
			return null;
		}
		List<String> scenarioInfo = new ArrayList<String>();
		scenarioInfo.add(scenario.SCENARIOID);
		scenarioInfo.add(scenario.DATE);

		return scenarioInfo;
	}
	
	public List<String> getScenario() {

		String tomcatHome = System.getProperty("user.home");
		String path = tomcatHome + scenarioFilePath;
		
		if(isLinux) {
			path = tomcatHome + linuxScenarioFilePath;
		}

		List<String> fileNames = new ArrayList<String>();

		File directory = new File(path);
		File[] files = directory.listFiles();

		for(File file : files) {
			fileNames.add(file.getName());
		}

		return fileNames;
	}
	
	public List<InputFlistResponse> getInputFList(String actionId, String scenarioId) throws Exception {
		
		String tomcatHome = System.getProperty("user.home");
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioId + "\\" + actionId + ".xml";
		
		if(isLinux) {
			path = tomcatHome + linuxScenarioFilePath + "/" + scenarioId + "/" + actionId + ".xml";
		}
		System.out.println("Getting actoins from path : " + path);

		File directory = new File(path);

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
				POID.setEditField("<div id='POID' class ='edit'></div>");
				inputFlistResponseChilds.add(POID);
				
				InputFlistResponse PROGRAM_NAME = new InputFlistResponse();
				PROGRAM_NAME.setName("PROGRAM_NAME");
				PROGRAM_NAME.setDefaultValue(fList.PROGRAM_NAME);
				PROGRAM_NAME.setEditField("<div id='PROGRAM_NAME' class ='edit'></div>");
				inputFlistResponseChilds.add(PROGRAM_NAME);
				
				InputFlistResponse SERVICE_OBJ = new InputFlistResponse();
				SERVICE_OBJ.setName("SERVICE_OBJ");
				SERVICE_OBJ.setDefaultValue(fList.SERVICE_OBJ);
				SERVICE_OBJ.setEditField("<div id='SERVICE_OBJ' class ='edit'></div>");
				inputFlistResponseChilds.add(SERVICE_OBJ);
				
				InputFlistResponse dealInfo = new InputFlistResponse();
				dealInfo.setName("DEAL_INFO");
				
				InputFlistResponse products = new InputFlistResponse();
				products.setName("products");
				
				List<InputFlistResponse> productsChildren = new ArrayList<InputFlistResponse>();
				
				for(int index = 0; index < productsList.size(); index++) {
					
					Products prod = productsList.get(index);
					InputFlistResponse product = new InputFlistResponse();
					product.setName("product_"+index);
					InputFlistResponse usageEndOffset = new InputFlistResponse();
					usageEndOffset.setName("USAGE_END_OFFSET");
					usageEndOffset.setDefaultValue(prod.USAGE_END_OFFSET);//<div id ='edit'>03/20/2010</div>
					usageEndOffset.setEditField("<div id='USAGE_END_OFFSET,"+ index +"' class ='edit'></div>");
					
					InputFlistResponse usageEndUnit = new InputFlistResponse();
					usageEndUnit.setName("USAGE_END_UNIT");
					usageEndUnit.setDefaultValue(prod.USAGE_END_UNIT);
					usageEndUnit.setEditField("<div id='USAGE_END_UNIT," + index + "' class ='edit'></div>");
					
					product.setChildren(Arrays.asList(usageEndOffset, usageEndUnit));
					
					productsChildren.add(product);
				}
				
				products.setChildren(productsChildren);
				
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

		String tomcatHome = System.getProperty("user.home");
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioId + "\\" + actionId + ".xml";
		if(isLinux) {
			path = tomcatHome + linuxScenarioFilePath + "/" + scenarioId + "/" + actionId + ".xml";
		}

		System.out.println("Getting actoins from path : " + path);

		File directory = new File(path);

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
				POIDResponse.setEditField("<div id='ACCTINFO|POID' class ='output_edit'></div>");
				
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
				billinfoObjResponse.setEditField("<div id='BAL_INFO|BILLINFO_OBJ' class ='output_edit'></div>");
				
				//NAME
				InputFlistResponse nameResponse = new InputFlistResponse();
				nameResponse.setName("NAME");
				nameResponse.setDefaultValue(balInfo.NAME);
				nameResponse.setEditField("<div id='BAL_INFO|NAME' class ='output_edit'></div>");
				
				//POID
				InputFlistResponse billPoidResponse = new InputFlistResponse();
				billPoidResponse.setName("POID");
				billPoidResponse.setDefaultValue(balInfo.POID);
				billPoidResponse.setEditField("<div id='BAL_INFO|POID' class ='output_edit'></div>");
				
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
				balGrpObjResponse.setEditField("<div id='BILLINFO|BAL_GRP_OBJ' class ='output_edit'></div>");
				
				//BILLINFO_ID
				InputFlistResponse billinfoIdResponse = new InputFlistResponse();
				billinfoIdResponse.setName("BILLINFO_ID");
				billinfoIdResponse.setDefaultValue(billInfo.BILLINFO_ID);
				billinfoIdResponse.setEditField("<div id='BILLINFO|BILLINFO_ID' class ='output_edit'></div>");
				
				//BILL_WHEN
				InputFlistResponse billWhenResponse = new InputFlistResponse();
				billWhenResponse.setName("BILL_WHEN");
				billWhenResponse.setDefaultValue(billInfo.BILL_WHEN);
				billWhenResponse.setEditField("<div id='BILLINFO|BILL_WHEN' class ='output_edit'></div>");
				
				//CURRENCY
				InputFlistResponse currencyResponse = new InputFlistResponse();
				currencyResponse.setName("CURRENCY");
				currencyResponse.setDefaultValue(billInfo.CURRENCY);
				currencyResponse.setEditField("<div id='BILLINFO|CURRENCY' class ='output_edit'></div>");
				
				//CURRENCY_SECONDARY
				InputFlistResponse currencySecondaryResponse = new InputFlistResponse();
				currencySecondaryResponse.setName("CURRENCY_SECONDARY");
				currencySecondaryResponse.setDefaultValue(billInfo.CURRENCY_SECONDARY);
				currencySecondaryResponse.setEditField("<div id='BILLINFO|CURRENCY_SECONDARY' class ='output_edit'></div>");
				
				//EFFECTIVE_T
				InputFlistResponse effectiveTResponse = new InputFlistResponse();
				effectiveTResponse.setName("EFFECTIVE_T");
				effectiveTResponse.setDefaultValue(billInfo.EFFECTIVE_T);
				effectiveTResponse.setEditField("<div id='BILLINFO|EFFECTIVE_T' class ='output_edit'></div>");
				
				//PAYINFO_OBJ
				InputFlistResponse PAYINFO_OBJ = new InputFlistResponse();
				PAYINFO_OBJ.setName("PAYINFO_OBJ");
				PAYINFO_OBJ.setDefaultValue(billInfo.PAYINFO_OBJ);
				PAYINFO_OBJ.setEditField("<div id='BILLINFO|PAYINFO_OBJ' class ='output_edit'></div>");
				
				//STATUS
				InputFlistResponse STATUS = new InputFlistResponse();
				STATUS.setName("STATUS");
				STATUS.setDefaultValue(billInfo.STATUS);
				STATUS.setEditField("<div id='BILLINFO|STATUS' class ='output_edit'></div>");
				
				//PAY_TYPE
				InputFlistResponse PAY_TYPE = new InputFlistResponse();
				PAY_TYPE.setName("PAY_TYPE");
				PAY_TYPE.setDefaultValue(billInfo.PAY_TYPE);
				PAY_TYPE.setEditField("<div id='BILLINFO|PAY_TYPE' class ='output_edit'></div>");
				
				//POID
				InputFlistResponse POID = new InputFlistResponse();
				POID.setName("POID");
				POID.setDefaultValue(billInfo.POID);
				POID.setEditField("<div id='BILLINFO|POID' class ='output_edit'></div>");
				
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
				ADDRESS.setEditField("<div id='NAMEINFO|ADDRESS' class ='output_edit'></div>");
				
				//CANON_COUNTRY
				InputFlistResponse CANON_COUNTRY = new InputFlistResponse();
				CANON_COUNTRY.setName("CANON_COUNTRY");
				CANON_COUNTRY.setDefaultValue(nameInfo.CANON_COUNTRY);
				CANON_COUNTRY.setEditField("<div id='NAMEINFO|CANON_COUNTRY' class ='output_edit'></div>");
				
				//CITY
				InputFlistResponse CITY = new InputFlistResponse();
				CITY.setName("CITY");
				CITY.setDefaultValue(nameInfo.CITY);
				CITY.setEditField("<div id='NAMEINFO|CITY' class ='output_edit'></div>");
				
				//COMPANY
				InputFlistResponse COMPANY = new InputFlistResponse();
				COMPANY.setName("COMPANY");
				COMPANY.setDefaultValue(nameInfo.COMPANY);
				COMPANY.setEditField("<div id='NAMEINFO|COMPANY' class ='output_edit'></div>");
				
				//CONTACT_TYPE
				InputFlistResponse CONTACT_TYPE = new InputFlistResponse();
				CONTACT_TYPE.setName("CONTACT_TYPE");
				CONTACT_TYPE.setDefaultValue(nameInfo.CONTACT_TYPE);
				CONTACT_TYPE.setEditField("<div id='NAMEINFO|CONTACT_TYPE' class ='output_edit'></div>");
				
				//COUNTRY
				InputFlistResponse COUNTRY = new InputFlistResponse();
				COUNTRY.setName("COUNTRY");
				COUNTRY.setDefaultValue(nameInfo.COUNTRY);
				COUNTRY.setEditField("<div id='NAMEINFO|COUNTRY' class ='output_edit'></div>");
				
				//ELEMENT_ID
				InputFlistResponse ELEMENT_ID = new InputFlistResponse();
				ELEMENT_ID.setName("ELEMENT_ID");
				ELEMENT_ID.setDefaultValue(nameInfo.ELEMENT_ID);
				ELEMENT_ID.setEditField("<div id='NAMEINFO|ELEMENT_ID' class ='output_edit'></div>");
				
				//EMAIL_ADDR
				InputFlistResponse EMAIL_ADDR = new InputFlistResponse();
				EMAIL_ADDR.setName("EMAIL_ADDR");
				EMAIL_ADDR.setDefaultValue(nameInfo.EMAIL_ADDR);
				EMAIL_ADDR.setEditField("<div id='NAMEINFO|EMAIL_ADDR' class ='output_edit'></div>");
				
				//FIRST_NAME
				InputFlistResponse FIRST_NAME = new InputFlistResponse();
				FIRST_NAME.setName("FIRST_NAME");
				FIRST_NAME.setDefaultValue(nameInfo.FIRST_NAME);
				FIRST_NAME.setEditField("<div id='NAMEINFO|FIRST_NAME' class ='output_edit'></div>");
				
				//LAST_NAME
				InputFlistResponse LAST_NAME = new InputFlistResponse();
				LAST_NAME.setName("LAST_NAME");
				LAST_NAME.setDefaultValue(nameInfo.LAST_NAME);
				LAST_NAME.setEditField("<div id='NAMEINFO|LAST_NAME' class ='output_edit'></div>");
				
				//MIDDLE_NAME
				InputFlistResponse MIDDLE_NAME = new InputFlistResponse();
				MIDDLE_NAME.setName("MIDDLE_NAME");
				MIDDLE_NAME.setDefaultValue(nameInfo.MIDDLE_NAME);
				MIDDLE_NAME.setEditField("<div id='NAMEINFO|MIDDLE_NAME' class ='output_edit'></div>");
				
				//PHONES
				InputFlistResponse PHONES = new InputFlistResponse();
				PHONES.setName("PHONES");
				
				//PHONE
				InputFlistResponse PHONE = new InputFlistResponse();
				PHONE.setName("PHONE");
				PHONE.setDefaultValue(nameInfo.PHONES.PHONE);
				PHONE.setEditField("<div id='NAMEINFO|PHONES|PHONE' class ='output_edit'></div>");
				//TYPE
				InputFlistResponse TYPE = new InputFlistResponse();
				TYPE.setName("TYPE");
				TYPE.setDefaultValue(nameInfo.PHONES.TYPE);
				TYPE.setEditField("<div id='NAMEINFO|PHONES|TYPE' class ='output_edit'></div>");
				
				PHONES.setChildren(Arrays.asList(PHONE, TYPE));
				
				//SALUTATION
				InputFlistResponse SALUTATION = new InputFlistResponse();
				SALUTATION.setName("SALUTATION");
				SALUTATION.setDefaultValue(nameInfo.SALUTATION);
				SALUTATION.setEditField("<div id='NAMEINFO|SALUTATION' class ='output_edit'></div>");
				
				//STATE
				InputFlistResponse STATE = new InputFlistResponse();
				STATE.setName("STATE");
				STATE.setDefaultValue(nameInfo.STATE);
				STATE.setEditField("<div id='NAMEINFO|STATE' class ='output_edit'></div>");
				
				//ZIP
				InputFlistResponse ZIP = new InputFlistResponse();
				ZIP.setName("ZIP");
				ZIP.setDefaultValue(nameInfo.ZIP);
				ZIP.setEditField("<div id='NAMEINFO|ZIP' class ='output_edit'></div>");
				
				NAMEINFO.setChildren(Arrays.asList(ADDRESS, CANON_COUNTRY, CITY, COMPANY, CONTACT_TYPE, COUNTRY, 
						ELEMENT_ID, EMAIL_ADDR, FIRST_NAME, LAST_NAME, MIDDLE_NAME, PHONES, SALUTATION, STATE, ZIP));
				
				
				flistResponse.setChildren(Arrays.asList(acttInfoResponse, balInfoObjResponse, billInfoObjResponse, NAMEINFO));
				
				return Arrays.asList(flistResponse);
			}
		}

		return null;
	}
	
	public List<String> getLogs(String scenarioId) {
		
		String tomcatHome = System.getProperty("user.home");
		String path = tomcatHome + scenarioFilePath + "\\" + scenarioId;
		
		if(isLinux) {
			 path = tomcatHome + linuxScenarioFilePath + "/" + scenarioId;
		}

		System.out.println("Getting logs from path : " + path);

		File directory = new File(path);

		FileFilter filter = new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".txt");
			}
		};
		
		List<String> logFileNames = new ArrayList<String>();
		File[] logFiles = directory.listFiles(filter);
		for(File logFile : logFiles) {
			logFileNames.add(logFile.getAbsolutePath());
		}
		
		return logFileNames;
	}
	
	private void addElementToXml(String fileName, Map<String, String> outputValues) throws Exception {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(fileName);
		Element root = document.getDocumentElement();
		
		String name = "${%s}";
		String value = "${OUTPUT:THIS:%s}";
		
		for(Map.Entry<String, String> entry : outputValues.entrySet()) {
			if(entry.getValue() != null && !entry.getValue().isEmpty()) {
				Element newServer = document.createElement("var");
				newServer.setAttribute("name", String.format(name, entry.getValue()));
				newServer.setAttribute("value", String.format(value, entry.getKey().replace("|", "/")));
				root.appendChild(newServer);
			}
		}


		DOMSource source = new DOMSource(document);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result = new StreamResult(fileName);
		transformer.transform(source, result);
	}
	
	public String editScenarioInput(String actionId, String scenarioId, EditedOutputList editedOutputList) throws Exception {
		String tomcatHome = System.getProperty("user.home");

		String actionPath = tomcatHome + scenarioFilePath + "\\" + scenarioId + "\\" + actionId + ".xml"; 
		if(isLinux) {
			actionPath = tomcatHome + linuxScenarioFilePath + "/" + scenarioId + "/" + actionId + ".xml";
		}

		Action action = buintUtil.convertXmlToAction(new File(actionPath));

		Map<String, String> values = editedOutputList.getValues();
		//KeyNameEnum
		KeyNameEnum keyNameEnum = null;
		for(Map.Entry<String, String> entry : values.entrySet()) {
			if(entry.getValue() != null && !entry.getValue().isEmpty()) {
				System.out.println(action.INPUT.FLIST);
				try {
					keyNameEnum = KeyNameEnum.valueOf(entry.getKey());
				} catch(Exception e) {
					keyNameEnum = KeyNameEnum.DEFAULT;
				}

				switch(keyNameEnum) {

				case POID : {
					action.INPUT.FLIST.POID = entry.getValue();
					System.out.println("POID : " + entry.getValue());
					break;
				}

				case PROGRAM_NAME : {
					action.INPUT.FLIST.PROGRAM_NAME = entry.getValue();
					System.out.println("PROGRAM_NAME : " + entry.getValue());
					break;
				}

				case SERVICE_OBJ : {
					action.INPUT.FLIST.SERVICE_OBJ = entry.getValue();
					System.out.println("SERVICE_OBJ : " + entry.getValue());
					break;
				}
				default:
					break;
				}
				if (entry.getKey().startsWith("USAGE")) {
					String[] keys = entry.getKey().split(",");
					if(keys == null || keys.length < 2) {
						continue;
					}

					String currentKey = keys[0];
					Integer currentProductIndex = Integer.parseInt(keys[1]);
					List<Products> productsToEdit = action.INPUT.FLIST.DEAL_INFO.PRODUCTS;
					Products product = productsToEdit.get(currentProductIndex);
					try {
						keyNameEnum = KeyNameEnum.valueOf(currentKey);
					} catch(Exception e) {
						keyNameEnum = KeyNameEnum.DEFAULT;
					}
					switch(keyNameEnum) {

					case USAGE_END_OFFSET : {
						product.USAGE_END_OFFSET = entry.getValue();
						break;
					}

					case USAGE_END_UNIT : {
						product.USAGE_END_UNIT = entry.getValue();
						break;
					}
					default:
						break;
					}
				}
			}
		}

		buintUtil.convertActionToXml(action, actionPath);

		return "Input FList edited successfully";
	}
	
}
