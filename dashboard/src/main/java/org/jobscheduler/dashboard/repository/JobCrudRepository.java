package org.jobscheduler.dashboard.repository;

import java.util.List;

import org.jobscheduler.dashboard.model.SchedulerJob;
import org.springframework.data.repository.CrudRepository;

public interface JobCrudRepository extends CrudRepository<SchedulerJob, Long> {

	List<SchedulerJob> findByNextStartTime(String nextStartTime);
}
