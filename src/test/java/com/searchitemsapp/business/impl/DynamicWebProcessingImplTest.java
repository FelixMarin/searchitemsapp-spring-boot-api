package com.searchitemsapp.business.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.ElementClickInterceptedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.business.DynamicWebProcessing;

@RunWith(SpringRunner.class)
@SpringBootTest
class DynamicWebProcessingImplTest {
	
	@Autowired
	DynamicWebProcessing dynamicWebProcessing;
	
	@Test
	void testDynamicHtmlContent() throws InterruptedException {
		final String urlBase = "https://tienda.consum.es/consum/es/search?q=miel#!Grid";
		String result = dynamicWebProcessing.getDynamicHtmlContent(urlBase, 116l,0l);
		
		assertThrows(ElementClickInterceptedException.class, () -> {
			dynamicWebProcessing.getDynamicHtmlContent(urlBase, 116l,2l);
		});
		
		assertThrows(ElementClickInterceptedException.class, () -> {
			dynamicWebProcessing.getDynamicHtmlContent(urlBase, 116l,1l);
		});
		
		assertNotNull(result);
	}
	
}
