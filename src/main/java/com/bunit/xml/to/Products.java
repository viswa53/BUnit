package com.bunit.xml.to;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PRODUCTS")
public class Products {
	
	@XmlElement(name = "USAGE_END_OFFSET")
	public String USAGE_END_OFFSET;
	
	@XmlElement(name = "USAGE_END_UNIT")
	public String USAGE_END_UNIT;
	
	@XmlAttribute(name = "elem")
	public String elem;

	public Products(String uSAGE_END_OFFSET, String uSAGE_END_UNIT, String elem) {
		super();
		USAGE_END_OFFSET = uSAGE_END_OFFSET;
		USAGE_END_UNIT = uSAGE_END_UNIT;
		this.elem = elem;
	}
	
	public Products() {
		
	}
}
