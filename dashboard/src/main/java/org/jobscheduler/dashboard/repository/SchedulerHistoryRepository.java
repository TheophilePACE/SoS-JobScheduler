package org.jobscheduler.dashboard.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.jobscheduler.dashboard.model.SchedulerHistory;
import org.jobscheduler.dashboard.model.SchedulerJob;
import org.jobscheduler.dashboard.model.SchedulerTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SchedulerHistoryRepository extends PagingAndSortingRepository<SchedulerHistory, Long> {

	List<SchedulerHistory> findByStartTimeBetween(Timestamp startTime1, Timestamp startTime2);
	Long countByStartTimeBetweenAndError(Timestamp startTime1, Timestamp startTime2, BigDecimal error);
	Long countByStartTimeBetween(Timestamp startTime1, Timestamp startTime2);
	
	// find and start time
	List<SchedulerHistory> findByStartTimeBetween(Timestamp startTime1, Timestamp startTime2, Pageable pageable);
 	// 
	List<SchedulerHistory> findByStartTimeBetweenAndJobNameLikeAndError(Timestamp startTime1, Timestamp startTime2, String jobName, BigDecimal error, Pageable pageable);
	
	Page<SchedulerHistory> findByStartTimeBetweenAndJobNameLikeAndSpoolerIdLikeAndError(Timestamp startTime1, Timestamp startTime2, String jobName, String spoolerId, BigDecimal error, Pageable pageable);
	Page<SchedulerHistory> findByStartTimeBetweenAndJobNameLikeAndSpoolerIdLike(Timestamp startTime1, Timestamp startTime2, String jobName, String spoolerId, Pageable pageable);
	
	@Query("from SchedulerHistory where (startTime >= :startTime1 and startTime <= :startTime2) order by (endTime - startTime) DESC")
	List<SchedulerHistory> findByStartTimeBetweenAndDuringTime(@Param("startTime1") Timestamp startTime1,@Param("startTime2") Timestamp startTime2, Pageable pageable);
}
