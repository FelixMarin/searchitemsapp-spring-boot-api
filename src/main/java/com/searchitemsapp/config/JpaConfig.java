package com.searchitemsapp.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 
@Configuration
@EnableTransactionManagement
@ComponentScan("com.searchitemsapp.dao")
public class JpaConfig {
		
	@Autowired
	Environment env;
	
	
    public JpaConfig() {
		super();
	}

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan("com.searchitemsapp.entities");

        final var vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }
    
	@Bean
    public DataSource getDataSource() {
		var dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("hibernate.connection.driver_class"));
        dataSourceBuilder.url(env.getProperty("hibernate.connection.url"));
        dataSourceBuilder.username(env.getProperty("hibernate.connection.username"));
        dataSourceBuilder.password(env.getProperty("hibernate.connection.password"));
        return dataSourceBuilder.build();
    }
	
	private Properties additionalProperties() {
		var properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.setProperty("hibernate.current_session_context_class", env.getProperty("hibernate.current_session_context_class"));
		properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", env.getProperty("hibernate.jdbc.lob.non_contextual_creation"));
		properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		properties.setProperty("hibernate.connection.autocommit", env.getProperty("hibernate.connection.autocommit"));
		properties.setProperty("hibernate.generate_statistics", env.getProperty("hibernate.statistics"));
		return properties;
	}
}
