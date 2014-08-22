package org.jobscheduler.dashboard.controller;

import java.io.Serializable;
import java.sql.Timestamp;

public class ActivityFields implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String startDate;
	protected String startTime;
	protected String endDate;
	protected String endTime;
	protected int step;
	protected String jobName;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public ActivityFields() {

	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setStep(int step) {
		this.step = step;
	}

	protected static Timestamp toDate(String date, String time) {
		String d[] = date.split("-");
		System.out.println(d[0] + " " + d[1] + " " + d[2]);
		String t[] = time.split(":");
		Timestamp ts = new Timestamp(Integer.parseInt(d[0]) - 1900,
				Integer.parseInt(d[1]) - 1, Integer.parseInt(d[2]),
				Integer.parseInt(t[0]), Integer.parseInt(t[1]), 0, 0);

		return ts;
	}

	protected static Timestamp toTime(String time) {

		String t[] = time.split(":");
		Timestamp ts = new Timestamp(-1901, -12, -31, Integer.parseInt(t[0]),
				Integer.parseInt(t[1]), 0, 0);

		return ts;
	}

	public Timestamp getStart() {
		return toDate(startDate, startTime);
	}

	public Timestamp getEnd() {
		return toDate(endDate, endTime);
	}

	public int getStep() {
		return step;
	}
}
