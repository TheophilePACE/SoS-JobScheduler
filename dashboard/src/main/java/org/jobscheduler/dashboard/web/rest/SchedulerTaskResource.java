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

import java.util.List;

import org.jobscheduler.dashboard.domain.SchedulerJob;
import org.jobscheduler.dashboard.domain.SchedulerTask;
import org.jobscheduler.dashboard.repository.SchedulerTaskRepository;
import org.jobscheduler.dashboard.web.rest.dto.ListDTO;
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
public class SchedulerTaskResource {
	
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
		public @ResponseBody ListDTO schedulerTasks(Model model, Pageable pageable) {
			
			//List<SchedulerTask> it = schedulerTaskRepository.findAll();
			return null;
			
		}		
	
}
