package org.jobscheduler.dashboard.controller;

import java.io.Serializable;

public class Data implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataUnit[] datasets ;
	private String[] labels ;
	private DataUnit[] datasets_Y1 ;
	public DataUnit[] getDatasets_Y1() {
		return datasets_Y1;
	}

	public void setDatasets_Y1(DataUnit[] datasets_Y1) {
		this.datasets_Y1 = datasets_Y1;
	}

	public DataUnit[] getDatasets_Y2() {
		return datasets_Y2;
	}

	public void setDatasets_Y2(DataUnit[] datasets_Y2) {
		this.datasets_Y2 = datasets_Y2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	private DataUnit[] datasets_Y2 ;
	public Data(DataUnit[] datasets, String[] labels) {
		super();
		this.datasets = datasets;
		this.labels = labels;
	}
	
	public Data(DataUnit datasets1,DataUnit datasets2,int size, String[] labels) {
		super();
		datasets_Y2 = new DataUnit[size] ;
		datasets_Y1 = new DataUnit[size] ;
		this.labels = labels;
		this.datasets_Y1[0] = datasets1 ;
		this.datasets_Y2[0] = datasets2 ;
	}

	public Data(int size){
		datasets_Y2 = new DataUnit[size] ;
		datasets_Y1 = new DataUnit[size] ;	
	}

	public DataUnit[] getDatasets() {
		return datasets;
	}


	public void setDatasets(DataUnit[] datasets) {
		this.datasets = datasets;
	}


	public String[] getLabels() {
		return labels;
	}


	public void setLabels(String[] labels) {
		this.labels = labels;
	}
	
	

}
