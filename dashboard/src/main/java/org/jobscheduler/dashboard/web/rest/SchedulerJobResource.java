package org.jobscheduler.dashboard.web.rest;

import java.util.List;

import org.jobscheduler.dashboard.domain.SchedulerJob;
import org.jobscheduler.dashboard.repository.SchedulerJobRepository;
import org.jobscheduler.dashboard.web.rest.dto.ListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@Autowired
	SchedulerJobRepository schedulerJobRepository;

	@RequestMapping("/job")
	@ApiOperation(value = "Get job detail")
	public @ResponseBody
	SchedulerJob schedulerJob(
			@RequestParam(value = "name", required = false, defaultValue = "essai") String name,
			Model model) {
		model.addAttribute("name", name);
		return new SchedulerJob();
	}

	@RequestMapping("/jobs")
	@ApiOperation(value = "Get list jobs")
	public @ResponseBody
	ListDTO schedulerJobs( 
			Model model,
			@RequestParam(value = "count") Integer count,
			@RequestParam(value = "page") Integer page,
			@RequestParam(value = "nextStartTime", required = false) String nextStartTime) {
		Pageable pageable = new PageRequest(page, count);

		ListDTO dto = new ListDTO();
		if (nextStartTime != null) {
			System.out.println("SchedulerJobController.schedulerJobs()" + nextStartTime);
			List<SchedulerJob> scheduleJob = schedulerJobRepository
					.findByNextStartTime(nextStartTime, pageable);

			dto.setResult(scheduleJob);
			dto.setTotal(new Long(scheduleJob.size()));

		} else {
			Page<SchedulerJob> scheduleJob = schedulerJobRepository
					.findAll(pageable);
			dto.setTotal(new Long(scheduleJob.getSize()));
			dto.setResult(scheduleJob.getContent());
		}
		return dto;
	}

}
