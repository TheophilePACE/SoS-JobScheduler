/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
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
