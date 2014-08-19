package org.jobscheduler.dashboard.datasources;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
//@ConfigurationProperties(prefix = "spring.datasource")
@EnableJpaRepositories("org.jobscheduler.dashboard.repository")
@EnableTransactionManagement
public abstract class ParentDataSourceConfiguration  {
	
	
	private final Logger log = LoggerFactory
			.getLogger(ParentDataSourceConfiguration.class);
	
//	@Bean(name="parentDataSource")
//	@Primary
//	public DriverManagerDataSource getAbstractRouter(){
//		log.info("Configuring the parent DataSource");
////		HikariConfig config = new HikariConfig() ;
////		config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
//		
//		return new DriverManagerDataSource() ;
//	}
	

}
