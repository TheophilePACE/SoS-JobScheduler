package org.jobscheduler.dashboard.model;

import javax.persistence.Embeddable;

@Embeddable
public class Fields {

	String field;
	String value;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
