package com.searchitemsapp.controller;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.searchitemsapp.dto.ProductDto;

@SpringBootTest
@ActiveProfiles("test") 
@EntityScan("com.searchitemsapp.entities")
@EnableJpaRepositories("com.searchitemsapp.dao.repository")
@PropertySource("classpath:db.properties")
@PropertySource("classpath:flow.properties")
@PropertySource("classpath:text.properties")
@PropertySource("classpath:log4j.properties")
@EnableTransactionManagement
class SearchItemsControllerTest {

	
	@Autowired
	SearchItemsController ac;

	@Test
	void statusCodeOkTest() {
		
		/*ResponseEntity<List<ProductDto>> result = ac.searchItems("101", "101", "1", "sal", "101");
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		
		result = ac.searchItems(null, null, null, "sal", "103");
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		
		result = ac.searchItems(null, null, "1", "sal", "104");
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		
		result = ac.searchItems(null, "101", null, "sal", "105");
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		
		result = ac.searchItems("101", null, null, "sal", "106");
		assertEquals(result.getStatusCode(), HttpStatus.OK);*/
	}
	
	@Test
	void exceptionNoProductTest() {
		
		  Assertions.assertThrows(NullPointerException.class, () -> {
			  ac.searchItems("101", "101", "1", null, "107");
		  });
	}
	
	@Test
	void exceptionNoEnterpriseTest() {
		
		  Assertions.assertThrows(NullPointerException.class, () -> {
			  ac.searchItems("101", "101", "1", "sal", null);
		  });
	}

}
