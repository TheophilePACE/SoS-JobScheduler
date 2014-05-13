/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.jobscheduler.dashboard.web.rest.dto;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
	
	// Size of samples where executed times has been calculated 
	private int nbJobsLastExecutedTimes;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:MM:ss")
	@JsonSerialize(using = DateTimeSerializer.class)
	private DateTime lastStartDateTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:MM:ss")
	@JsonSerialize(using = DateTimeSerializer.class)
	private DateTime lastEndDateTime;

	private double meanWorkingTime;
	
	private List<SerieDTO> workingTime;

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

	public List<SerieDTO> getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(List<SerieDTO> workingTime) {
		this.workingTime = workingTime;
	}

	public int getNbJobsLastExecutedTimes() {
		return nbJobsLastExecutedTimes;
	}

	public void setNbJobsLastExecutedTimes(int nbJobsLastExecutedTimes) {
		this.nbJobsLastExecutedTimes = nbJobsLastExecutedTimes;
		
	}

}
