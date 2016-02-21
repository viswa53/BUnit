package com.bunit.xml.to;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "NAMEINFO")
public class NameInfo {
	
	@XmlAttribute(name = "elem")
	public String elem;
	
	@XmlElement(name = "ADDRESS")
	public String ADDRESS;
	
	@XmlElement(name = "CANON_COUNTRY")
	public String CANON_COUNTRY;
	
	@XmlElement(name = "CITY")
	public String CITY;
	
	@XmlElement(name = "COMPANY")
	public String COMPANY;
	
	@XmlElement(name = "CONTACT_TYPE")
	public String CONTACT_TYPE;
	
	@XmlElement(name = "COUNTRY")
	public String COUNTRY;
	
	@XmlElement(name = "ELEMENT_ID")
	public String ELEMENT_ID;
	
	@XmlElement(name = "EMAIL_ADDR")
	public String EMAIL_ADDR;
	
	@XmlElement(name = "FIRST_NAME")
	public String FIRST_NAME;
	
	@XmlElement(name = "LAST_NAME")
	public String LAST_NAME;
	
	@XmlElement(name = "MIDDLE_NAME")
	public String MIDDLE_NAME;
	
	@XmlElement(name = "PHONES")
	public Phones PHONES;
	
	@XmlElement(name = "SALUTATION")
	public String SALUTATION;
	
	@XmlElement(name = "STATE")
	public String STATE; 	
	
	@XmlElement(name = "ZIP")
	public String ZIP; 	
	
	public NameInfo() {
		
	}

	public NameInfo(String elem, String aDDRESS, String cANON_COUNTRY,
			String cITY, String cOMPANY, String cONTACT_TYPE, String cOUNTRY,
			String eLEMENT_ID, String eMAIL_ADDR, String fIRST_NAME,
			String lAST_NAME, String mIDDLE_NAME, Phones pHONES,
			String sALUTATION, String sTATE, String zIP) {
		super();
		this.elem = elem;
		ADDRESS = aDDRESS;
		CANON_COUNTRY = cANON_COUNTRY;
		CITY = cITY;
		COMPANY = cOMPANY;
		CONTACT_TYPE = cONTACT_TYPE;
		COUNTRY = cOUNTRY;
		ELEMENT_ID = eLEMENT_ID;
		EMAIL_ADDR = eMAIL_ADDR;
		FIRST_NAME = fIRST_NAME;
		LAST_NAME = lAST_NAME;
		MIDDLE_NAME = mIDDLE_NAME;
		PHONES = pHONES;
		SALUTATION = sALUTATION;
		STATE = sTATE;
		ZIP = zIP;
	}
}
