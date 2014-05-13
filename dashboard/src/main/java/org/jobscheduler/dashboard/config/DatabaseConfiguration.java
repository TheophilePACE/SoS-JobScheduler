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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@EnableJpaRepositories("org.jobscheduler.dashboard.repository")
@EnableTransactionManagement
public class DatabaseConfiguration implements EnvironmentAware, ApplicationContextAware {

	private final Logger log = LoggerFactory
			.getLogger(DatabaseConfiguration.class);

	private RelaxedPropertyResolver propertyResolver;

	@Inject
	private Environment env;
	
	private ApplicationContext applicationContext;

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment,
				"spring.datasource.");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}
	
	@Bean
	public DataSource dataSource() {
		log.debug("Configuring Datasource");
		if (propertyResolver.getProperty("url") == null
				&& propertyResolver.getProperty("databaseName") == null) {
			log.error(
					"Your database connection pool configuration is incorrect (url = "
							+ propertyResolver.getProperty("url")
							+ ",  databaseName = "
							+ propertyResolver.getProperty("databaseName")
							+ ") ! The application"
							+ " cannot start. Please check your Spring profile, current profiles are: {}",
					Arrays.toString(env.getActiveProfiles()));
			throw new ApplicationContextException("Database connection pool is not configured correctly", null);
		}
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(propertyResolver
				.getProperty("dataSourceClassName"));
		if (propertyResolver.getProperty("url") == null
				|| "".equals(propertyResolver.getProperty("url"))) {
			config.addDataSourceProperty("databaseName",
					propertyResolver.getProperty("databaseName"));
			config.addDataSourceProperty("serverName",
					propertyResolver.getProperty("serverName"));
		} else {
			config.addDataSourceProperty("url",
					propertyResolver.getProperty("url"));
		}
		config.addDataSourceProperty("user",
				propertyResolver.getProperty("username"));
		config.addDataSourceProperty("password",
				propertyResolver.getProperty("password"));


		// Display datasource properties
		Properties datasourceProperties = config.getDataSourceProperties();
		SortedMap sortedSystemProperties = new TreeMap(datasourceProperties);
		Set keySet = sortedSystemProperties.keySet();
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String propertyName = (String) iterator.next();
			String propertyValue = datasourceProperties
					.getProperty(propertyName);
			log.info(propertyName + ": " + propertyValue);
		}
		return new HikariDataSource(config);
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
		liquibase.setDataSource(dataSource());
		liquibase.setChangeLog("classpath:config/liquibase/master.xml");
		liquibase.setContexts("development, production");
		return liquibase;
	}


}
