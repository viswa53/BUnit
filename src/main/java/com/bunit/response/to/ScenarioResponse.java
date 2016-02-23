package com.bunit.response.to;

import java.util.List;

public class ScenarioResponse {

	private Integer total;

	private List<ScenarioInfo> rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<ScenarioInfo> getRows() {
		return rows;
	}

	public void setRows(List<ScenarioInfo> rows) {
		this.rows = rows;
	}
}
