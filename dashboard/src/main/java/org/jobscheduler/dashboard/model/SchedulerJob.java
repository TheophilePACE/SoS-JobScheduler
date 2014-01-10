package org.jobscheduler.dashboard.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SCHEDULER_JOBS database table.
 * 
 */
@Entity
@Table(name="SCHEDULER_JOBS")
@NamedQuery(name="SchedulerJob.findAll", query="SELECT s FROM SchedulerJob s")
public class SchedulerJob implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SchedulerJobPK id;

	@Column(name="NEXT_START_TIME")
	private String nextStartTime;

	@Column(name="STOPPED")
	private int stopped;

	public SchedulerJob() {
	}

	public SchedulerJobPK getId() {
		return this.id;
	}

	public void setId(SchedulerJobPK id) {
		this.id = id;
	}

	public String getNextStartTime() {
		return this.nextStartTime;
	}

	public void setNextStartTime(String nextStartTime) {
		this.nextStartTime = nextStartTime;
	}

	public int getStopped() {
		return this.stopped;
	}

	public void setStopped(int stopped) {
		this.stopped = stopped;
	}

}