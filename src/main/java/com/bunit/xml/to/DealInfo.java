package com.bunit.xml.to;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="DEAL_INFO")
public class DealInfo {
	
	public Products PRODUCTS;

	public DealInfo(Products pRODUCTS) {
		super();
		PRODUCTS = pRODUCTS;
	}
	
	public DealInfo() {
		
	}
}
