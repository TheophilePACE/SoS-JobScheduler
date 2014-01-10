package org.jobscheduler.dashboard;

import java.net.UnknownHostException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.jobscheduler.dashboard.swagger.NameEndPointComparator;
import org.jobscheduler.dashboard.swagger.NameOperationComparator;
import org.jobscheduler.dashboard.swagger.RestDocumentationConfig;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mangofactory.swagger.EndpointComparator;
import com.mangofactory.swagger.OperationComparator;
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
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class RestDocumentationSwaggerConfiguration implements
		ServletContextAware {

	private ServletContext servletContext;

	@Bean(name = "swaggerPropertyPlaceHolderConfigurer")
	public PropertyPlaceholderConfigurer swaggerProperties()
			throws UnknownHostException {
		Properties properties = new Properties();
		// properties.setProperty("documentation.services.basePath",
		// servletContext.getContextPath());
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

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;

	}

}
