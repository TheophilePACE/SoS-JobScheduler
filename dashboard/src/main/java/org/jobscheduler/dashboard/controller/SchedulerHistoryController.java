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
import org.jobscheduler.dashboard.SpringConfiguration;
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
			.forPattern("yyyy/MM/dd");

	@Autowired
	SchedulerHistoryRepository schedulerHistoryRepository;

	@RequestMapping("/schedulerHistory")
	@ApiOperation(value = "Get scheduler history detail")
	public @ResponseBody
	SchedulerHistory schedulerHistory(
			@RequestParam(value = "name", required = false) String name,
			Model model) {
		model.addAttribute("name", name);
		return new SchedulerHistory();
	}

	@RequestMapping("/schedulerHistories")
	@ApiOperation(value = "Get list scheduler history")
	public @ResponseBody
	ListDataTransfertObject schedulerHistories(
			Model model,
			@RequestParam(value = "count") Integer count,
			@RequestParam(value = "page") Integer page,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") DateTime startDT,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") DateTime endDT,
			HttpServletRequest request) throws UnsupportedEncodingException {

		Enumeration<String> parametersNames = request.getParameterNames();
		String jobName = "%";
		String spoolerId = "%";
		BigDecimal error = new BigDecimal(1);
		while (parametersNames.hasMoreElements()) {
			String parameterName = (String) parametersNames.nextElement();
			if (parameterName.startsWith(Constant.PARAM_FILTER)) {
				String filter = request.getParameter(parameterName);
				String parameterFilter = parameterName.substring(
						parameterName.indexOf("[") + 1,
						parameterName.indexOf("]"));
				if (parameterFilter.equals("jobName")) {
					jobName = "%" + URLDecoder.decode(filter, "UTF-8") + "%";
				}
				if (parameterFilter.equals("spoolerId")) {
					spoolerId = "%" + filter + "%";
				}
				if (parameterFilter.equals("error")) {
					error = new BigDecimal(filter);
				}
				log.info("Filter in get list history : " + parameterName + "="
						+ filter);
			}

		}

		Pageable pageable = new PageRequest(page, count);

		ListDataTransfertObject dto = new ListDataTransfertObject();
		if (startDT == null) {
			startDT = DateTime.now().minusDays(100);
		}
		if (endDT == null) {
			endDT = DateTime.now();
		}

		List<SchedulerHistory> scheduleJob = schedulerHistoryRepository
				.findByStartTimeBetweenAndJobNameLikeAndSpoolerIdLikeAndError(
						new Timestamp(startDT.getMillis()),
						new Timestamp(endDT.getMillis()), jobName, spoolerId, error, pageable);

		dto.setResult(scheduleJob);
		dto.setTotal(schedulerHistoryRepository.count());

		return dto;
	}

	/**
	 * Build one serie with number of jobs between startDate and endDate
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
			Model model) {

		if ((startChartDT == null) || (endChartDT == null))
			return null;

		List<SchedulerHistory> schedulerHistories = schedulerHistoryRepository
				.findByStartTimeBetween(
						new Timestamp(startChartDT.getMillis()), new Timestamp(
								endChartDT.getMillis()));

		Days days = Days.daysBetween(startChartDT, endChartDT);
		// Create 1 serie with point per day
		List<SerieDataTransfertObject> series = new ArrayList<SerieDataTransfertObject>();
		SerieDataTransfertObject serie = new SerieDataTransfertObject();
		List<PointDataTransfertObject> points = new ArrayList<PointDataTransfertObject>(
				days.getDays() + 1);
		for (int i = 0; i < days.getDays() + 1; i++) {
			PointDataTransfertObject point = new PointDataTransfertObject();
			point.setX(startChartDT.plusDays(i).getMillis());
			point.setY(new Long(schedulerHistories.size()));
			points.add(point);
		}
		serie.setKey("Number of jobs between " + startChartDT.toString(fmt)
				+ " and " + endChartDT.toString(fmt));

		serie.setValues(points);
		series.add(serie);
		return series;

	}

	/**
	 * Build series with nbr jobs failed
	 * 
	 * @param startTime
	 * @param endTime
	 * @param model
	 * @return
	 */
	@RequestMapping("/nbJobsFailedBetween2Date")
	@ApiOperation(value = "Build one serie with number of jobs between startDate and endDate")
	public @ResponseBody
	List<SerieDataTransfertObject> nbJobsFailedBetween2Date(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") DateTime startChartDT,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") DateTime endChartDT,
			Model model) {

		if ((startChartDT == null) || (endChartDT == null))
			return null;

		List<SchedulerHistory> schedulerHistories = schedulerHistoryRepository
				.findByStartTimeBetweenAndJobNameLikeAndError(new Timestamp(
						startChartDT.getMillis()),
						new Timestamp(endChartDT.getMillis()), "%", new BigDecimal(1), null);

		Days days = Days.daysBetween(startChartDT, endChartDT);
		List<SerieDataTransfertObject> series = new ArrayList<SerieDataTransfertObject>();
		// Create 1 serie per day
		SerieDataTransfertObject serie = new SerieDataTransfertObject();
		List<PointDataTransfertObject> points = new ArrayList<PointDataTransfertObject>();
		for (int i = 0; i < days.getDays() + 1; i++) {

			PointDataTransfertObject point = new PointDataTransfertObject();
			point.setX(startChartDT.plusDays(i).getMillis());
			point.setY(new Long(schedulerHistories.size()));
			points.add(point);
		}
		serie.setKey("Number of jobs between" + startChartDT.toString(fmt)
				+ " and " + endChartDT.toString(fmt));

		series.add(serie);

		return series;

	}
}
