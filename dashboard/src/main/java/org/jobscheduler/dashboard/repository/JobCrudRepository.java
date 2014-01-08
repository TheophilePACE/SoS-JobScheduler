package org.jobscheduler.dashboard.repository;

import java.util.List;

import org.jobscheduler.dashboard.model.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobCrudRepository extends CrudRepository<Job, Long> {

	List<Job> findByName(String name);
}
