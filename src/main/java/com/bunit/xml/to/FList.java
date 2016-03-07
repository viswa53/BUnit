package com.bunit.xml.to;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="FLIST")
public class FList {
	
	public DealInfo DEAL_INFO;
	
	public String POID;

	public String PROGRAM_NAME;

	public String SERVICE_OBJ;
	
	public String ACCOUNT_OBJ;
	
	public String END_T;
	
	public AcctInfo ACCTINFO;
	
	public BalInfo BAL_INFO;
	
	public BillInfo BILLINFO;
	
	public NameInfo NAMEINFO;

	public FList() {
		
	}

	public FList(DealInfo dEAL_INFO, String pOID, String pROGRAM_NAME,
			String sERVICE_OBJ, String aCCOUNT_OBJ, String eND_T,
			AcctInfo aCCTINFO, BalInfo bAL_INFO, BillInfo bILLINFO,
			NameInfo nAMEINFO) {
		super();
		DEAL_INFO = dEAL_INFO;
		POID = pOID;
		PROGRAM_NAME = pROGRAM_NAME;
		SERVICE_OBJ = sERVICE_OBJ;
		ACCOUNT_OBJ = aCCOUNT_OBJ;
		END_T = eND_T;
		ACCTINFO = aCCTINFO;
		BAL_INFO = bAL_INFO;
		BILLINFO = bILLINFO;
		NAMEINFO = nAMEINFO;
	}

	@Override
	public String toString() {
		return "FList [DEAL_INFO=" + DEAL_INFO + ", POID=" + POID + ", PROGRAM_NAME=" + PROGRAM_NAME + ", SERVICE_OBJ="
				+ SERVICE_OBJ + ", ACCOUNT_OBJ=" + ACCOUNT_OBJ + ", END_T=" + END_T + ", ACCTINFO=" + ACCTINFO
				+ ", BAL_INFO=" + BAL_INFO + ", BILLINFO=" + BILLINFO + ", NAMEINFO=" + NAMEINFO + "]";
	}
	
	
}
