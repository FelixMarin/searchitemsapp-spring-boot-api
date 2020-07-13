package com.searchitemsapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.searchitemsapp.controller.ApplicationController;

@SpringBootTest
@ActiveProfiles("test") 
@EntityScan("com.searchitemsapp.entities")
@EnableJpaRepositories("com.searchitemsapp.dao.repository")
@PropertySource("classpath:db.properties")
@PropertySource("classpath:flow.properties")
@PropertySource("classpath:log4j.properties")
@EnableTransactionManagement
class SiaApplicationTests {
	
	@Autowired
	ApplicationController ac;

	@Test
	void contextLoads() {
		
		String result = ac.listaProductos("101", "101", "1", "sal", "101");
		
		assertNotNull(result);
	}

}
