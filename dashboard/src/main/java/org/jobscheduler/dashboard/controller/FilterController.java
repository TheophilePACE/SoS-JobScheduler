package org.jobscheduler.dashboard.controller;

import java.util.List;

import org.jobscheduler.dashboard.model.Field;
import org.jobscheduler.dashboard.model.Filter;
import org.jobscheduler.dashboard.repository.FieldRepository;
import org.jobscheduler.dashboard.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

@RestController
@Api(value = "", description = "Filter")
public class FilterController {

	@Autowired
	FilterRepository filterRepository;
	
	@Autowired
	FieldRepository fieldRepository;

	@RequestMapping(value = "/addFieldInFilter", method = RequestMethod.GET)
	public @ResponseBody
	Filter addFilter(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "fieldName", required = false) String fieldName,
			@RequestParam(value = "fieldValue", required = false) String fieldValue,
			Model model) {
		// Search existing filter
		Filter filter = filterRepository.findByName(name);
System.out.println("FilterController.addFilter()" + filter);
		if (filter == null) {
			filter = new Filter();
			filter.setName(name);
			filter = filterRepository.save(filter);
		} 
		
		Field field = new Field();
		field.setName(fieldName);
		field.setValue(fieldValue);
		field.setFilter(filter);
		fieldRepository.save(field);
		filter.addField(field);
		filterRepository.save(filter);
		
		return filter;

	}

}
