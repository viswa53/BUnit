package com.bunit.ui;


import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bunit.ui.xml.service.BUnitService;
import com.bunit.ui.xml.to.Scenario;
import com.bunit.ui.xml.util.BuintUtil;
import com.google.gson.Gson;

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
	public Response getActions() throws Exception {

		BUnitService unitService  = new BUnitService();

		Gson gson = new Gson();
//		String jsonString = gson.toJson(unitService.getActions());
		String jsonString = "{\"total\":7,\"rows\":[{\"name\":\"Create Acct with Default Plan and Paytype\",\"value\":\"PCM_OP_CUST_COMMIT_CUSTOMER\",\"group\":\"Acct Mgmt Opcodes\",\"editor\":\"label\"},{\"name\":\"AddOn Deal\",\"value\":\"PCM_OP_ADD_DEAL\",\"group\":\"Acct Mgmt Opcodes\",\"editor\":\"label\"},	{\"name\":\"Search Items\",\"value\":\"PCM_OP_SEARCH\",\"group\":\"Search Opcodes\",\"editor\":\"label\"},	{\"name\":\"Search Plans\",\"value\":\"PCM_OP_SEARCH\",\"group\":\"Search Opcodes\",\"editor\":\"label\"},	{\"name\":\"Read Functions\",\"value\":\"PCM_OP_READ_OBJ\",\"group\":\"Read Storable Object\",\"editor\":\"label\"},	{\"name\":\"Billing\",\"value\":\"PCM_OP_BILL_MAKE_BILL\",\"group\":\"Bill Mgmt\",\"editor\":\"label\"},	{\"name\":\"Payment\",\"value\":\"PCM_OP_PAYMENT_COLLECT\",\"group\":\"Payment Mgmt\",\"editor\":\"label\"},	{\"name\":\"AR Activity\",\"value\":\"PCM_OP_AR_ITEM_ADJUSTMENT\",\"group\":\"Payment Mgmt\",\"editor\":\"label\"}]}";
		return Response.ok().entity(jsonString).build();
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
