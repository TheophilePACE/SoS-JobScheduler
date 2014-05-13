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
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the scheduler_tasks database table.
 * 
 */
@Entity
@Table(name="scheduler_tasks")
@NamedQuery(name="SchedulerTask.findAll", query="SELECT s FROM SchedulerTask s")
public class SchedulerTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"TASK_ID\"")
	private long taskId;

	@Column(name="\"CLUSTER_MEMBER_ID\"")
	private String clusterMemberId;

	@Column(name="\"ENQUEUE_TIME\"")
	private Timestamp enqueueTime;

	@Column(name="\"JOB_NAME\"")
	private String jobName;

	@Column(name="\"PARAMETERS\"")
	private String parameters;

	@Column(name="\"SPOOLER_ID\"")
	private String spoolerId;

	@Column(name="\"START_AT_TIME\"")
	private Timestamp startAtTime;

	@Column(name="\"TASK_XML\"")
	private String taskXml;

	public SchedulerTask() {
	}

	public long getTaskId() {
		return this.taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getClusterMemberId() {
		return this.clusterMemberId;
	}

	public void setClusterMemberId(String clusterMemberId) {
		this.clusterMemberId = clusterMemberId;
	}

	public Timestamp getEnqueueTime() {
		return this.enqueueTime;
	}

	public void setEnqueueTime(Timestamp enqueueTime) {
		this.enqueueTime = enqueueTime;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getParameters() {
		return this.parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getSpoolerId() {
		return this.spoolerId;
	}

	public void setSpoolerId(String spoolerId) {
		this.spoolerId = spoolerId;
	}

	public Timestamp getStartAtTime() {
		return this.startAtTime;
	}

	public void setStartAtTime(Timestamp startAtTime) {
		this.startAtTime = startAtTime;
	}

	public String getTaskXml() {
		return this.taskXml;
	}

	public void setTaskXml(String taskXml) {
		this.taskXml = taskXml;
	}

}