package com.searchitemsapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.searchitemsapp.config.filter.JwtAuthorizationFilter;
import com.searchitemsapp.config.filter.RegistrationFilter;
import com.searchitemsapp.config.filter.TimeAccessFilter;
import com.searchitemsapp.controller.ExceptionController;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("sia.users")
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilter(jwtAuthorizationFilter());
	
	}
	
	@Bean
	public FilterRegistrationBean<RegistrationFilter> registrationFilter() {
		FilterRegistrationBean<RegistrationFilter> filterRegistrationBean = new FilterRegistrationBean<RegistrationFilter>();
		filterRegistrationBean.setFilter(new RegistrationFilter());
		filterRegistrationBean.addUrlPatterns(ExceptionController.REGISTRATION);
		return filterRegistrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<TimeAccessFilter> timeAccessFileter() {
		FilterRegistrationBean<TimeAccessFilter> timeAccessFilter = new FilterRegistrationBean<TimeAccessFilter>();
		timeAccessFilter.setFilter(new TimeAccessFilter());
		timeAccessFilter.addUrlPatterns(ExceptionController.OUT_OF_TIME);
		timeAccessFilter.setOrder(Ordered.LOWEST_PRECEDENCE - 1);
		return timeAccessFilter;
	}
	
	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter()  throws Exception {
		return new JwtAuthorizationFilter(this.authenticationManager());
	}
	
}
