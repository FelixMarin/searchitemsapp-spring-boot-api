package com.searchitemsapp.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.MalformedURLException;
import java.util.List;

import org.assertj.core.util.Lists;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
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
		final String baseUri = "https://www.elcorteingles.es/supermercado/buscar/1/?term=miel&sort=priceAsc";
		Document document = Document.createShell(baseUri);
		Element element = document.getAllElements().first();
		CssSelectorsDto cssSelectorsDto = CssSelectorsDto.builder().didEmpresa(111l).selPaginacion("a|href").build();
		UrlDto urlDto = UrlDto.builder().didEmpresa(111l)
			.selectores(cssSelectorsDto).nomUrl(baseUri).build();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div><a class='link' href='test'>1 de 6</a></div>");
		List<String> res = eci.getUrls(document, urlDto);
		assertEquals(1, res.size());
	}

	@Test
	void testRemoveInitialBrand() {
		assertEquals("aceite ", eci.removeInitialBrand("HACENDADO aceite"));
	}

	@Test
	void testSelectorTextExtractor() {
		final String baseUri = "https://www.elcorteingles.es";
		final String cssSelector = "a.href";

		Document document = Document.createShell(baseUri);
		Element element = document.getAllElements().first();
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

}
