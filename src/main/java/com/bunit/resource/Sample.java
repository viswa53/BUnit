package com.bunit.resource;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Sample {
	public static void main(String[] args) throws Exception {
		 
		String fileName = "E:\\tom\\BUNIT\\Scenarios\\BRMTestScenario2\\BRMTestScenario2.xml";
		Map<String, String> map = new HashMap<String, String>();
		map.put("Viswa", "Rupa");
		
		addElementToXml(fileName, map);
		
		System.out.println(System.getProperty("user.home"));
	}
	
	public static void addElementToXml(String fileName, Map<String, String> outputValues) throws Exception {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(fileName);
		Element root = document.getDocumentElement();
		
		NodeList list  = document.getElementsByTagName("ACTIONLIST");
		
		 NodeList nodes = document.getElementsByTagName("ACTIONLIST");
        Element element = (Element) nodes.item(0);
        NodeList movieList = element.getElementsByTagName("ACTION");
        Element movieElement = (Element) movieList.item(0);
		
		String name = "${%s}";
		String value = "${OUTPUT:THIS:%s}";
		
		for(Map.Entry<String, String> entry : outputValues.entrySet()) {
			if(entry.getValue() != null && !entry.getValue().isEmpty()) {
				Element newServer = document.createElement("var");
				newServer.setAttribute("name", String.format(name, entry.getValue()));
				newServer.setAttribute("value", String.format(value, entry.getKey().replace("|", "/")));
				movieElement.appendChild(newServer);
			}
		}


		DOMSource source = new DOMSource(document);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result = new StreamResult(fileName);
		transformer.transform(source, result);
	}
}
