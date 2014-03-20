package org.jobscheduler.dashboard.config;

import java.net.UnknownHostException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.jobscheduler.dashboard.swagger.RestDocumentationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.configuration.ExtensibilityModule;

/**
 * Configuration Swagger
 * 
 * 
 * 
 */

@Configuration
@Import(RestDocumentationConfig.class)
@ComponentScan(basePackages = { "org.jobscheduler.dashboard.controller",
		"com.mangofactory.swagger.spring.controller" }, useDefaultFilters = false, includeFilters = { @ComponentScan.Filter({
		Controller.class, RestController.class }) })
@AutoConfigureAfter(WebConfigurer.class)
public class RestDocumentationSwaggerConfiguration {

	@Autowired
	private ServletContext servletContext;

	@Bean(name = "swaggerPropertyPlaceHolderConfigurer")
	public PropertyPlaceholderConfigurer swaggerProperties()
			throws UnknownHostException {
		Properties properties = new Properties();
		//properties.setProperty("documentation.services.basePath",
		//		servletContext.getContextPath());
		properties.setProperty("documentation.services.basePath",
		 "http://tlssbcloud12:7000/");
		properties.setProperty("documentation.services.version", "ignored");
		PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
		configurer.setProperties(properties);
		configurer.setIgnoreUnresolvablePlaceholders(true);
		return configurer;
	}

	@Bean
	public ExtensibilityModule extensibilityModule() {
		return new ExtensibilityModule();
	}

}
