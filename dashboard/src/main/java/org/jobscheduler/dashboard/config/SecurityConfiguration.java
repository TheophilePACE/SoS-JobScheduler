package org.jobscheduler.dashboard.config;

import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.jobscheduler.dashboard.security.AjaxAuthenticationFailureHandler;
import org.jobscheduler.dashboard.security.AjaxAuthenticationSuccessHandler;
import org.jobscheduler.dashboard.security.AjaxLogoutSuccessHandler;
import org.jobscheduler.dashboard.security.AuthoritiesConstants;
import org.jobscheduler.dashboard.security.CustomPersistentRememberMeServices;
import org.jobscheduler.dashboard.security.Http401UnauthorizedEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Inject
	private Environment env;

	@Inject
	private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

	@Inject
	private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

	@Inject
	private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

	@Inject
	private Http401UnauthorizedEntryPoint authenticationEntryPoint;

	@Bean
	public RememberMeServices rememberMeServices() {
		return new CustomPersistentRememberMeServices(env, userDetailsService());
	}

	@Bean
	public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
		return new RememberMeAuthenticationProvider(
				env.getProperty("jhipster.security.rememberme.key"));
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new org.jobscheduler.dashboard.security.UserDetailsService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(
				passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				// .antMatchers("/app/components/**")
				.antMatchers("/app/**")//.antMatchers("/rest/**")
				// .antMatchers("/app/home/*.js")
				.antMatchers("/rest-api-doc/**").antMatchers("/img/**")
				.antMatchers("/css/**").antMatchers("/console/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
        http
        .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
        .rememberMe()
            .rememberMeServices(rememberMeServices())
            .key(env.getProperty("jhipster.security.rememberme.key"))
            .and()
        .formLogin()
            .loginProcessingUrl("/authentication")
            .successHandler(ajaxAuthenticationSuccessHandler)
            .failureHandler(ajaxAuthenticationFailureHandler)
            .usernameParameter("j_username")
            .passwordParameter("j_password")
            .permitAll()
            .and() 
        .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(ajaxLogoutSuccessHandler)
            .deleteCookies("JSESSIONID")
            .permitAll()
            .and()

        .authorizeRequests()
            .antMatchers("/authenticate").permitAll()
            .antMatchers("/rest/logs/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/rest/**").authenticated()
            // Swagger JSON properties
            .antMatchers("/api-docs/**").permitAll()
            .antMatchers("/websocket/tracker").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/websocket/**").permitAll()
            .antMatchers("/metrics*").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/metrics/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/health*").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/health/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/trace*").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/dump*").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/dump/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/shutdown*").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/shutdown/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/beans*").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/beans/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/info*").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/info/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/autoconfig*").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/autoconfig/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/env*").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/env/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/trace*").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN);
        
		http.csrf().disable();

		// disable csrf for upload request
//		http.csrf().requireCsrfProtectionMatcher(new RequestMatcher() {
//			private Pattern allowedMethods = Pattern
//					.compile("^(GET|HEAD|TRACE|OPTIONS)$");
//			private RegexRequestMatcher apiMatcher = new RegexRequestMatcher(
//					"/api-docs.*", "GET");
//
//			@Override
//			public boolean matches(HttpServletRequest request) {
//				// CSRF due to REST api call
//				if (request.getRequestURI().startsWith("/api-docs")) {
//					return true;
//				}
//				// CSRF false for everything else that is not an API call or an
//				// allowedMethod
//				return false;
//			}
//		});
	}

	@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
	private static class GlobalSecurityConfiguration extends
			GlobalMethodSecurityConfiguration {
	}
}
