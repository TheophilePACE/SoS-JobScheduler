package org.jobscheduler.dashboard.datasources;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.jobscheduler.dashboard.config.DatabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
public class DataSourcesConfiguration extends ParentDataSourceConfiguration {
	

	private RelaxedPropertyResolver propertyResolver;
	
	@Inject
	private Environment env;
	
public HikariConfig getConfig() {
		
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
		return config ;
	}
	
	private final Logger log = LoggerFactory
			.getLogger(DataSourcesConfiguration.class);
	
	@Bean(name="toulouseDataSource")
//	@Primary
	public DataSource getAbstractRouterToulouse(){
		this.propertyResolver = new RelaxedPropertyResolver(this.env,
		"spring.datasource1.");
		log.debug("Configuring Datasource Valbonne");
		HikariConfig config  = getConfig() ;
		return new HikariDataSource(config);
	
		
		
	}
	
	
	@Bean(name="valbonneDataSource")
	public DataSource getAbstractRouterValbonne(){
		
		this.propertyResolver = new RelaxedPropertyResolver(this.env,
				"spring.datasource2.");
				log.debug("Configuring Datasource Valbonne");
				HikariConfig config  = getConfig() ;
				return new HikariDataSource(config);
		
		
	}
	
	
	
	@Bean
	@Primary
	public AbstractRoutingDataSource getRouter(){
		
		AbstractRoutingDataSource router = new JobSchedulerDataSourceRouter()  ;
		
		Map<Object,Object> targets = new HashMap<Object,Object>() ;
		
		targets.put("Toulouse",getAbstractRouterToulouse()) ;
		targets.put("Valbonne",getAbstractRouterValbonne() ) ;

		router.setTargetDataSources(targets);
		//router.setDefaultTargetDataSource(getAbstractRouterToulouse());
		((JobSchedulerDataSourceRouter) router).setCurrentKey("Toulouse");
		
		return router ;
	}
	

	
	
	public class JobSchedulerDataSourceRouter extends AbstractRoutingDataSource{
		private final ThreadLocal<HikariDataSource> contextHolder = new ThreadLocal<HikariDataSource>();
		
		
		protected String currentKey ;
		@Override
		protected Object determineCurrentLookupKey() {
			
			return this.currentKey ;
		}
		
		
		public void setCurrentKey(String key){
			this.currentKey = key ; 
		}
		
		
		 public  void setDataSource(String dataSource) {
			 this.currentKey = dataSource ;
	   }
		 
		 public  DataSource getDataSource() {
			   System.out.println("Context data source " +contextHolder.get()) ;
		          return (DataSource) contextHolder.get();

		   }

		   public  void clearDataSource() {
		          contextHolder.remove();
		   }
		   
		   public  void switchDataSource(String datasource){
			   clearDataSource();

			   	setCurrentKey(datasource);
			   	
		
	}
	
	

	}
}