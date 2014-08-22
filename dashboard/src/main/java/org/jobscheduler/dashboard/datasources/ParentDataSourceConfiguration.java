package org.jobscheduler.dashboard.datasources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories("org.jobscheduler.dashboard.repository")
@EnableTransactionManagement
public abstract class ParentDataSourceConfiguration  {
	
	
	private final Logger log = LoggerFactory
			.getLogger(ParentDataSourceConfiguration.class);

}
