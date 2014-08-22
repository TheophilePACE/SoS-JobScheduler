/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.jobscheduler.dashboard.config;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseConfiguration implements EnvironmentAware {

	private final Logger log = LoggerFactory
			.getLogger(DatabaseConfiguration.class);

	private RelaxedPropertyResolver propertyResolver;

	@Inject
	private Environment env;
	
	
	@Autowired
	private DataSource datasource;
	


	@Override
	public void setEnvironment(Environment environment) {
		setPropertyResolver(environment, "spring.datasource.");
		
	}

	@Bean(name = { "org.springframework.boot.autoconfigure.AutoConfigurationUtils.basePackages" })
	public List<String> getBasePackages() {
		List<String> basePackages = new ArrayList();
		basePackages.add("org.jobscheduler.dashboard.domain");
		return basePackages;
	}

	
	@Bean
	public SpringLiquibase liquibase() {
		log.debug("Configuring Liquibase");
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(datasource);
		liquibase.setChangeLog("classpath:config/liquibase/master.xml");
		liquibase.setContexts("development, production");
		return liquibase;
	}
	
	
	public void setPropertyResolver(Environment environment, String datasource){
		this.propertyResolver = new RelaxedPropertyResolver(environment,
				datasource);
	}
	
	public Environment getEnvironment(){
		return this.env ;
	}
	

}
