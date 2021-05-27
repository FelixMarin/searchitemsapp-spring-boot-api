package com.searchitemsapp.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.assertj.core.util.Lists;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
@SpringBootTest
class CarrefourTest {
	
	@Autowired
	private Carrefour carrefour;
	
	@Test
	void testGetUrls() throws MalformedURLException {
		final var baseUri = "https://www.carrefour.es/supermercado/c?No=1&Ns=product.salepointActivePrice_004320%7C0%7C%7Cproduct.description%7C0&Ntt={1}&sb=true";
		var document = Document.createShell(baseUri);
		var element = document.getAllElements().first();
		var cssSelectorsDto = CssSelectorsDto.builder().didEmpresa(105l).selPaginacion("a|href").build();
		var urlDto = UrlDto.builder().didEmpresa(105l)
			.selectores(cssSelectorsDto).nomUrl(baseUri).build();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div><a class='link' href='&page=3'>1 de 6</a></div>");
		List<String> res = carrefour.getUrls(document, urlDto);
		assertNotNull(res);
		assertEquals(2, res.size());
	}

	@Test
	void testGetId() {
		assertEquals(104l, carrefour.getId());
	}
	
	@Test
	void testGetJsoupConnection() throws MalformedURLException {
		final var baseUri = "https://www.carrefour.es/supermercado/c?No=1&Ns=product.salepointActivePrice_004320%7C0%7C%7Cproduct.description%7C0&Ntt=miel&sb=true";
		var res = carrefour.getJsoupConnection(baseUri, "miel");
		assertNotNull(res.response());
	}

	@Test
	void testGetJsoupDocument() throws IOException {
		final var baseUri = "https://www.carrefour.es/supermercado/c?No=1&Ns=product.salepointActivePrice_004320%7C0%7C%7Cproduct.description%7C0&Ntt=miel&sb=true";
		var res = carrefour.getJsoupConnection(baseUri, "miel");
		var document = carrefour.getJsoupDocument(res.execute(), baseUri);
		assertNotNull(document);
	}

	@Test
	void testIsDynamic() {
		assertFalse(carrefour.isDynamic());
	}

	@Test
	void testRemoveInitialBrand() {
		var espected = "Remove Initial Brand ";
		assertEquals(espected, carrefour.removeInitialBrand(espected));
	}

	@Test
	void testReplaceCharacters() {
		var espected = "Replace Characters";
		assertEquals(espected, carrefour.replaceCharacters(espected));
	}

	@Test
	void testGetAllUrlsToSearch() {
		var product = ProductDto.builder().nomUrlAllProducts("").build();
		assertEquals("", carrefour.getAllUrlsToSearch(product));
	}

	@Test
	void testSelectorTextExtractor() throws MalformedURLException {
		final var baseUri = "https://www.carrefour.es";
		final var cssSelector = "a|href";

		var document = Document.createShell(baseUri);
		var element = document.getAllElements().first();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div><a href='test'></a></div>");
		List<String> list = Lists.newArrayList();
		list.add("a");
		list.add("href");		
		var res = carrefour.selectorTextExtractor(element, list, cssSelector);
		assertNotNull(res);
		assertEquals("test", res); 
		
		list = Lists.newArrayList();
		list.add("a");
		res = carrefour.selectorTextExtractor(element, list, cssSelector);
		assertEquals("", res);
		
		list = Lists.newArrayList();
		res = carrefour.selectorTextExtractor(element, list, cssSelector);
		assertEquals("", res);
	}
}

