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
package org.jobscheduler.dashboard.web.rest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jobscheduler.dashboard.domain.SchedulerHistory;
import org.jobscheduler.dashboard.repository.SchedulerHistoryRepository;
import org.jobscheduler.dashboard.web.rest.dto.ListDTO;
import org.jobscheduler.dashboard.web.rest.dto.PointDTO;
import org.jobscheduler.dashboard.web.rest.dto.SerieDTO;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@Api(value = "", description = "Scheduler History information")
public class SchedulerHistoryResource {

    private final Logger log = LoggerFactory.getLogger(SchedulerHistoryResource.class);
    
	DateTimeFormatter fmt = org.joda.time.format.DateTimeFormat
			.forPattern("yyyy/MM/dd HH:mm:ss");

	@Autowired
	SchedulerHistoryRepository schedulerHistoryRepository;

	@RequestMapping("/schedulerHistory")
	@ApiOperation(value = "Get scheduler history detail")
	public @ResponseBody
	SchedulerHistory schedulerHistory(
			@RequestParam(value = "id", required = false) Long id, Model model) {
		SchedulerHistory schedulerHistory = schedulerHistoryRepository
				.findOne(id);
		return schedulerHistory;
	}

	@RequestMapping("/schedulerHistories")
	@ApiOperation(value = "Get list scheduler history")
	public @ResponseBody
	ListDTO schedulerHistories(Model model,
			@RequestParam(value = "count") Integer count,
			@RequestParam(value = "page") Integer page,
			HttpServletRequest request) throws UnsupportedEncodingException {

		// Spring Data count from page 0, ngTable from page 1
		page--;
		Enumeration<String> parametersNames = request.getParameterNames();

		// Parameters
		String jobName = "%";
		String spoolerId = "%";
		BigDecimal error = null;

		DateTime startDT = DateTime.now().minusDays(100);
		DateTime endDT = DateTime.now();

		// For sorting
		List<Order> orders = new ArrayList<Order>();

		while (parametersNames.hasMoreElements()) {
			String parameterName = (String) parametersNames.nextElement();

			// Filtering
			if (parameterName.startsWith(Constant.PARAM_FILTER)) {
				String filter = request.getParameter(parameterName);
				String parameterFilter = parameterName.substring(
						parameterName.indexOf("[") + 1,
						parameterName.indexOf("]"));
				String decodedFilter = URLDecoder.decode(filter, "UTF-8");
				if (parameterFilter.equals("jobName")) {
					jobName = "%" + decodedFilter + "%";
				}
				if (parameterFilter.equals("spoolerId")) {
					spoolerId = "%" + decodedFilter + "%";
				}
				if (parameterFilter.equals("error")) {
					error = new BigDecimal(filter);
				}
				if (parameterFilter.equals("startTime")) {
					startDT = DateTime.parse(decodedFilter, fmt);
				}
				if (parameterFilter.equals("endTime")) {
					endDT = DateTime.parse(decodedFilter, fmt);
				}

				log.info("Filter in get list history : " + parameterName + "="
						+ filter);
			}

			// Sorting
			if (parameterName.startsWith(Constant.PARAM_SORT)) {
				String directionParameter = request.getParameter(parameterName);
				String sortByColumnName = parameterName.substring(
						parameterName.indexOf("[") + 1,
						parameterName.indexOf("]"));
				String direction = URLDecoder.decode(directionParameter,
						"UTF-8");
				orders.add(new Order(Direction.fromString(direction),
						sortByColumnName));
			}
		}

		PageRequest pageable;
		if (orders.size() == 0)
			pageable = new PageRequest(page, count);
		else
			pageable = new PageRequest(page, count, new Sort(orders));

		ListDTO dto = new ListDTO();

		Page<SchedulerHistory> scheduleJob;
		if (error != null)
			scheduleJob = schedulerHistoryRepository
					.findByStartTimeBetweenAndJobNameLikeAndSpoolerIdLikeAndError(
							new Timestamp(startDT.getMillis()), new Timestamp(
									endDT.getMillis()), jobName, spoolerId,
							error, pageable);

		else
			scheduleJob = schedulerHistoryRepository
					.findByStartTimeBetweenAndJobNameLikeAndSpoolerIdLike(
							new Timestamp(startDT.getMillis()), new Timestamp(
									endDT.getMillis()), jobName, spoolerId,
							pageable);

		dto.setResult(scheduleJob.getContent());
		dto.setTotalElements(scheduleJob.getTotalElements());
		dto.setTotalPages(scheduleJob.getTotalPages());

		return dto;
	}

	/**
	 * Build one serie with number of jobs between startDate and endDate (job in
	 * error or not)
	 * 
	 * @param startTime
	 * @param endTime
	 * @param model
	 * @return
	 */
	@RequestMapping("/nbJobsBetween2Date")
	@ApiOperation(value = "Build one serie with number of jobs between startDate and endDate")
	public @ResponseBody
	List<SerieDTO> nbJobsBetween2Date(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") DateTime startChartDT,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") DateTime endChartDT,
			@RequestParam(value = "jobInError", required = false) Boolean jobInError,
			Model model) {

		if ((startChartDT == null) || (endChartDT == null))
			return null;

		Days days = Days.daysBetween(startChartDT, endChartDT);
		if (days.getDays() < 0)
			return new ArrayList<SerieDTO>(0);
		// Create 1 serie with point per day
		List<SerieDTO> series = new ArrayList<SerieDTO>(
				1);
		SerieDTO serie = new SerieDTO();
		List<PointDTO> points = new ArrayList<PointDTO>(
				days.getDays() + 1);
		for (int i = 0; i < days.getDays() + 1; i++) {
			PointDTO point = new PointDTO();
			point.setX(startChartDT.plusDays(i).getMillis());
			Long nbJobs;
			if (jobInError) {
				nbJobs = schedulerHistoryRepository
						.countByStartTimeBetweenAndError(new Timestamp(
								startChartDT.plusDays(i).getMillis()),
								new Timestamp(startChartDT.plusDays(i + 1)
										.getMillis()), new BigDecimal(1));
			} else {
				nbJobs = schedulerHistoryRepository
						.countByStartTimeBetween(new Timestamp(startChartDT
								.plusDays(i).getMillis()), new Timestamp(
								startChartDT.plusDays(i + 1).getMillis()));
			}

			point.setY(nbJobs);
			points.add(point);
		}
		String inError = "";
		if (jobInError)
			inError = "in Error";
		serie.setKey("Number of jobs " + inError + " between "
				+ startChartDT.toString(fmt) + " and "
				+ endChartDT.toString(fmt));

		serie.setValues(points);
		series.add(serie);
		return series;

	}

	/**
	 * Build series with long running jobs
	 * 
	 * @param startTime
	 * @param endTime
	 * @param model
	 * @return
	 */
	@RequestMapping("/longRunningJobsBetween2Date")
	@ApiOperation(value = "Build series with the most long running jobs between startDate and endDate")
	public @ResponseBody
	List<SerieDTO> longRunningJobsBetween2Date(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") DateTime startChartDT,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") DateTime endChartDT,
			Model model) {

		if ((startChartDT == null) || (endChartDT == null))
			return null;

		Days days = Days.daysBetween(startChartDT, endChartDT);
		if (days.getDays() < 0)
			return new ArrayList<SerieDTO>(0);
		// 1 serie by day
		List<SerieDTO> series = new ArrayList<SerieDTO>(
				days.getDays());

		for (int i = 0; i < days.getDays()+1; i++) {
			// 1 serie contains the most long running jobs
			SerieDTO serie = new SerieDTO();
			serie.setKey("Serie "
					+ i + " in seconds");
			List<PointDTO> points = new ArrayList<PointDTO>();
			serie.setValues(points);
			series.add(serie);
		}

		for (int i = 0; i < days.getDays()+1; i++) {
			List<SchedulerHistory> schedulerHistories = schedulerHistoryRepository
					.findByStartTimeBetweenAndDuringTime(new Timestamp(
							startChartDT.getMillis()), new Timestamp(
							startChartDT.plusDays(i).getMillis()), new PageRequest(0, days.getDays()+1));
			SerieDTO serie = series.get(days.getDays()-i);
			for (int j = 0; j < schedulerHistories.size(); j++) {
				List<PointDTO> points = (List<PointDTO>) serie
						.getValues();
				PointDTO point = new PointDTO();
				point.setX(startChartDT.plusDays(j).getMillis());
				if (schedulerHistories.get(j).getEndTime() != null
						&& schedulerHistories.get(j).getStartTime() != null) {
					long elapsedTime = schedulerHistories.get(j).getEndTime()
							.getTime()
							- schedulerHistories.get(j).getStartTime()
									.getTime();

					point.setY(elapsedTime);
				} else
					point.setY(0L);
				points.add(point);
			}
		}
		return series;

	}
}
