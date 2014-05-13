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

/**
 * The primary key class for the scheduler_jobs database table.
 * 
 */
@Embeddable
public class SchedulerJobPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"SPOOLER_ID\"")
	private String spoolerId;

	@Column(name="\"CLUSTER_MEMBER_ID\"")
	private String clusterMemberId;

	@Column(name="\"PATH\"")
	private String path;

	public SchedulerJobPK() {
	}
	public String getSpoolerId() {
		return this.spoolerId;
	}
	public void setSpoolerId(String spoolerId) {
		this.spoolerId = spoolerId;
	}
	public String getClusterMemberId() {
		return this.clusterMemberId;
	}
	public void setClusterMemberId(String clusterMemberId) {
		this.clusterMemberId = clusterMemberId;
	}
	public String getPath() {
		return this.path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SchedulerJobPK)) {
			return false;
		}
		SchedulerJobPK castOther = (SchedulerJobPK)other;
		return 
			this.spoolerId.equals(castOther.spoolerId)
			&& this.clusterMemberId.equals(castOther.clusterMemberId)
			&& this.path.equals(castOther.path);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.spoolerId.hashCode();
		hash = hash * prime + this.clusterMemberId.hashCode();
		hash = hash * prime + this.path.hashCode();
		
		return hash;
	}
}