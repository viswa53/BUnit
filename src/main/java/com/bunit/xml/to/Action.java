package com.bunit.xml.to;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name ="ACTION")
public class Action {

	public Data DATA;

	public Input INPUT;
	
	public Output OUTPUT;
	
	public String ID;
	
	public String DESCRIPTION;
	
	public String GROUP;
	
	public String STATUS;
	
	public String actionFileName;

	public Action(){

	}

	public Action(Data dATA, Input iNPUT, Output oUTPUT, String iD,
			String dESCRIPTION, String gROUP, String sTATUS,String actionFileName) {
		super();
		DATA = dATA;
		INPUT = iNPUT;
		OUTPUT = oUTPUT;
		ID = iD;
		DESCRIPTION = dESCRIPTION;
		GROUP = gROUP;
		STATUS = sTATUS;
		this.actionFileName = actionFileName;
	}

	@Override
	public String toString() {
		return "Action [DATA=" + DATA + ", INPUT=" + INPUT + ", OUTPUT="
				+ OUTPUT + ", ID=" + ID + ", DESCRIPTION=" + DESCRIPTION
				+ ", GROUP=" + GROUP + ", STATUS=" + STATUS
				+ ", actionFileName=" + actionFileName + "]";
	}
	
	
}
