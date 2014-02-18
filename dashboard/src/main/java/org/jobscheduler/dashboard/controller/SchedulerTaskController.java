package org.jobscheduler.dashboard.controller;

import java.util.List;

import org.jobscheduler.dashboard.dto.ListDataTransfertObject;
import org.jobscheduler.dashboard.model.SchedulerJob;
import org.jobscheduler.dashboard.model.SchedulerTask;
import org.jobscheduler.dashboard.repository.SchedulerTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@Api(value="", description="Scheduler Task information")
public class SchedulerTaskController {
	
		@Autowired
		SchedulerTaskRepository schedulerTaskRepository;

		@RequestMapping("/schedulerTask")
		@ApiOperation(value="Get scheduler task detail")
		public @ResponseBody SchedulerTask schedulerTask(@RequestParam(value="name", required=false, defaultValue="essai") String name, Model model) {
			model.addAttribute("name", name);
			return new SchedulerTask();
		}
		
		@RequestMapping("/schedulerTasks")
		@ApiOperation(value="Get list scheduler tasks")
		public @ResponseBody ListDataTransfertObject schedulerTasks(Model model, Pageable pageable) {
			
			//List<SchedulerTask> it = schedulerTaskRepository.findAll();
			return null;
			
		}		
	
}
