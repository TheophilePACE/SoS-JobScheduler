package org.jobscheduler.dashboard.web.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.jobscheduler.dashboard.domain.Field;
import org.jobscheduler.dashboard.domain.Filter;
import org.jobscheduler.dashboard.repository.FieldRepository;
import org.jobscheduler.dashboard.repository.FilterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@Api(value = "", description = "Filter")
public class FilterResource {

    private final Logger log = LoggerFactory.getLogger(FilterResource.class);
    
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
	
    /**
     * POST  /rest/filters -> Create a new filter.
     */
    @RequestMapping(value = "/rest/filters",
            method = RequestMethod.POST,
            produces = "application/json")
    @Timed
    public void create(@RequestBody Filter filter) {
        log.debug("REST request to save Filter : {}", filter);
        filterRepository.save(filter);
    }

    /**
     * GET  /rest/filters -> get all the filters.
     */
    @RequestMapping(value = "/rest/filters",
            method = RequestMethod.GET,
            produces = "application/json")
    @Timed
    public Iterable<Filter> getAll() {
        log.debug("REST request to get all Filters");
        return filterRepository.findAll();
    }

    /**
     * GET  /rest/filters/:id -> get the "id" filter.
     */
    @RequestMapping(value = "/rest/filters/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    @Timed
    public Filter get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Filter : {}", id);
        Filter filter = filterRepository.findOne(id);
        if (filter == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return filter;
    }

    /**
     * DELETE  /rest/filters/:id -> delete the "id" filter.
     */
    @RequestMapping(value = "/rest/filters/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @Timed
    public void delete(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to delete Filter : {}", id);
        filterRepository.delete(id);
    }
}
