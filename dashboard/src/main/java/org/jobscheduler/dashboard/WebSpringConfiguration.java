package org.jobscheduler.dashboard;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;


/**
 * Configuration JPA + Spring MVC
 * Ne pas utiliser @EnableWebMvc dans cette classe :
 * cela désactive la configuration web mvc par défaut 
 * disponible dans WebMvcAutoConfiguration
 *
 */
@Configuration
@Import({RestDocumentationSwaggerConfiguration.class, SecurityConfiguration.class})
@EnableAutoConfiguration
@ComponentScan
@PropertySource("classpath:dashboard.properties")
public class WebSpringConfiguration extends WebMvcConfigurerAdapter {

	private static final Log log = LogFactory.getLog(WebSpringConfiguration.class);
	
	@Autowired
	Environment env;
	
	
	/**
	 * Datasource
	 * 
	 * @return
	 */
	//@Bean
	public DataSource localDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}

	/**
	 * Datasource
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		basicDataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		basicDataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		basicDataSource.setPassword(env.getRequiredProperty("jdbc.password"));
		
		try {
			Connection connection = basicDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("Database not available : " + e.getMessage());
			System.exit(-1);
		}
		
		return basicDataSource;
	}
	
	/**
	 * JPA Entity Manager
	 * @param dataSource
	 * @param jpaVendorAdapter
	 * @return
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(dataSource);
		lef.setJpaVendorAdapter(jpaVendorAdapter);
		lef.setPackagesToScan("org.jobscheduler.dashboard");
		return lef;
	}
	
	/**
	 * Hibernate JPA Adapter for local database
	 * @return
	 */
	//@Bean
	public JpaVendorAdapter localJpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
		return hibernateJpaVendorAdapter;
	}
	
	/**
	 * Hibernate JPA Adapter for remote database
	 * @return
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
		return hibernateJpaVendorAdapter;
	}
	
	/**
	 * Transaction
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}
	
//	/**
//	 * Spring MVC : 
//	 * @return
//	 */
//	@Bean
//	public InternalResourceViewResolver configureJspInternalResourceViewResolver() {
//		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//		resolver.setPrefix("/WEB-INF/");
//		resolver.setSuffix(".jsp");
//		return resolver;
//	}

	
	
	/**
	 * Add swagger
	 * Add JS/HTML managed by bower
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Map all static resources coming to /rest-api-doc/** to the resource files under the 'swagger' directory
		ResourceHandlerRegistration registrationSwagger = registry.addResourceHandler("/rest-api-doc/**");
		registrationSwagger.addResourceLocations("classpath:/static/swagger/");
		
	}
	
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }	


	
	
	
	
}
