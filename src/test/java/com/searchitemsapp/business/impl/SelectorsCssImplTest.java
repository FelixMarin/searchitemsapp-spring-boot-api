package com.searchitemsapp.business.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.SelectorsCss;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
@SpringBootTest
class SelectorsCssImplTest {
	
	@Autowired
	private SelectorsCss selectorsCss;

	@Test
	void testSelectorCssListByEnterprise() {
		List<CssSelectorsDto> dto = selectorsCss.selectorCssListByEnterprise("105");
		assertEquals(1, dto.size());
		
		dto = selectorsCss.selectorCssListByEnterprise("ALL");
		assertEquals(12, dto.size());
		
	}

	@Test
	void testValidateSelector() {
		final var baseUri = "https://www.dia.es";
		
		var document = Document.createShell(baseUri);
		Element element = document.getAllElements().first();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div class='load-more-products'></div>");
		
		Optional<Element> res = selectorsCss.validateSelector(element);
		assertNotNull(res);
		assertTrue(res.isPresent());
	}
	
	@Test
	void testValidateSelector1() {
		final String baseUri = "https://www.dia.es";
		
		var document = Document.createShell(baseUri);
		var element = document.getAllElements().first();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div class='accesoPopupPeso26163'></div>");
		
		Optional<Element> res = selectorsCss.validateSelector(element);
		assertNotNull(res);
		assertTrue(res.isPresent());
	}
	
	@Test
	void testValidateSelector2() {
		final var baseUri = "https://www.dia.es";
		
		var document = Document.createShell(baseUri);
		var element = document.getAllElements().first();
		
		element.setBaseUri(baseUri);
		
		Optional<Element> res = selectorsCss.validateSelector(element);
		assertNotNull(res);
		assertTrue(res.isEmpty());
	}

	@Test
	void testAddCssSelectors() {
		var baseUri = "https://www.dia.es";
		var urlDto = UrlDto.builder().didEmpresa(105l).nomUrl(baseUri).build();
		List<CssSelectorsDto> lista = Lists.newArrayList();
		lista.add(CssSelectorsDto.builder().didEmpresa(105l).build());
		var dto = selectorsCss.addCssSelectors(urlDto, lista);
		assertNotNull(dto);
		
		 baseUri = "//www.dia.es";
		 dto = selectorsCss.addCssSelectors(urlDto, lista);
		 assertNotNull(dto);
	}

	@Test
	void testElementByCssSelector() throws MalformedURLException {
		final var baseUri = "https://www.dia.es";
		
		var document = Document.createShell(baseUri);
		var element = document.getAllElements().first();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div class='load-more-products'><p>test</p></div>");
		
		var urlDto = UrlDto.builder().didEmpresa(105l).nomUrl(baseUri).build();
		
		var res = selectorsCss.elementByCssSelector(element, "div.load-more-products", urlDto);
		
		assertEquals("test", res);
		
		element.getElementsByTag("div").remove();
		element.getElementsByTag("body")
		.append("<div class='load-more-products'><p>/</p></div>");
		res = selectorsCss.elementByCssSelector(element, "div.load-more-products", urlDto);
		assertEquals("https://www.dia.es/", res);
		
		element.getElementsByTag("div").remove();
		element.getElementsByTag("body")
		.append("<div class='load-more-products'><p>//</p></div>");
		res = selectorsCss.elementByCssSelector(element, "div.load-more-products", urlDto);
		assertEquals("https://", res);
	}

}
