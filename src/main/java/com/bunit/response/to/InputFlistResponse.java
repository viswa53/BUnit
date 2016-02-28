package com.bunit.response.to;

import java.util.List;

public class InputFlistResponse {
	
	private String name;
	
	private String defaultValue;
	
	private String editField;
	
	private List<InputFlistResponse> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getEditField() {
		return editField;
	}

	public void setEditField(String editField) {
		this.editField = editField;
	}

	public List<InputFlistResponse> getChildren() {
		return children;
	}

	public void setChildren(List<InputFlistResponse> children) {
		this.children = children;
	}
}
