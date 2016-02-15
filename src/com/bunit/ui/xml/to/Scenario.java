package com.bunit.ui.xml.to;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SCENARIO")
public class Scenario {
	
	@XmlElement(name = "SCENARIOID")
	public String SCENARIOID;
	
	@XmlElement(name = "DESC")
	public String DESC;
	
	@XmlElement(name = "DATE")
	public String DATE;
	
	@XmlElement(name = "USER")
	public String USER;
	
	@XmlElement(name = "STATUS")
	public String STATUS;
	
	@XmlElement(name = "ACTIONLIST")
	public ActionList ACTIONLIST;
	
	public Scenario(){
		
	}

	public Scenario(String SCENARIOID, String DESC, String DATE, String USER,
			String STATUS, ActionList ACTIONLIST) {
		
		this.SCENARIOID = SCENARIOID;
		this.DESC = DESC;
		this.DATE = DATE;
		this.USER = USER;
		this.STATUS = STATUS;
		this.ACTIONLIST = ACTIONLIST;
	}
}
