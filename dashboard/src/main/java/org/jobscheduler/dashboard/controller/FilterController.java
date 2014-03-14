package org.jobscheduler.dashboard.controller;

import java.util.List;

import org.jobscheduler.dashboard.model.Field;
import org.jobscheduler.dashboard.model.Filter;
import org.jobscheduler.dashboard.repository.FieldRepository;
import org.jobscheduler.dashboard.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@Api(value = "", description = "Filter")
public class FilterController {

	@Autowired
	FilterRepository filterRepository;

	@Autowired
	FieldRepository fieldRepository;

	/**
	 * Add a field (fieldname/fieldValue) in filter
	 * 
	 * @param name
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	@RequestMapping(value = "/addFieldInFilter", method = RequestMethod.GET)
	@ApiOperation(value = "Add a field (fieldname/fieldValue) in filter")
	public @ResponseBody
	Filter addFilter(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "fieldName", required = false) String fieldName,
			@RequestParam(value = "fieldValue", required = false) String fieldValue) {
		// Search existing filter
		Filter filter = filterRepository.findByName(name);
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

	/**
	 * Get filter by name
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	@ApiOperation(value = "Get filter")
	public @ResponseBody
	Filter getFilter(@RequestParam(value = "name", required = false) String name) {
		// Search existing filter
		Filter filter = filterRepository.findByName(name);
		return filter;
	}

	/**
	 * List filters
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/filters", method = RequestMethod.GET)
	@ApiOperation(value = "Get list filters")
	public @ResponseBody
	List<String> filters() {
		// Search existing filter
		List<String> filterNames = filterRepository.findAllFilterName();
		return filterNames;
	}
}
