package org.jobscheduler.dashboard.repository;

import org.jobscheduler.dashboard.domain.Field;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FieldRepository extends PagingAndSortingRepository<Field, Long> {

}
