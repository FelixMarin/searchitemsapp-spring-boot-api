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
        		.addResourceLocations("static/");
        registry.addResourceHandler("/static/css/**")
        		.addResourceLocations("static/css/");
        registry.addResourceHandler("/static/js/**")
        		.addResourceLocations("static/js/");
        registry.addResourceHandler("/static/img/**")
        		.addResourceLocations("static/img/");
    }
}