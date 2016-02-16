package com.bunit.ui.xml.to;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name ="ACTION")
public class Action {

	public Data DATA;

	public Input INPUT;
	
	public String ID;
	
	public String DESCRIPTION;
	
	public String GROUP;
	
	public String STATUS;

	public Action(){

	}

	public Action(Data dATA, Input iNPUT, String iD, String dESCRIPTION,
			String gROUP, String sTATUS) {
		super();
		DATA = dATA;
		INPUT = iNPUT;
		ID = iD;
		DESCRIPTION = dESCRIPTION;
		GROUP = gROUP;
		STATUS = sTATUS;
	}
}
