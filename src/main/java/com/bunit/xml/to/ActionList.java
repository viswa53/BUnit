package com.bunit.xml.to;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="ACTIONLIST")
public class ActionList {
	
	public List<Action> ACTION;
	
	public ActionList() {
		
	}

	public ActionList(List<Action> aCTION) {
		super();
		ACTION = aCTION;
	}
}
