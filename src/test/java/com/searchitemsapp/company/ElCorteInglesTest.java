package com.searchitemsapp.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.util.List;

import org.assertj.core.util.Lists;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ElCorteInglesTest {
	
	@Autowired
	private ElCorteIngles eci;

	@Test
	void testGetId() {
		assertEquals(111l, eci.getId());
	}

	@Test
	void testGetUrls() throws MalformedURLException {
		final var baseUri = "https://www.elcorteingles.es/supermercado/buscar/1/?term=miel&sort=priceAsc";
		var document = Document.createShell(baseUri);
		var element = document.getAllElements().first();
		var cssSelectorsDto = CssSelectorsDto.builder().didEmpresa(111l).selPaginacion("a|href").build();
		var urlDto = UrlDto.builder().didEmpresa(111l)
			.selectores(cssSelectorsDto).nomUrl(baseUri).build();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div><a class='link' href='test'>1 de 6</a></div>");
		List<String> res = eci.getUrls(document, urlDto);
		assertNotNull(res);
		assertEquals(1, res.size());
	}

	@Test
	void testRemoveInitialBrand() {
		assertEquals("ELCORTEINGLES aceite ", eci.removeInitialBrand("ELCORTEINGLES aceite"));
	}

	@Test
	void testSelectorTextExtractor() {
		final var baseUri = "https://www.elcorteingles.es";
		final var cssSelector = "a.href";

		var document = Document.createShell(baseUri);
		var element = document.getAllElements().first();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div class='prices-price'><a href='test'><p>test</p></a></div>");
		List<String> list = Lists.newArrayList();
		list.add("a");
		list.add("href");		
		String res = eci.selectorTextExtractor(element, list, cssSelector);
		assertNotNull(res);
		assertEquals("test", res); 
	}
	
	@Test
	void testIsDynamic() {
		assertTrue(eci.isDynamic());
	}

}
