package org.jobscheduler.dashboard.controller;

import java.io.Serializable;

public class TopFields implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int top ; 
	protected String start ;
	protected String end ;
	
	
	public TopFields() {
		
	}


	public int getTop() {
		return top;
	}


	public void setTop(int top) {
		this.top = top;
	}


	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getEnd() {
		return end;
	}


	public void setEnd(String end) {
		this.end = end;
	}

}
