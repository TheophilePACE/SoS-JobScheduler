package org.jobscheduler.dashboard;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@Order(Ordered.LOWEST_PRECEDENCE - 6)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Disable CSRF
		http.csrf().requireCsrfProtectionMatcher(new RequestMatcher() {
	        private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
	        private RegexRequestMatcher apiMatcher = new RegexRequestMatcher("/jobDefinitionUpload.*", "POST");

	        @Override
	        public boolean matches(HttpServletRequest request) {
	            // No CSRF due to allowedMethod
	            if(allowedMethods.matcher(request.getMethod()).matches())
	                return false;

	            // No CSRF due to api call
	            if(apiMatcher.matches(request))
	                return false;

	            // CSRF for everything else that is not an API call or an allowedMethod
	            return true;
	        }
	    });
		http.authorizeRequests().antMatchers("/**").permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder)
			throws Exception {
		//authManagerBuilder.inMemoryAuthentication().withUser(username)
	}
}
