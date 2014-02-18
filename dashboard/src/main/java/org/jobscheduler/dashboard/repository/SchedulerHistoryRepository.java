package org.jobscheduler.dashboard.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.jobscheduler.dashboard.model.SchedulerHistory;
import org.jobscheduler.dashboard.model.SchedulerJob;
import org.jobscheduler.dashboard.model.SchedulerTask;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SchedulerHistoryRepository extends PagingAndSortingRepository<SchedulerHistory, Long> {

	List<SchedulerHistory> findByStartTimeBetween(Timestamp startTime1, Timestamp startTime2);
	// find and start time
	List<SchedulerHistory> findByStartTimeBetween(Timestamp startTime1, Timestamp startTime2, Pageable pageable);
 	// 
	List<SchedulerHistory> findByStartTimeBetweenAndJobNameLikeAndError(Timestamp startTime1, Timestamp startTime2, String jobName, BigDecimal error, Pageable pageable);
	
	List<SchedulerHistory> findByStartTimeBetweenAndJobNameLike(Timestamp startTime1, Timestamp startTime2, String jobName, Pageable pageable);
	
	List<SchedulerHistory> findByStartTimeBetweenAndJobNameLikeAndSpoolerIdLikeAndError(Timestamp startTime1, Timestamp startTime2, String jobName, String spoolerId, BigDecimal error, Pageable pageable);
}
