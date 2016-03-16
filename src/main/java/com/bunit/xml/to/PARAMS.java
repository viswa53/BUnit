package com.bunit.xml.to;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PARAMS")
public class PARAMS {
	
	@XmlElement(name = "PLAN")
	public String PLAN;
	
	@XmlElement(name = "SERVICE")
	public String SERVICE;
	
	@XmlAttribute(name = "taxable")
	public String taxable;
	
	public PARAMS(String pLAN, String sERVICE, String taxable) {
		super();
		PLAN = pLAN;
		SERVICE = sERVICE;
		this.taxable = taxable;
	}

	public PARAMS() {
		
	}
}
