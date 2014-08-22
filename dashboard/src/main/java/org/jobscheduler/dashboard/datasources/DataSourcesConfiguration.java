package org.jobscheduler.dashboard.datasources;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@EnableJpaRepositories("org.jobscheduler.dashboard.repository")
@EnableTransactionManagement
public class DataSourcesConfiguration extends ParentDataSourceConfiguration
		implements ApplicationContextAware {

	@Inject
	private Environment env;

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;

	}

	public HikariConfig getConfig(RelaxedPropertyResolver datasourceResolver) {

		if (datasourceResolver.getProperty("url") == null
				&& datasourceResolver.getProperty("databaseName") == null) {
			log.error(
					"Your database connection pool configuration is incorrect (url = "
							+ datasourceResolver.getProperty("url")
							+ ",  databaseName = "
							+ datasourceResolver.getProperty("databaseName")
							+ ") ! The application"
							+ " cannot start. Please check your Spring profile, current profiles are: {}",
					Arrays.toString(env.getActiveProfiles()));
			throw new ApplicationContextException(
					"Database connection pool is not configured correctly",
					null);
		}
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(datasourceResolver
				.getProperty("dataSourceClassName"));
		if (datasourceResolver.getProperty("url") == null
				|| "".equals(datasourceResolver.getProperty("url"))) {
			config.addDataSourceProperty("databaseName",
					datasourceResolver.getProperty("databaseName"));
			config.addDataSourceProperty("serverName",
					datasourceResolver.getProperty("serverName"));
		} else {
			config.addDataSourceProperty("url",
					datasourceResolver.getProperty("url"));
		}
		config.addDataSourceProperty("user",
				datasourceResolver.getProperty("username"));
		config.addDataSourceProperty("password",
				datasourceResolver.getProperty("password"));

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
		return config;
	}

	private final Logger log = LoggerFactory
			.getLogger(DataSourcesConfiguration.class);

	// @Bean(name = "toulouseDataSource")
	// // @Primary
	// public DataSource getAbstractRouterToulouse() {
	// this.propertyResolver = new RelaxedPropertyResolver(this.env,
	// "spring.datasource.");
	// log.debug("Configuring Datasource Valbonne");
	// HikariConfig config = getConfig();
	// return new HikariDataSource(config);
	//
	// }
	//
	// @Bean(name = "valbonneDataSource")
	// public DataSource getAbstractRouterValbonne() {
	//
	// this.propertyResolver = new RelaxedPropertyResolver(this.env,
	// "spring.datasource2.");
	// log.debug("Configuring Datasource Valbonne");
	// HikariConfig config = getConfig();
	// return new HikariDataSource(config);
	//
	// }

	// public DataSource getAbstractRouter() {
	//
	// this.propertyResolver = new RelaxedPropertyResolver(this.env,
	// "spring");
	// log.debug("Configuring Datasource");
	// Map<String, Object> datasourceProperties =
	// propertyResolver.getSubProperties("datasource");
	// datasourcePropertiesEntry = datasourceProperties.entrySet();
	// HikariConfig config = getConfig();
	// AutowireCapableBeanFactory autowireCapableBeanFactory =
	// this.applicationContext.getAutowireCapableBeanFactory();
	// autowireCapableBeanFactory.initializeBean(arg0, arg1)
	// return new HikariDataSource(config);
	//
	// }

	@Bean
	@Primary
	public AbstractRoutingDataSource getRouter() {

		AutowireCapableBeanFactory autowireCapableBeanFactory = this.applicationContext
				.getAutowireCapableBeanFactory();

		AbstractRoutingDataSource router = new JobSchedulerDataSourceRouter();

		Map<Object, Object> targets = new HashMap<Object, Object>();

		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(
				this.env, "spring.");
		Map<String, Object> datasourceProperties = propertyResolver
				.getSubProperties("datasource");

		int nbDatasources = datasourceProperties.size()/6;
		if ((datasourceProperties == null) || (nbDatasources < 1))
			throw new ApplicationContextException(
					"Database connection pool is not configured correctly",
					null);
		log.debug("Configuring " + nbDatasources
				+ " datasource(s)");
		for (int i = 1; i < nbDatasources+1; i++) {
			log.debug("Configuring datasource #" + i);
			RelaxedPropertyResolver datasourceResolver = new RelaxedPropertyResolver(
					propertyResolver, "datasource.datasource" + String.valueOf(i) + ".");

			System.out.println("DataSourcesConfiguration.getRouter()"
					+ datasourceResolver.getProperty("url"));

			if (datasourceResolver != null) {
				HikariConfig config = getConfig(datasourceResolver);
				DataSource datasource = new HikariDataSource(config);
				//autowireCapableBeanFactory.initializeBean(datasource, "datasource" + i);

				targets.put("datasource" + i, datasource);
			}
		}

		router.setTargetDataSources(targets);
		((JobSchedulerDataSourceRouter) router).setCurrentKey("datasource1");

		return router;
	}

	public class JobSchedulerDataSourceRouter extends AbstractRoutingDataSource {
		private final ThreadLocal<HikariDataSource> contextHolder = new ThreadLocal<HikariDataSource>();

		protected String currentKey;

		@Override
		protected Object determineCurrentLookupKey() {

			return this.currentKey;
		}

		public void setCurrentKey(String key) {
			this.currentKey = key;
		}

		public void setDataSource(String dataSource) {
			this.currentKey = dataSource;
		}

		public DataSource getDataSource() {
			System.out.println("Context data source " + contextHolder.get());
			return (DataSource) contextHolder.get();

		}

		public void clearDataSource() {
			contextHolder.remove();
		}

		public void switchDataSource(String datasource) {
			clearDataSource();

			setCurrentKey(datasource);

		}

	}
}