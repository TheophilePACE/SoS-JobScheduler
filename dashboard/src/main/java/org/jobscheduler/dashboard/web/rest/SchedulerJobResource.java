package org.jobscheduler.dashboard.web.rest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jobscheduler.dashboard.domain.SchedulerHistory;
import org.jobscheduler.dashboard.domain.SchedulerJob;
import org.jobscheduler.dashboard.repository.SchedulerJobRepository;
import org.jobscheduler.dashboard.service.SchedulerJobService;
import org.jobscheduler.dashboard.web.rest.dto.ListDTO;
import org.jobscheduler.dashboard.web.rest.dto.SchedulerJobStatsDTO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@Api(value = "", description = "Jobs information")
public class SchedulerJobResource {

	private final Logger log = LoggerFactory
			.getLogger(SchedulerJobResource.class);

	@Autowired
	SchedulerJobRepository schedulerJobRepository;

	@Autowired
	SchedulerJobService schedulerJobService;

	/**
	 * Get job detail by path name Many jobs can be returned (in several
	 * spooler)
	 * 
	 * @param name
	 * @param model
	 * @return
	 */
	@RequestMapping("/job")
	@ApiOperation(value = "Get job detail")
	public @ResponseBody
	List<SchedulerJob> schedulerJob(
			@RequestParam(value = "jobPath") String jobPath, Model model) {
		return schedulerJobRepository.findByJobPath(jobPath);
	}

	@RequestMapping("/jobs")
	@ApiOperation(value = "Get list jobs")
	public @ResponseBody
	ListDTO schedulerJobs(Model model,
			@RequestParam(value = "count") Integer count,
			@RequestParam(value = "page") Integer page,
			HttpServletRequest request) throws UnsupportedEncodingException {
		
		// Spring Data count from page 0, ngTable from page 1
		page--;

		Enumeration<String> parametersNames = request.getParameterNames();
		// Parameters

		String spoolerId = "%";
		String clusterMember = "%";
		String jobName = "%";

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

				if (parameterFilter.equals("spoolerId")) {
					spoolerId = "%" + decodedFilter + "%";
				}
				if (parameterFilter.equals("clusterMemberId")) {
					clusterMember = "%" + decodedFilter + "%";
				}
				if (parameterFilter.equals("jobName")) {
					jobName = "%" + decodedFilter + "%";
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

		Page<SchedulerJob> scheduleJob = schedulerJobRepository
				.findBySpoolerIdLikeAndClusterMemberLikeAndJobNameLike(spoolerId, clusterMember, jobName,
						pageable);

		dto.setResult(scheduleJob.getContent());
		dto.setTotalElements(scheduleJob.getTotalElements());
		dto.setTotalPages(scheduleJob.getTotalPages());
		
		return dto;
	}

	/**
	 * Ge a list with all jobName for spooler
	 * 
	 * @param spoolerId
	 * @return
	 */
	@RequestMapping("/jobsBySpooler")
	@ApiOperation(value = "Get list unique job by spoolerId")
	public @ResponseBody
	List<String> schedulerJobByspooler(String spoolerId) {
		return schedulerJobRepository.findBySpoolerId(spoolerId);
	}

	@RequestMapping("/jobStats")
	@ApiOperation(value = "Get statistics information on job")
	public @ResponseBody
	SchedulerJobStatsDTO statsByJobName(
			@RequestParam(value = "spoolerId") String spoolerId,
			@RequestParam(value = "jobName") String jobName, Model model) {
		return schedulerJobService.getStatsPerJobName(spoolerId, jobName);
	}

}
