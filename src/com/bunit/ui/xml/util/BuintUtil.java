package com.bunit.ui.xml.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.bunit.ui.xml.to.Action;
import com.bunit.ui.xml.to.SampleAction;
import com.bunit.ui.xml.to.Scenario;

public class BuintUtil {
	
	public void jaxbObjectToXML(SampleAction action, String fileName) {
		 
        try {
            JAXBContext context = JAXBContext.newInstance(SampleAction.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
            // Write to System.out for debugging
            // m.marshal(emp, System.out);
 
            // Write to File
            m.marshal(action, new File(fileName));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
	
	public SampleAction convertXmlToObject(File file) throws Exception {

		JAXBContext jaxbContext = JAXBContext.newInstance(SampleAction.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		SampleAction action = (SampleAction) jaxbUnmarshaller.unmarshal(file);

		return action;
	}
	
	public Scenario convertXmlToScenario(File file) throws Exception {

		JAXBContext jaxbContext = JAXBContext.newInstance(Scenario.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Scenario scenario = (Scenario) jaxbUnmarshaller.unmarshal(file);

		return scenario;
	}
}
