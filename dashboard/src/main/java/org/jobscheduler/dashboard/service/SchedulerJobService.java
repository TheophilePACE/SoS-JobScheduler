package org.jobscheduler.dashboard.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jobscheduler.dashboard.domain.SchedulerHistory;
import org.jobscheduler.dashboard.repository.SchedulerHistoryRepository;
import org.jobscheduler.dashboard.repository.SchedulerJobRepository;
import org.jobscheduler.dashboard.web.rest.dto.PointDTO;
import org.jobscheduler.dashboard.web.rest.dto.SchedulerJobStatsDTO;
import org.jobscheduler.dashboard.web.rest.dto.SerieDTO;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	private final Logger log = LoggerFactory
			.getLogger(SchedulerJobService.class);

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
			String jobName, Pageable pageable) {
		SchedulerJobStatsDTO dto = new SchedulerJobStatsDTO();
		dto.setJobName(jobName);
		dto.setSpoolerId(spoolerId);
		
		// Number of executed jobs
		Long executedTimes = schedulerHistoryRepository.countBySpoolerIdAndJobName(spoolerId, jobName);
		dto.setExecutedTimes(executedTimes);
		
		// / Retrieve last executed jobs for a job name (max 100)
		List<SchedulerHistory> schedulerHistories = schedulerHistoryRepository
				.findBySpoolerIdAndJobName(spoolerId, jobName, pageable);
		dto.setNbJobsLastExecutedTimes(schedulerHistories.size());

		DescriptiveStatistics errorStats = new DescriptiveStatistics();
		DescriptiveStatistics workingTimeStats = new DescriptiveStatistics();
		SerieDTO workingTimes = new SerieDTO();
		SerieDTO meanWorkingTimes = new SerieDTO();
		List<PointDTO> points = new ArrayList<PointDTO>();
		List<PointDTO> meanWorkingTimePoints = new ArrayList<PointDTO>();
		workingTimes.setKey("Working time");
		workingTimes.setValues(points);
		meanWorkingTimes.setKey("Mean working time");
		meanWorkingTimes.setValues(meanWorkingTimePoints);
		
		DateTime startDateTime = null;
		DateTime endDateTime = null;
		for (SchedulerHistory schedulerHistory : schedulerHistories) {
			if ((schedulerHistory.getEndTime() != null)
					&& (schedulerHistory.getEndTime().getTime() >= 0)) {
				endDateTime = new DateTime(schedulerHistory.getEndTime()
						.getTime());
				startDateTime = new DateTime(schedulerHistory.getStartTime()
						.getTime());
				long workingTime = endDateTime.getMillis() - startDateTime.getMillis();
				workingTimeStats.addValue(workingTime);
				points.add(new PointDTO(startDateTime.getMillis(), (long)workingTime));
				errorStats.addValue(0);
			} else {
				errorStats.addValue(1);
			}
		}
		dto.setLastStartDateTime(startDateTime);
		dto.setLastEndDateTime(endDateTime);
		dto.setMeanWorkingTime(workingTimeStats.getMean());

		for (SchedulerHistory schedulerHistory : schedulerHistories) {
			startDateTime = new DateTime(schedulerHistory.getStartTime()
					.getTime());
			meanWorkingTimePoints.add(new PointDTO(startDateTime.getMillis(), (long)workingTimeStats.getMean()));
		}
		
		List<SerieDTO> seriesDTO = new ArrayList<SerieDTO>();
		seriesDTO.add(workingTimes);
		seriesDTO.add(meanWorkingTimes);
		dto.setWorkingTime(seriesDTO);
		
		
		schedulerJobRepository.findBySpoolerId(spoolerId);
		return dto;
	}
}
