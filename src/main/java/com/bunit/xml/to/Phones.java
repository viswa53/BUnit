package com.bunit.xml.to;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PHONES")
public class Phones {
	
	@XmlAttribute(name = "elem")
	public String elem;
	
	@XmlElement(name = "PHONE")
	public String PHONE;
	
	@XmlElement(name = "TYPE")
	public String TYPE;
	
	public Phones() {
		
	}

	public Phones(String elem, String pHONE, String tYPE) {
		super();
		this.elem = elem;
		PHONE = pHONE;
		TYPE = tYPE;
	}
}
