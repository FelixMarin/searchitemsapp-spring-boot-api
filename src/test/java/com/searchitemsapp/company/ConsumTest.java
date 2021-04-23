package com.searchitemsapp.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.business.webdriver.impl.WebDriverManagerImpl;
import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
@SpringBootTest
class ConsumTest {
	
	@Autowired
	private Consum consum; 
	
	@Autowired
	WebDriverManagerImpl dynamicWebProcessing;

	@Test
	void testGetUrls() throws MalformedURLException {
		final String baseUri = "https://tienda.consum.es/";
				
		UrlDto urlDto = UrlDto.builder()
				.didEmpresa(116l)
				.nomUrl(baseUri)
				.build();
		
		List<String> res = consum.getUrls(null, urlDto);
		assertEquals(1, res.size());
	}

	@Test
	void testGetHtmlContent() throws InterruptedException {
		final String baseUri = "https://tienda.consum.es/consum/es/search?q=miel#!Grid";
		String content = consum.getHtmlContent(dynamicWebProcessing.getWebDriver().get(), baseUri);
		assertNotNull(content);
	}

	@Test
	void testGetId() {
		assertEquals(116l, consum.getId());
	}

	@Test
	void testIsDynamic() {
		assertTrue(consum.isDynamic());
	}

}
