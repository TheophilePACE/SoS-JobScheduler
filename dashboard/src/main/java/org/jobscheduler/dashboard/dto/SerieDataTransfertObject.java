package org.jobscheduler.dashboard.dto;

import java.util.List;

/**
 * DTO to transfert a serie
 *  
 * @author Cédric
 *
 */
public class SerieDataTransfertObject {

	public String key;

	public List<?> values;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<?> getValues() {
		return values;
	}

	public void setValues(List<?> values) {
		this.values = values;
	}





	
}
