package org.jobscheduler.dashboard.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Job {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	
	private String name;

}
