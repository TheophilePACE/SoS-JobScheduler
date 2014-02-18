package org.jobscheduler.dashboard.model;

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