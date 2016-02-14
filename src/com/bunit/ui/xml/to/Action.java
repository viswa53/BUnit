package com.bunit.ui.xml.to;

public class Action {
	
	public String ID;
	
	public String DESCRIPTION;
	
	public String GROUP;
	
	public String STATUS;
	
	public Action(){
		
	}
	public Action(String iD, String dESCRIPTION, String gROUP,
			String sTATUS) {
		this.ID = iD;
		this.DESCRIPTION = dESCRIPTION;
		this.GROUP = gROUP;
		this.STATUS = sTATUS;
	}
}
