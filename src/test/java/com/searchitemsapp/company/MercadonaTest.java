package com.searchitemsapp.company;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
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

import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MercadonaTest {
	
	private final String baseUrl = "https://7uzjkl1dj0-dsn.algolia.net/1/indexes/products_prod_vlc1_es/query?x-algolia-agent=Algolia%20for%20JavaScript%20(3.33.0)%3B%20Browser&x-algolia-application-id=7UZJKL1DJ0&x-algolia-api-key=9d8f2e39e90df472b4f2e559a116fe17";


	@Autowired
	private Mercadona mercadona;
	
	@Test
	void testGetUrls() throws MalformedURLException {
		final var baseUri = "https://mercadona.es/";
		
		var urlDto = UrlDto.builder()
				.didEmpresa(101l)
				.nomUrl(baseUri)
				.build();
		
		List<String> res = mercadona.getUrls(null, urlDto);
		assertNotNull(res);
		assertEquals(1, res.size());
	}

	@Test
	void testGetId() {
		assertEquals(101l, mercadona.getId());
	}

	@Test
	void testGetJsoupConnection() {
		final var productName = "miel";
		var con = mercadona.getJsoupConnection(baseUrl, productName);
		assertNotNull(con.response());
	}

	@Test
	void testGetJsoupDocument() throws IOException {
		var productName = "arroz";
		var con = mercadona.getJsoupConnection(baseUrl, productName);
		var document = mercadona.getJsoupDocument(con.execute(), baseUrl);
		assertNotNull(document);
		
		productName = "";
		document = mercadona.getJsoupDocument(con.execute(), baseUrl);
		assertNotNull(document);
		
		productName = "*";
		con = mercadona.getJsoupConnection("http://www.posttestserver.com/", productName);
		document = mercadona.getJsoupDocument(con.execute(), "https://test.page");
		assertNull(document);
	}

	@Test
	void testGetAllUrlsToSearch() {
		var product = ProductDto.builder()
				.nomProducto("test product name")
				.imagen("image test mock")
				.build();
		
		String res = mercadona.getAllUrlsToSearch(product);
		assertEquals("https://lolamarket.com/tienda/mercadona/buscar/test%20product%20name", res);
	}

	@Test
	void testSelectorTextExtractor() {
		final var baseUri = "https://www.mercadona.es";
		final var cssSelector = "a";

		var document = Document.createShell(baseUri);
		var element = document.getAllElements().first();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div class='prices-price'><a href='test'><p>test</p></a></div>");
		List<String> list = Lists.newArrayList();
		list.add("a");
		list.add("href");		
		var res = mercadona.selectorTextExtractor(element, list, cssSelector);
		assertNotNull(res);
		assertEquals("test", res); 
	}
}
