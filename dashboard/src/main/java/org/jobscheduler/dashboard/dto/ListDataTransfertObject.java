package org.jobscheduler.dashboard.dto;

import java.util.List;

public class ListDataTransfertObject {

	public Integer total;
	
	public List<?> result;

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}


	
}
