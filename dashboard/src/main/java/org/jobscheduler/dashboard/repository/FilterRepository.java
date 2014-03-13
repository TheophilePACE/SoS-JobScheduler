package org.jobscheduler.dashboard.repository;

import org.jobscheduler.dashboard.model.Filter;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FilterRepository extends PagingAndSortingRepository<Filter, Long> {

	Filter findByName(String name);
}
