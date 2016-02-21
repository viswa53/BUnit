package com.bunit.xml.to;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BILLINFO")
public class BillInfo {

	@XmlAttribute(name = "elem")
	public String elem;

	@XmlElement(name = "BAL_GRP_OBJ")
	public String BAL_GRP_OBJ;

	@XmlElement(name = "BILLINFO_ID")
	public String BILLINFO_ID;

	@XmlElement(name = "BILL_WHEN")
	public String BILL_WHEN;

	@XmlElement(name = "CURRENCY")
	public String CURRENCY;

	@XmlElement(name = "CURRENCY_SECONDARY")
	public String CURRENCY_SECONDARY;

	@XmlElement(name = "EFFECTIVE_T")
	public String EFFECTIVE_T;

	@XmlElement(name = "PAYINFO_OBJ")
	public String PAYINFO_OBJ;

	@XmlElement(name = "STATUS")
	public String STATUS;

	@XmlElement(name = "PAY_TYPE")
	public String PAY_TYPE;

	@XmlElement(name = "POID")
	public String POID;

	public BillInfo() {

	}

	public BillInfo(String elem, String bAL_GRP_OBJ, String bILLINFO_ID,
			String bILL_WHEN, String cURRENCY, String cURRENCY_SECONDARY,
			String eFFECTIVE_T, String pAYINFO_OBJ, String sTATUS, String pAY_TYPE,
			String pOID) {
		super();
		this.elem = elem;
		BAL_GRP_OBJ = bAL_GRP_OBJ;
		BILLINFO_ID = bILLINFO_ID;
		BILL_WHEN = bILL_WHEN;
		CURRENCY = cURRENCY;
		CURRENCY_SECONDARY = cURRENCY_SECONDARY;
		EFFECTIVE_T = eFFECTIVE_T;
		PAYINFO_OBJ = pAYINFO_OBJ;
		STATUS = sTATUS;
		PAY_TYPE = pAY_TYPE;
		POID = pOID;
	}
}
