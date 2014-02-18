package org.jobscheduler.dashboard.dto;

import java.util.List;

/**
 * DTO to transfert a list and the number of elements inside this list
 *  
 * @author Cédric
 *
 */
public class ListDataTransfertObject {

	public Long total;
	
	public List<?> result;

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}


	
}
