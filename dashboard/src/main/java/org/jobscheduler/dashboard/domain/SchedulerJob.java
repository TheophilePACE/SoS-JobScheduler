package org.jobscheduler.dashboard.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the scheduler_jobs database table.
 * 
 */
@Entity
@Table(name="scheduler_jobs")
@NamedQuery(name="SchedulerJob.findAll", query="SELECT s FROM SchedulerJob s")
public class SchedulerJob implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SchedulerJobPK id;

	@Column(name="\"NEXT_START_TIME\"")
	private String nextStartTime;

	@Column(name="\"STOPPED\"")
	private BigDecimal stopped;

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

	public BigDecimal getStopped() {
		return this.stopped;
	}

	public void setStopped(BigDecimal stopped) {
		this.stopped = stopped;
	}

}