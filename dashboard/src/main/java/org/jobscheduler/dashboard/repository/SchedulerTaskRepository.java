package org.jobscheduler.dashboard.repository;

import java.sql.Timestamp;
import java.util.List;

import org.jobscheduler.dashboard.model.SchedulerTask;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface SchedulerTaskRepository extends CrudRepository<SchedulerTask, Long> {

	List<SchedulerTask> findByStartAtTime(Timestamp startTime, Pageable pageable);
}
