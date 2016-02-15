package com.bunit.ui.xml.to;

public class Data {
	
	public String ID;
	public String DESCRIPTION;
	public String OPCODE;
	public String SCENARIOID	;
	public String TYPE;
	public String GROUP;
	public String STATUS;
	
	public Data() {
		
	}

	public Data(String iD, String dESCRIPTION, String oPCODE,
			String sCENARIOID, String tYPE, String gROUP, String sTATUS) {
		super();
		this.ID = iD;
		this.DESCRIPTION = dESCRIPTION;
		this.OPCODE = oPCODE;
		this.SCENARIOID = sCENARIOID;
		this.TYPE = tYPE;
		this.GROUP = gROUP;
		this.STATUS = sTATUS;
	}
}
