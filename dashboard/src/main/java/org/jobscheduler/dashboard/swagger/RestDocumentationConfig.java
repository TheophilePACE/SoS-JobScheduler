package org.jobscheduler.dashboard.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.mangofactory.swagger.EndpointComparator;
import com.mangofactory.swagger.OperationComparator;
import com.mangofactory.swagger.configuration.DocumentationConfig;

@Configuration
@Import(DocumentationConfig.class)
public class RestDocumentationConfig {
	@Bean
    public EndpointComparator endPointComparator() {
        return new NameEndPointComparator();
    }

    @Bean
    public OperationComparator operationComparator() {
        return new NameOperationComparator();
    }
	
}
