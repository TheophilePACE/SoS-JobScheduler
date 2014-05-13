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
package org.jobscheduler.dashboard;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

/**
 * To run JobScheduler Dashboard in a Java standalone application
 * 
 * @author cloud
 * 
 */
@ComponentScan
@EnableAutoConfiguration(exclude = { MetricFilterAutoConfiguration.class,
		MetricRepositoryAutoConfiguration.class })
public class Application {

	private static final Logger log = LoggerFactory
			.getLogger(Application.class);

	@Autowired
	private ConfigurableEnvironment env;

	/**
	 * Initializes JobScheduler.
	 * <p/>
	 * Spring profiles can be configured with a program arguments
	 * --spring.profiles.active=your-active-profile
	 * <p/>
	 */
	@PostConstruct
	public void initApplication() throws IOException {
		if (env.getActiveProfiles().length == 0) {
			log.warn("No Spring profile configured, running with default configuration");
		} else {
			log.info("Running with Spring profile(s) : {}",
					Arrays.toString(env.getActiveProfiles()));
		}
	}

	public static void main(String[] args) {
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(
				args);

		SpringApplication app = new SpringApplication(Application.class);

		// Display help if argument help
		if (source.containsProperty("help")) {
			System.err.println("Usage : java -jar dashboard.war [Options....]");
			System.err
					.println("You can use a specific application properties file with -Dspring.profiles.active=<suffix>");
			System.err
					.println("     e.g. : with -Dspring.profiles.active=prod, you will use application-prod.yml");
			System.err
					.println("     --dc : Display Spring container configuration");
			System.err.println("     --help : Display this information");
			System.exit(0);
		}

		// Check if the selected profile has been set as argument.
		// if not the development profile will be added
		addDefaultProfile(app, source);

		ConfigurableApplicationContext ctx = app.run(args);

		// Display Spring container configuration if argument dc
		if (source.containsProperty("dc")) {
			displayBeans(ctx);
			displayProperties(ctx);
		}

	}

	/**
	 * Set a default profile if it has not been set
	 */
	private static void addDefaultProfile(SpringApplication app,
			SimpleCommandLinePropertySource source) {
		
//		if (!source.containsProperty("spring.profiles.active")) {
//			app.setAdditionalProfiles(Constants.SPRING_PROFILE_DEVELOPMENT);
//		}
		
	}

	private static void displayProperties(ConfigurableApplicationContext ctx) {
		log.info("Profile available in Spring container");
		log.info("###################################");
		Environment env = ctx.getBean(Environment.class);
		String[] profiles = env.getDefaultProfiles();
		for (String profile : profiles) {
			log.info("  - " + profile);
		}

		log.info("###################################");

	}

	/**
	 * Helper class to display beans available in spring container
	 * 
	 * @param ctx
	 */
	public static void displayBeans(ConfigurableApplicationContext ctx) {
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		log.info("###################################");
		log.info("Beans available in Spring container");
		log.info("###################################");
		for (String beanName : beanNames) {
			log.info("  - " + beanName + " - "
					+ ctx.getBean(beanName).getClass());

		}
		log.info("###################################");
	}

	// @Override
	// public void customize(ConfigurableEmbeddedServletContainer container) {
	// MimeMappings mimeMappings = new MimeMappings(MimeMappings.DEFAULT);
	// mimeMappings.add("html", "text/html;charset=utf-8");
	// container.setMimeMappings(mimeMappings);
	// }

	// @Bean
	// public EmbeddedServletContainerFactory servletContainer() {
	// return new TomcatEmbeddedServletContainerFactory(this.port);
	// }

}
