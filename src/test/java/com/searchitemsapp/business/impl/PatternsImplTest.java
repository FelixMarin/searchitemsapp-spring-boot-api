package com.searchitemsapp.business.impl;

import static org.junit.Assert.assertNotNull;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.business.Patterns;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class PatternsImplTest {
	
	@Autowired
	private Patterns pattern;

	@Test
	void testSelectScrapPattern() {
		String[] productsArray = {"leche", "de", "coco"};
		var pat =pattern.createPatternProduct(productsArray);
		assertNotNull(pat);
	}

	@Test
	void testCreatePatternProduct() {
		var document = Document.createShell("https://www.dia.es/compra-online/search?q={1}%3Aprice-asc&page=0&disp=");
		var elements = pattern.selectScrapPattern(document, "div.space", null);
		elements = pattern.selectScrapPattern(document, "div.space", "test");
		assertNotNull(elements);
	}

}
