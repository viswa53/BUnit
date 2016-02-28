package com.bunit.xml.to;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="DEAL_INFO")
public class DealInfo {
	
	public List<Products> PRODUCTS;

	public DealInfo(List<Products> pRODUCTS) {
		super();
		PRODUCTS = pRODUCTS;
	}
	
	public DealInfo() {
		
	}
}
