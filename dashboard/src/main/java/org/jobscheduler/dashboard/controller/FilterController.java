package org.jobscheduler.dashboard.controller;

import java.util.ArrayList;
import java.util.List;

import org.jobscheduler.dashboard.model.Filter;
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

	List<Filter> filters = new ArrayList<Filter>();
	
	@RequestMapping(value = "/addFilter", method = RequestMethod.GET)
	public @ResponseBody
	Filter addFilter(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "field", required = false) String field,
			@RequestParam(value = "value", required = false) String value,
			Model model) {
		//filter.addField(field, value);
		//return filter;
		return null;
		
	}

}
