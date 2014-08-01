package org.jobscheduler.dashboard.controller;

import java.io.Serializable;

public class DataUnit implements Serializable {
	
//	
//	 label: "My First dataset",
//     fillColor: "rgba(220,220,220,0.2)",
//     strokeColor: "rgba(220,220,220,1)",
//     pointColor: "rgba(220,220,220,1)",
//     pointStrokeColor: "#fff",
//     pointHighlightFill: "#fff",
//     pointHighlightStroke: "rgba(220,220,220,1)",
//     data: [65, 59, 80, 81, 56, 55, 40]
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String label ;
	
	private String fillColor ; 
	
	private String pointColor ; 
	
	private String  strokeColor ;
	
	public String getStrokeColor() {
		return strokeColor;
	}


	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}


	private String pointStrokeColor ;
	
	private String pointHighlightFill ;
	
	private String pointHighlightStroke ;
	
	private double[] data ;

	






	


	public DataUnit(){
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getFillColor() {
		return fillColor;
	}


	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}


	public String getPointColor() {
		return pointColor;
	}


	public void setPointColor(String pointColor) {
		this.pointColor = pointColor;
	}


	public String getPointStrokeColor() {
		return pointStrokeColor;
	}


	public void setPointStrokeColor(String pointStrokeColor) {
		this.pointStrokeColor = pointStrokeColor;
	}


	public String getPointHighlightFill() {
		return pointHighlightFill;
	}


	public void setPointHighlightFill(String pointHighlightFill) {
		this.pointHighlightFill = pointHighlightFill;
	}


	public String getPointHighlightStroke() {
		return pointHighlightStroke;
	}


	public void setPointHighlightStroke(String pointHighlightStroke) {
		this.pointHighlightStroke = pointHighlightStroke;
	}


	public double[] getData() {
		return data;
	}


	public void setData(double[] data) {
		this.data = data;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

}
