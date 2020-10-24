package com.searchitemsapp.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.assertj.core.util.Lists;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
		final String baseUri = "https://www.carrefour.es/supermercado/c?No=1&Ns=product.salepointActivePrice_004320%7C0%7C%7Cproduct.description%7C0&Ntt={1}&sb=true";
		Document document = Document.createShell(baseUri);
		Element element = document.getAllElements().first();
		CssSelectorsDto cssSelectorsDto = CssSelectorsDto.builder().didEmpresa(105l).selPaginacion("a|href").build();
		UrlDto urlDto = UrlDto.builder().didEmpresa(105l)
			.selectores(cssSelectorsDto).nomUrl(baseUri).build();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div><a class='link' href='&page=3'>1 de 6</a></div>");
		List<String> res = carrefour.getUrls(document, urlDto);
		assertEquals(2, res.size());
	}

	@Test
	void testGetId() {
		assertEquals(104l, carrefour.getId());
	}
	
	@Test
	void testGetJsoupConnection() throws MalformedURLException {
		final String baseUri = "https://www.carrefour.es/supermercado/c?No=1&Ns=product.salepointActivePrice_004320%7C0%7C%7Cproduct.description%7C0&Ntt=miel&sb=true";
		Connection res = carrefour.getJsoupConnection(baseUri, "miel");
		assertNotNull(res.response());
	}

	@Test
	void testGetJsoupDocument() throws IOException {
		final String baseUri = "https://www.carrefour.es/supermercado/c?No=1&Ns=product.salepointActivePrice_004320%7C0%7C%7Cproduct.description%7C0&Ntt=miel&sb=true";
		Connection res = carrefour.getJsoupConnection(baseUri, "miel");
		Document document = carrefour.getJsoupDocument(res.execute(), baseUri);
		assertNotNull(document);
	}

	@Test
	void testIsDynamic() {
		assertFalse(carrefour.isDynamic());
	}

	@Test
	void testRemoveInitialBrand() {
		String espected = "Remove Initial Brand";
		assertEquals(espected, carrefour.removeInitialBrand(espected));
	}

	@Test
	void testReplaceCharacters() {
		String espected = "Replace Characters";
		assertEquals(espected, carrefour.replaceCharacters(espected));
	}

	@Test
	void testGetAllUrlsToSearch() {
		ProductDto product = ProductDto.builder().nomUrlAllProducts("").build();
		assertEquals("", carrefour.getAllUrlsToSearch(product));
	}

	@Test
	void testSelectorTextExtractor() throws MalformedURLException {
		final String baseUri = "https://www.carrefour.es";
		final String cssSelector = "a|href";

		Document document = Document.createShell(baseUri);
		Element element = document.getAllElements().first();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div><a href='test'></a></div>");
		List<String> list = Lists.newArrayList();
		list.add("a");
		list.add("href");		
		String res = carrefour.selectorTextExtractor(element, list, cssSelector);
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

