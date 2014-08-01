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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.jobscheduler.dashboard.domain.SchedulerHistory;
import org.jobscheduler.dashboard.domain.SchedulerJob;
import org.jobscheduler.dashboard.domain.SchedulerTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SchedulerHistoryRepository extends PagingAndSortingRepository<SchedulerHistory, Long> {

	// For graphics
	///////////////
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
	
	
	
	// for getting information details
	//////////////////////////////////
	
	List<SchedulerHistory> findBySpoolerIdAndJobName(String spoolerId, String jobName, Pageable pageable);
	Long countBySpoolerIdAndJobName(String spoolerId, String jobName);

	
	//getting all the jobs
	@Query("select jobName from SchedulerHistory")
	String[] getAllJobs() ;
	
	//getting all start time and all end time 
	@Query("select startTime from SchedulerHistory where (jobName = :jobName1 and endTime is not null and startTime >= :startTime1 and endTime <= :endTime1) order by startTime ")
	List<Timestamp> getStartTimes(@Param("jobName1") String jobName1,@Param("startTime1")Timestamp start,@Param("endTime1")Timestamp end ) ;

	
	@Query("select endTime from SchedulerHistory where (jobName = :jobName1 and endTime is not null and startTime >= :startTime1 and endTime <= :endTime1) order by startTime ")
	List<Timestamp> getEndTimes(@Param("jobName1") String jobName1,@Param("startTime1")Timestamp start,@Param("endTime1")Timestamp end  ) ;

	
	
	@Query("select distinct jobName from SchedulerHistory where (jobName like %:jobName1% and endTime is not null ) ")
	List<String> getJobLike(@Param("jobName1") String jobName1 ) ;

	@Query("select count(jobName) from SchedulerHistory where (startTime <= :step and endTime > :step) ")
	int getJobNumberbyDate(@Param("step") Timestamp step ) ;

	
	//get job in errors 
	
	@Query("select count(jobName ) from SchedulerHistory where (startTime >= :start and (endTime=null or endTime <= startTime))")
	int getErrorJobs(@Param("start") Timestamp start) ;
//
	@Query("select count(jobName) from SchedulerHistory where (startTime >= :dayBegin and endTime < :dayEnd) and exitCode = 0")
	int getDayJobsCount(@Param("dayBegin") Timestamp dayBegin,@Param("dayEnd") Timestamp dayEnd ) ;
	
	@Query("select count(jobName) from SchedulerHistory where (startTime >= :dayBegin and endTime < :dayEnd) and exitCode != 0")
	int getErrorDayJobsCount(@Param("dayBegin") Timestamp dayBegin,@Param("dayEnd") Timestamp dayEnd ) ;

////	
	@Query("select distinct exitCode from SchedulerHistory where (startTime >= :begin and endTime < :end and exitCode !=0) ")
	BigDecimal[] getExitCodes(@Param("begin") Timestamp begin,@Param("end") Timestamp end ) ;
	

	@Query("select count(jobName) from SchedulerHistory where (startTime >= :dayBegin and endTime < :dayEnd) and exitCode = :code")
	int getErrorDayJobsCountAndExitCode(@Param("dayBegin") Timestamp dayBegin,@Param("dayEnd") Timestamp dayEnd,@Param("code") BigDecimal code ) ;

	
	@Query("select jobName as duration from SchedulerHistory where (endTime is not null and startTime is not null ) order by (endTime-startTime) DESC")
	List<Object> getTopJobs() ;



}
