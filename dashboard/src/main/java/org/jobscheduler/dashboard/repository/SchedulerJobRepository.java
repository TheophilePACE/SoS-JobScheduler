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
