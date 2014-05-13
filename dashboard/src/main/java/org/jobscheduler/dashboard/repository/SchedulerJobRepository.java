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
package org.jobscheduler.dashboard.repository;

import java.util.List;

import org.jobscheduler.dashboard.domain.SchedulerJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SchedulerJobRepository extends PagingAndSortingRepository<SchedulerJob, Long> {

	List<SchedulerJob> findByNextStartTime(String nextStartTime);
	List<SchedulerJob> findByNextStartTime(String nextStartTime, Pageable pageable);
	
	@Query("from SchedulerJob sj where sj.id.spoolerId like ?1 and sj.id.clusterMemberId like ?2 and sj.id.path like ?3")
	Page<SchedulerJob> findBySpoolerIdLikeAndClusterMemberLikeAndJobNameLike(String spoolerId, String clusterMember, String jobName, Pageable pageable);
	
	@Query("select distinct(sj.id.path) from SchedulerJob sj where sj.id.spoolerId = ?1")
	List <String> findBySpoolerId(String spoolerId);
	
	@Query("select sj from SchedulerJob sj where sj.id.path = ?1")
	List<SchedulerJob> findByJobPath(String jobPath);

}
