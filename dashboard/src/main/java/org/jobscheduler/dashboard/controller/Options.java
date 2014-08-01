package org.jobscheduler.dashboard.controller;

import java.io.Serializable;

public class Options implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean scaleShowGridLines ;
  String  scaleGridLineColor ;
  int scaleGridLineWidth ;
 boolean bezierCurve ;
  double bezierCurveTension;
  boolean   pointDot ;
 int     pointDotRadius ;
   int  pointDotStrokeWidth ;
 int pointHitDetectionRadius ;
 boolean datasetStroke ;
 int datasetStrokeWidth ;
boolean datasetFill ;
String  legendTemplate ;


public Options(){
	
}


public boolean isScaleShowGridLines() {
	return scaleShowGridLines;
}


public void setScaleShowGridLines(boolean scaleShowGridLines) {
	this.scaleShowGridLines = scaleShowGridLines;
}


public String getScaleGridLineColor() {
	return scaleGridLineColor;
}


public void setScaleGridLineColor(String scaleGridLineColor) {
	this.scaleGridLineColor = scaleGridLineColor;
}


public int getScaleGridLineWidth() {
	return scaleGridLineWidth;
}


public void setScaleGridLineWidth(int scaleGridLineWidth) {
	this.scaleGridLineWidth = scaleGridLineWidth;
}


public boolean isBezierCurve() {
	return bezierCurve;
}


public void setBezierCurve(boolean bezierCurve) {
	this.bezierCurve = bezierCurve;
}


public double getBezierCurveTension() {
	return bezierCurveTension;
}


public void setBezierCurveTension(double bezierCurveTension) {
	this.bezierCurveTension = bezierCurveTension;
}


public boolean isPointDot() {
	return pointDot;
}


public void setPointDot(boolean pointDot) {
	this.pointDot = pointDot;
}


public int getPointDotRadius() {
	return pointDotRadius;
}


public void setPointDotRadius(int pointDotRadius) {
	this.pointDotRadius = pointDotRadius;
}


public int getPointDotStrokeWidth() {
	return pointDotStrokeWidth;
}


public void setPointDotStrokeWidth(int pointDotStrokeWidth) {
	this.pointDotStrokeWidth = pointDotStrokeWidth;
}


public int getPointHitDetectionRadius() {
	return pointHitDetectionRadius;
}


public void setPointHitDetectionRadius(int pointHitDetectionRadius) {
	this.pointHitDetectionRadius = pointHitDetectionRadius;
}


public boolean isDatasetStroke() {
	return datasetStroke;
}


public void setDatasetStroke(boolean datasetStroke) {
	this.datasetStroke = datasetStroke;
}


public int getDatasetStrokeWidth() {
	return datasetStrokeWidth;
}


public void setDatasetStrokeWidth(int datasetStrokeWidth) {
	this.datasetStrokeWidth = datasetStrokeWidth;
}


public boolean isDatasetFill() {
	return datasetFill;
}


public void setDatasetFill(boolean datasetFill) {
	this.datasetFill = datasetFill;
}


public String getLegendTemplate() {
	return legendTemplate;
}


public void setLegendTemplate(String legendTemplate) {
	this.legendTemplate = legendTemplate;
}


public static long getSerialversionuid() {
	return serialVersionUID;
}





}
