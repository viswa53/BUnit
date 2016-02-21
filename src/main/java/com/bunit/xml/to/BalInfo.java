package com.bunit.xml.to;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BAL_INFO")
public class BalInfo {
	
	@XmlElement(name = "BILLINFO_OBJ")
	public String BILLINFO_OBJ;
	
	@XmlElement(name = "NAME")
	public String NAME;
	
	@XmlElement(name = "POID")
	public String POID;
	
	@XmlAttribute(name = "elem")
	public String elem;

	public BalInfo(String bILLINFO_OBJ, String nAME, String pOID, String elem) {
		super();
		BILLINFO_OBJ = bILLINFO_OBJ;
		NAME = nAME;
		POID = pOID;
		this.elem = elem;
	}
    
	public BalInfo() {
		
	}
}
