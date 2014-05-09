package org.jobscheduler.dashboard.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.jobscheduler.dashboard.swagger.RestDocumentationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
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
@AutoConfigureAfter(WebConfigurer.class)
public class RestDocumentationSwaggerConfiguration implements EnvironmentAware {

	private static final Logger log = LoggerFactory.getLogger(RestDocumentationSwaggerConfiguration.class);
	
	static private RelaxedPropertyResolver propertyResolver;
	
    @Inject
    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment);
    }
    
	@Bean(name = "swaggerPropertyPlaceHolderConfigurer")
	public PropertyPlaceholderConfigurer swaggerProperties()
			throws UnknownHostException {
		Properties properties = new Properties();
		String serverAddress  = (propertyResolver.getProperty("server.address") != null) ? propertyResolver.getProperty("server.address") :  InetAddress.getLocalHost().getCanonicalHostName();
		String port = (propertyResolver.getProperty("server.port") != null) ? propertyResolver.getProperty("server.port") : "8080"; 
		String url = "http://" + serverAddress + ":" + port;		
		log.info("To see JobScheduler Dashboard, open your browser to this url " + url);
		properties.setProperty("documentation.services.basePath", url);
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
