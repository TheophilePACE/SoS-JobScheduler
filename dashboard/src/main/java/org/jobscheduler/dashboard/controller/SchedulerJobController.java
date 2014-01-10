package org.jobscheduler.dashboard.controller;

import org.jobscheduler.dashboard.model.SchedulerJob;
import org.jobscheduler.dashboard.repository.JobCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@Api(value="", description="Jobs information")
public class SchedulerJobController {
	
		@Autowired
		JobCrudRepository jobRepository;

		@RequestMapping("/job")
		@ApiOperation(value="Get job detail")
		public @ResponseBody SchedulerJob schedulerJob(@RequestParam(value="name", required=false, defaultValue="essai") String name, Model model) {
			model.addAttribute("name", name);
			return new SchedulerJob();
		}
		
		@RequestMapping("/jobs")
		@ApiOperation(value="Get job information")
		public @ResponseBody Iterable<SchedulerJob> schedulerJobs(Model model) {
			return jobRepository.findAll();
			
		}		
	
}
