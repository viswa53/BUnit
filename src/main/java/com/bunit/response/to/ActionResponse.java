package com.bunit.response.to;

import java.util.List;

import com.bunit.response.to.ActionInfo;

public class ActionResponse {
	
	private Integer total;
	
	private List<ActionInfo> rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<ActionInfo> getRows() {
		return rows;
	}

	public void setRows(List<ActionInfo> actionList) {
		this.rows = actionList;
	}
}

