package org.jobscheduler.dashboard.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jobscheduler.dashboard.domain.SchedulerHistory;
import org.jobscheduler.dashboard.repository.SchedulerHistoryRepository;
import org.jobscheduler.dashboard.repository.SchedulerJobRepository;
import org.jobscheduler.dashboard.web.rest.dto.SchedulerJobStatsDTO;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to manage job
 * 
 * @author bigloupe
 * 
 */
@Service
@Transactional
public class SchedulerJobService {

	@Inject
	SchedulerHistoryRepository schedulerHistoryRepository;

	@Inject
	SchedulerJobRepository schedulerJobRepository;

	/**
	 * Get statistics for JobName
	 * 
	 * @param spoolerId
	 * @param jobName
	 * @return
	 */
	public SchedulerJobStatsDTO getStatsPerJobName(String spoolerId,
			String jobName) {
		SchedulerJobStatsDTO dto = new SchedulerJobStatsDTO();
		dto.setJobName(jobName);
		dto.setSpoolerId(spoolerId);
		// / Retrieve all executed jobs for a job name
		List<SchedulerHistory> schedulerHistories = schedulerHistoryRepository
				.findBySpoolerIdAndJobName(spoolerId, jobName);
		dto.setExecutedTimes(new Long(schedulerHistories.size()));

		DescriptiveStatistics errorStats = new DescriptiveStatistics();
		DescriptiveStatistics workingTimeStats = new DescriptiveStatistics();

		DateTime startDateTime = null;
		DateTime endDateTime = null;
		for (SchedulerHistory schedulerHistory : schedulerHistories) {
			if ((schedulerHistory.getEndTime() != null)
					&& (schedulerHistory.getEndTime().getTime() >= 0)) {
				endDateTime = new DateTime(schedulerHistory.getEndTime()
						.getTime());

				startDateTime = new DateTime(schedulerHistory.getStartTime()
						.getTime());
				Period workingTime = new Period(startDateTime, endDateTime);
				workingTimeStats.addValue(workingTime.getMillis());
				errorStats.addValue(0);
			} else {
				errorStats.addValue(1);
			}
		}
		dto.setLastStartDateTime(startDateTime);
		dto.setLastEndDateTime(endDateTime);
		dto.setMeanWorkingTime(workingTimeStats.getMean());
		schedulerJobRepository.findBySpoolerId(spoolerId);
		return dto;
	}
}
