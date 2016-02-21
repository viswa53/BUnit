package com.bunit.xml.to;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ACCTINFO")
public class AcctInfo {
	
	@XmlElement(name = "POID")
	public String POID;
	
	@XmlAttribute(name = "elem")
	public String elem;
	
	public AcctInfo() {
		
	}

	public AcctInfo(String pOID, String elem) {
		super();
		POID = pOID;
		this.elem = elem;
	}
}
