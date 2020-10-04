package com.searchitemsapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@SuppressWarnings("deprecation")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        
        return resolver;
    }
    
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
			
		};
	}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/resources/**")
        		.addResourceLocations("classpath:/resources/");
        registry.addResourceHandler("/static/**")
        		.addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/css/**")
        		.addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
        		.addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/img/**")
        		.addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/logo/**")
		.addResourceLocations("classpath:/static/img/logo");
    }
}