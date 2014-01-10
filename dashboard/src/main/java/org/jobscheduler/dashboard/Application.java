package org.jobscheduler.dashboard;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import scala.actors.threadpool.Arrays;

@Configuration
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {
	
	private static final Log log = LogFactory.getLog(Application.class);

	@Option(name = "-p", aliases = "-port", usage = "Webserver port")
	private int port = 7000;
	
	@Option(name = "-h", aliases = "-help", usage = "Print this message")
	private boolean help = false;

	@Option(name = "-dc", aliases = "-displayConfiguration", usage = "Display Spring container configuration")
	private boolean displayConfiguration = false;
	
	@Option(name = "-noSecurity", usage = "No security in Webserver")
	private boolean noSecurity = false;
	
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		return new TomcatEmbeddedServletContainerFactory(this.port);
	}
	
	public static void main(String[] args) throws CmdLineException {
		Application application = new Application();
		final CmdLineParser parser = new CmdLineParser(application);
		parser.parseArgument(args);
		
		if (application.help) {
			System.err.println("Usage : java -jar dashboard.jar [Options....]");
			parser.printUsage(System.err);
			System.exit(0);
		}
		System.setProperty("status", "ok");
        SpringApplication app = new SpringApplication(Application.class, SpringConfiguration.class);
        ConfigurableApplicationContext ctx = app.run(args);
        
        
        if (application.displayConfiguration) {
        	displayBeans(ctx);
        	displayProperties(ctx);
        }
        
        
        
        		
    }
	
	private static void displayProperties(ConfigurableApplicationContext ctx) {
		log.info("Profile available in Spring container");
		log.info("###################################");
		Environment env = ctx.getBean(Environment.class);
		String[] profiles = env.getDefaultProfiles();
		for (String profile : profiles) {
			log.info("  - "  + profile);
		}
		
		log.info("###################################");

		
	}

	/**
	 * Helper class to display beans available in spring container
	 * @param ctx
	 */
	public static void displayBeans(ConfigurableApplicationContext ctx) {
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		log.info("###################################");
		log.info("Beans available in Spring container");
		log.info("###################################");
		for (String beanName : beanNames) {
			log.info("  - "  + beanName + " - " + ctx.getBean(beanName).getClass()); 
			
		}
		log.info("###################################");
	}

	@Override
	public void customize(ConfigurableEmbeddedServletContainerFactory factory) {
		MimeMappings mimeMappings = new MimeMappings(MimeMappings.DEFAULT);
		mimeMappings.add("html",  "text/html;charset=utf-8");
		factory.setMimeMappings(mimeMappings);
	}
	

}
