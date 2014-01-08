package org.jobscheduler.dashboard;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class SpringConfiguration {

	@Value("9000")
	private int port;
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(dataSource);
		lef.setJpaVendorAdapter(jpaVendorAdapter);
		lef.setPackagesToScan("org.jobscheduler.dashboard");
		return lef;
	}
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		return new TomcatEmbeddedServletContainerFactory(this.port);
	}
}
