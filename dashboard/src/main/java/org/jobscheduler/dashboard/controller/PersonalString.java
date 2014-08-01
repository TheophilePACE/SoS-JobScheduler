package org.jobscheduler.dashboard.controller;

import java.io.Serializable;

public class PersonalString implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1932591922203864527L;
	private String myString ;

	public PersonalString(String myString) {
		super();
		this.myString = myString;
	}

	public String getMyString() {
		return myString;
	}

	public void setMyString(String myString) {
		this.myString = myString;
	}
	
	
	
	
}
