package org.jobscheduler.dashboard.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="filters")
public class Filter {
	
	@Id
	String name;
	@Embedded
	Fields[] fields;
	
	public Filter addField(String field, String value) {
		//fields.s
		return this;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Fields[] getFields() {
		return fields;
	}
	public void setFields(Fields[] fields) {
		this.fields = fields;
	}
	


}
