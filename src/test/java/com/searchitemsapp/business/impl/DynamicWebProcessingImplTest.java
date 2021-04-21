package com.searchitemsapp.business.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.business.webdriver.WebDriverManager;

@RunWith(SpringRunner.class)
@SpringBootTest
class DynamicWebProcessingImplTest {
	
	@Autowired
	WebDriverManager dynamicWebProcessing;
	
	@Test
	void testDynamicHtmlContent() throws InterruptedException {
		final String urlBase = "https://tienda.consum.es/consum/es/search?q=miel#!Grid";
		String result = dynamicWebProcessing.getDynamicHtmlContent(dynamicWebProcessing.getWebDriver(),urlBase, 116l);
		
		assertNotNull(result);
	}
	
}
