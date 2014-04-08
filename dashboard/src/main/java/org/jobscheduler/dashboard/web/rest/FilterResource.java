package org.jobscheduler.dashboard.web.rest;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.jobscheduler.dashboard.domain.Field;
import org.jobscheduler.dashboard.domain.Filter;
import org.jobscheduler.dashboard.repository.FieldRepository;
import org.jobscheduler.dashboard.repository.FilterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Filter controller
 * 2 JSON views :
 * 	- One with FieldSerializer (default serialization)
 *  - One without FieldSerializer
 * @author cloud
 *
 */
@RestController
@Api(value = "", description = "Filter")
public class FilterResource {

	private final Logger log = LoggerFactory.getLogger(FilterResource.class);

	@Autowired
	FilterRepository filterRepository;

	@Autowired
	FieldRepository fieldRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final MappingJackson2JsonView view = new MappingJackson2JsonView();

	public FilterResource() {
		objectMapper.addMixInAnnotations(Field.class, FieldView.class);
		view.setObjectMapper(objectMapper);
		view.setModelKeys(new HashSet<String>(Arrays.asList("NormalView")));
	}

	/** Class to overload JsonSerialisation **/
	@JsonSerialize
	private static class FieldView extends Field {
		// Empty by design ...
	}

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
	 * POST /rest/filters -> Create a new filter.
	 */
	@RequestMapping(value = "/rest/filters", method = RequestMethod.POST, produces = "application/json")
	@Timed
	public void create(@RequestBody Filter filter) {
		log.debug("REST request to save Filter : {}", filter);
		if ((filter.getFields()!=null) && (!filter.getFields().isEmpty())) {
			Iterator<Field> it = filter.getFields().iterator();
			while (it.hasNext()) {
				Field field = (Field) it.next();
				fieldRepository.save(field);
			}
		}
		filterRepository.save(filter);
	}

	/**
	 * GET /rest/filters -> get all the filters.
	 */
	@RequestMapping(value = "/rest/filters", method = RequestMethod.GET, produces = "application/json")
	@Timed
	public @ResponseBody
	String getAll(Model model) throws JsonGenerationException,
			JsonMappingException, IOException {
		log.debug("REST request to get all Filters for editing");
		return objectMapper.writeValueAsString(filterRepository.findAll());
	}

	/**
	 * GET /rest/filters/:id -> get the "id" filter.
	 */
	@RequestMapping(value = "/rest/filters/{id}", method = RequestMethod.GET, produces = "application/json")
	@Timed
	public @ResponseBody
	String get(@PathVariable Long id, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		log.debug("REST request to get Filter : {}", id);
		Filter filter = filterRepository.findOne(id);
		if (filter == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return objectMapper.writeValueAsString(filter);
	}

	/**
	 * DELETE /rest/filters/:id -> delete the "id" filter.
	 */
	@RequestMapping(value = "/rest/filters/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@Timed
	public void delete(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to delete Filter : {}", id);
		filterRepository.delete(id);
	}
}
