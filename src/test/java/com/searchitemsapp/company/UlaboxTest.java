package com.searchitemsapp.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.MalformedURLException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
@SpringBootTest
class UlaboxTest {
	
	@Autowired
	private Ulabox ulabox;

	@Test
	void testGetUrls() throws MalformedURLException {
		final var baseUri = "https://www.ulabox.com/";
		var document = Document.createShell(baseUri);
		var element = document.getAllElements().first();
		var cssSelectorsDto = CssSelectorsDto.builder().didEmpresa(106l).selPaginacion("a.link").build();
		var urlDto = UrlDto.builder().didEmpresa(106l)
			.selectores(cssSelectorsDto).nomUrl(baseUri).build();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div><a class='link' href='test'>1 de 6</a></div>");
		List<String> res = ulabox.getUrls(document, urlDto);
		assertNotNull(res);
		assertEquals(5, res.size());
		
		document = Document.createShell(baseUri);
		element = document.getAllElements().first();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
		.append("<div><a class='link' href='test'>1…6</a></div>");
		res = ulabox.getUrls(document, urlDto);
		assertNotNull(res);
		assertEquals(1, res.size());
	}

	@Test
	void testGetId() {
		assertEquals(106l, ulabox.getId());
	}

}
