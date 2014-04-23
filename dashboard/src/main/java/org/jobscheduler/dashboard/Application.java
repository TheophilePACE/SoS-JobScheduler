package org.jobscheduler.dashboard;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;


/**
 * To run JobScheduler Dashboard in a Java standalone application
 * 
 * @author cloud
 * 
 */
@ComponentScan
@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class})
public class Application  {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Option(name = "-h", aliases = "-help", usage = "Print this message")
	private boolean help = false;

	@Option(name = "-dc", aliases = "-displayConfiguration", usage = "Display Spring container configuration")
	private boolean displayConfiguration = false;

	@Option(name = "-noSecurity", usage = "No security in Webserver")
	private boolean noSecurity = false;

    @Autowired
    private Environment env;
    
    /**
     * Initializes JobScheduler.
     * <p/>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p/>
     */
    @PostConstruct
    public void initApplication() throws IOException {
        if (env.getActiveProfiles().length == 0) {
            log.warn("No Spring profile configured, running with default configuration");
        } else {
            log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        }
    }

//	@Bean
//	public EmbeddedServletContainerFactory servletContainer() {
//		return new TomcatEmbeddedServletContainerFactory(this.port);
//	}

	public static void main(String[] args) throws CmdLineException {
		Application application = new Application();
		final CmdLineParser parser = new CmdLineParser(application);
		//parser.parseArgument(args);

		if (application.help) {
			System.err.println("Usage : java -jar dashboard.jar [Options....]");
			System.err.println("You can use a specific application properties file with -Dspring.profiles.active=<suffix>");
			System.err.println("     e.g. : with -Dspring.profiles.active=prod, you will use application-prod.yml");
			parser.printUsage(System.err);
			System.exit(0);
		}
		
		SpringApplication app = new SpringApplication(Application.class);
		
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);

        // Check if the selected profile has been set as argument.
        // if not the development profile will be added
        addDefaultProfile(app, source);
        
		ConfigurableApplicationContext ctx = app.run(args);

		if (application.displayConfiguration) {
			displayBeans(ctx);
			displayProperties(ctx);
		}

	}
	
    /**
     * Set a default profile if it has not been set
     */
    private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
        if (!source.containsProperty("spring.profiles.active")) {
            app.setAdditionalProfiles(Constants.SPRING_PROFILE_DEVELOPMENT);
        }
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

//	@Override
//	public void customize(ConfigurableEmbeddedServletContainer container) {
//		MimeMappings mimeMappings = new MimeMappings(MimeMappings.DEFAULT);
//		mimeMappings.add("html", "text/html;charset=utf-8");
//		container.setMimeMappings(mimeMappings);
//	}

}
