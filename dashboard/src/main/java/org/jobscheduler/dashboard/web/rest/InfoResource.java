package org.jobscheduler.dashboard.web.rest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.inject.Inject;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.cache.CacheManager;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Read information 
 * @author cloud
 *
 */
@RestController
public class InfoResource implements EnvironmentAware {

	private RelaxedPropertyResolver propertyResolver;
	
//	@Inject()
//	CacheManager cacheManager;
//	
    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment);
    }
    
    
    /**
     * Get URL to access to REST-API definition for Swagger Index.html
     * @return
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/rest/info/restapi",
            method = RequestMethod.GET,
            produces = "application/json")
    public String restApi() throws UnknownHostException {
		String serverAddress  = (propertyResolver.getProperty("server.address") != null) ? propertyResolver.getProperty("server.address") :  InetAddress.getLocalHost().getCanonicalHostName();
		String port = (propertyResolver.getProperty("server.port") != null) ? propertyResolver.getProperty("server.port") : "8080"; 
		return "{ \"url\" : \"http://" + serverAddress + ":" + port + "\"}";	
    }

    /**
     * Give git information
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/rest/info/git",
            method = RequestMethod.GET,
            produces = "application/json")
    public Properties gitInformation() throws IOException  {
// TODO MANAGE Cache
//    	Properties properties;
//    	if (cacheManager != null) {
//    		Cache cacheGitInformation = cacheManager.getCache("gitInformation");
//    		
//    	}
    		
    	Resource resource = new ClassPathResource("git.properties");
    	return PropertiesLoaderUtils.loadProperties(resource);
    }
    
}
