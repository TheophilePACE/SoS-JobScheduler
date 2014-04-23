package org.jobscheduler.dashboard.repository;

import java.util.List;

import org.jobscheduler.dashboard.domain.Filter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FilterRepository extends PagingAndSortingRepository<Filter, Long> {

	Filter findByName(String name);
	
	@Query("select name from Filter")
	List<String> findAllFilterName();
}
