package com.bunit.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

import com.bunit.xml.to.Action;
import com.bunit.xml.to.Scenario;

@Component
public class BuintUtil {

	public File jaxbScenarioToXML(Scenario scenario, String fileName) {
		File newScenarioFile = null;
		try {
			JAXBContext context = JAXBContext.newInstance(Scenario.class);
			Marshaller m = context.createMarshaller();
			//for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			// m.marshal(emp, System.out);

			// Write to File
			newScenarioFile = new File(fileName);  
			m.marshal(scenario, newScenarioFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return newScenarioFile;
	}

	public Action convertXmlToAction(File file) throws Exception {

		JAXBContext jaxbContext = JAXBContext.newInstance(Action.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Action action = (Action) jaxbUnmarshaller.unmarshal(file);

		return action;
	}

	public Scenario convertXmlToScenario(File file) throws Exception {

		JAXBContext jaxbContext = JAXBContext.newInstance(Scenario.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Scenario scenario = (Scenario) jaxbUnmarshaller.unmarshal(file);

		return scenario;
	}
	
	public void convertActionToXml(Action action, String fileName) {
		File newScenarioFile = null;
		try {
			JAXBContext context = JAXBContext.newInstance(Action.class);
			Marshaller m = context.createMarshaller();
			//for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to File
			newScenarioFile = new File(fileName);  
			m.marshal(action, newScenarioFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
