package org.jobscheduler.dashboard.web.rest.dto;

import org.joda.time.DateTime;

/**
 * Collect all statistics concerning a job
 *  
 *
 */
public class SchedulerJobStatsDTO {

	private String jobName;
	private String spoolerId;
	// The number of times that job is executed
	private Long executedTimes;

	private DateTime lastStartDateTime;
	private DateTime lastEndDateTime;
	private double meanWorkingTime;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getSpoolerId() {
		return spoolerId;
	}

	public void setSpoolerId(String spoolerId) {
		this.spoolerId = spoolerId;
	}

	public Long getExecutedTimes() {
		return executedTimes;
	}

	public void setExecutedTimes(Long executedTimes) {
		this.executedTimes = executedTimes;
	}

	public void setLastStartDateTime(DateTime lastStartDateTime) {
		this.lastStartDateTime = lastStartDateTime;
	}

	public DateTime getLastStartDateTime() {
		return lastStartDateTime;
	}

	public void setLastEndDateTime(DateTime lastEndDateTime) {
		this.lastEndDateTime = lastEndDateTime;
	}

	public DateTime getLastEndDateTime() {
		return lastEndDateTime;
	}

	public void setMeanWorkingTime(double mean) {
		this.meanWorkingTime = mean;

	}

	public double getMeanWorkingTime() {
		return meanWorkingTime;
	}

}
