package com.searchitemsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = "com.searchitemsapp",
				excludeFilters = @Filter(Repository.class))
@EntityScan("com.searchitemsapp.entities")
@EnableJpaRepositories("com.searchitemsapp.dao.repository")
@PropertySource("classpath:db.properties")
@PropertySource("classpath:flow.properties")
@PropertySource("classpath:log4j.properties")
@EnableTransactionManagement
@EnableScheduling
@EnableCaching
@EnableAspectJAutoProxy
public class SiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiaApplication.class, args);
	}

}
