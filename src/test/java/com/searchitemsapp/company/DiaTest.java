package com.searchitemsapp.company;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.util.List;

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
class DiaTest {
	
	@Autowired
	private Dia dia;

	@Test
	void testGetUrls() throws MalformedURLException {
		final String baseUri = "https://www.dia.es/compra-online/search?q=aceite%3Aprice-asc&page=0&disp=";
		Document document = Document.createShell(baseUri);
		Element element = document.getAllElements().first();
		CssSelectorsDto cssSelectorsDto = CssSelectorsDto.builder().didEmpresa(105l).selPaginacion("a|href").build();
		UrlDto urlDto = UrlDto.builder().didEmpresa(105l)
			.selectores(cssSelectorsDto).nomUrl(baseUri).build();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div><a class='link' href='test'>1 de 6</a></div>");
		List<String> res = dia.getUrls(document, urlDto);
		assertEquals(2, res.size());
	}

	@Test
	void testGetId() {
		assertEquals(105l, dia.getId());
	}

	@Test
	void testRemoveInitialBrand() {
		assertEquals("HACENDADO aceite ", dia.removeInitialBrand("HACENDADO aceite"));
	}

}
