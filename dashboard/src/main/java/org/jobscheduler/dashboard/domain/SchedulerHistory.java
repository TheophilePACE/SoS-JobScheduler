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
package org.jobscheduler.dashboard.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * The persistent class for the scheduler_history database table.
 * 
 */
@Entity
@Table(name="scheduler_history")
@NamedQuery(name="SchedulerHistory.findAll", query="SELECT s FROM SchedulerHistory s")
public class SchedulerHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"ID\"")
	private long id;

	@Column(name="\"CAUSE\"")
	private String cause;

	@Column(name="\"CLUSTER_MEMBER_ID\"")
	private String clusterMemberId;

	@Column(name="\"END_TIME\"")
	private Timestamp endTime;

	@Column(name="\"ERROR\"")
	private BigDecimal error;

	@Column(name="\"ERROR_CODE\"")
	private String errorCode;

	@Column(name="\"ERROR_TEXT\"")
	private String errorText;

	@Column(name="\"EXIT_CODE\"")
	private BigDecimal exitCode;

	@Column(name="\"ITEM_START\"")
	private String itemStart;

	@Column(name="\"ITEM_STOP\"")
	private String itemStop;

	@Column(name="\"JOB_NAME\"")
	private String jobName;

	@Column(name="\"LOG\"")
	@JsonSerialize(using= LogSerializer.class)
	private byte[] log;

	@Column(name="\"PARAMETERS\"")
	@JsonSerialize(using= ParameterSerializer.class)
	private String parameters;

	@Column(name="\"PID\"")
	private BigDecimal pid;

	@Column(name="\"SPOOLER_ID\"")
	private String spoolerId;

	@Column(name="\"START_TIME\"")
	private Timestamp startTime;

	@Column(name="\"STEPS\"")
	private BigDecimal steps;

	public SchedulerHistory() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCause() {
		return this.cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getClusterMemberId() {
		return this.clusterMemberId;
	}

	public void setClusterMemberId(String clusterMemberId) {
		this.clusterMemberId = clusterMemberId;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getError() {
		return this.error;
	}

	public void setError(BigDecimal error) {
		this.error = error;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorText() {
		return this.errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public BigDecimal getExitCode() {
		return this.exitCode;
	}

	public void setExitCode(BigDecimal exitCode) {
		this.exitCode = exitCode;
	}

	public String getItemStart() {
		return this.itemStart;
	}

	public void setItemStart(String itemStart) {
		this.itemStart = itemStart;
	}

	public String getItemStop() {
		return this.itemStop;
	}

	public void setItemStop(String itemStop) {
		this.itemStop = itemStop;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public byte[] getLog() {
		return this.log;
	}

	public void setLog(byte[] log) {
		this.log = log;
	}

	public String getParameters() {
		return this.parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public BigDecimal getPid() {
		return this.pid;
	}

	public void setPid(BigDecimal pid) {
		this.pid = pid;
	}

	public String getSpoolerId() {
		return this.spoolerId;
	}

	public void setSpoolerId(String spoolerId) {
		this.spoolerId = spoolerId;
	}

	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public BigDecimal getSteps() {
		return this.steps;
	}

	public void setSteps(BigDecimal steps) {
		this.steps = steps;
	}

}