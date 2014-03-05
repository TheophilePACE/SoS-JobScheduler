package org.jobscheduler.dashboard.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jobscheduler.dashboard.WebSpringConfiguration;
import org.jobscheduler.dashboard.dto.ListDataTransfertObject;
import org.jobscheduler.dashboard.dto.PointDataTransfertObject;
import org.jobscheduler.dashboard.dto.SerieDataTransfertObject;
import org.jobscheduler.dashboard.model.SchedulerHistory;
import org.jobscheduler.dashboard.model.SchedulerJob;
import org.jobscheduler.dashboard.model.SchedulerTask;
import org.jobscheduler.dashboard.repository.SchedulerHistoryRepository;
import org.jobscheduler.dashboard.repository.SchedulerTaskRepository;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus.Series;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@Api(value = "", description = "Scheduler History information")
public class SchedulerHistoryController {

	private static final Log log = LogFactory
			.getLog(SchedulerHistoryController.class);

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
	ListDataTransfertObject schedulerHistories(Model model,
			@RequestParam(value = "count") Integer count,
			@RequestParam(value = "page") Integer page,
			HttpServletRequest request) throws UnsupportedEncodingException {

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

		ListDataTransfertObject dto = new ListDataTransfertObject();

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
		dto.setTotal(scheduleJob.getTotalElements());

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
	List<SerieDataTransfertObject> nbJobsBetween2Date(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") DateTime startChartDT,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") DateTime endChartDT,
			@RequestParam(value = "jobInError", required = false) Boolean jobInError,
			Model model) {

		if ((startChartDT == null) || (endChartDT == null))
			return null;

		Days days = Days.daysBetween(startChartDT, endChartDT);
		if (days.getDays() < 0)
			return new ArrayList<SerieDataTransfertObject>(0);
		// Create 1 serie with point per day
		List<SerieDataTransfertObject> series = new ArrayList<SerieDataTransfertObject>(
				1);
		SerieDataTransfertObject serie = new SerieDataTransfertObject();
		List<PointDataTransfertObject> points = new ArrayList<PointDataTransfertObject>(
				days.getDays() + 1);
		for (int i = 0; i < days.getDays() + 1; i++) {
			PointDataTransfertObject point = new PointDataTransfertObject();
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
	List<SerieDataTransfertObject> longRunningJobsBetween2Date(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") DateTime startChartDT,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") DateTime endChartDT,
			Model model) {

		if ((startChartDT == null) || (endChartDT == null))
			return null;

		Days days = Days.daysBetween(startChartDT, endChartDT);
		if (days.getDays() < 0)
			return new ArrayList<SerieDataTransfertObject>(0);
		// 1 serie by day
		List<SerieDataTransfertObject> series = new ArrayList<SerieDataTransfertObject>(
				days.getDays());

		for (int i = 0; i < days.getDays()+1; i++) {
			// 1 serie contains the most long running jobs
			SerieDataTransfertObject serie = new SerieDataTransfertObject();
			serie.setKey("Serie "
					+ i + " in seconds");
			List<PointDataTransfertObject> points = new ArrayList<PointDataTransfertObject>();
			serie.setValues(points);
			series.add(serie);
		}

		for (int i = 0; i < days.getDays()+1; i++) {
			List<SchedulerHistory> schedulerHistories = schedulerHistoryRepository
					.findByStartTimeBetweenAndDuringTime(new Timestamp(
							startChartDT.getMillis()), new Timestamp(
							startChartDT.plusDays(i).getMillis()), new PageRequest(0, days.getDays()+1));
			SerieDataTransfertObject serie = series.get(days.getDays()-i);
			for (int j = 0; j < schedulerHistories.size(); j++) {
				List<PointDataTransfertObject> points = (List<PointDataTransfertObject>) serie
						.getValues();
				PointDataTransfertObject point = new PointDataTransfertObject();
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
