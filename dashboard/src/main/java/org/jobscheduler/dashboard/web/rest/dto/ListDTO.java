package org.jobscheduler.dashboard.web.rest.dto;

import java.util.List;

/**
 * DTO to transfert a list and the number of elements inside this list
 *  
 * @author Cédric
 *
 */
public class ListDTO {

	// Number of elements
	public Long totalElements;
	// Number of pages
	public int totalPages;
	
	
	
	public List<?> result;

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
		
	}


	
}
