package org.jobscheduler.dashboard.repository;

import java.util.List;

import org.jobscheduler.dashboard.model.SchedulerJob;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SchedulerJobRepository extends PagingAndSortingRepository<SchedulerJob, Long> {

	List<SchedulerJob> findByNextStartTime(String nextStartTime);

}
