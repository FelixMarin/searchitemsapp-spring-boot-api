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
class AlcampoTest {
	
	@Autowired
	Alcampo alcampo;

	@Test
	void testGetUrls() throws MalformedURLException {
		final var baseUri = "https://www.alcampo.es/compra-online/search?q=miel%3Aprice-asc&page=1";
		
		var cssSelectorsDto =CssSelectorsDto.builder().didEmpresa(105l).selPaginacion("a|href").build();
		var urlDto = UrlDto.builder().didEmpresa(105l)
			.selectores(cssSelectorsDto).nomUrl(baseUri).build();
		var document = Document.createShell(baseUri);
		var element = document.getAllElements().first();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div><a class='link' href='&page=3'>1 de 6</a></div>");
		List<String> res = alcampo.getUrls(document, urlDto);
		assertEquals(4, res.size());
		
		cssSelectorsDto.setSelPaginacion("div");
		element.getElementsByTag("div").remove();
		res = alcampo.getUrls(document, urlDto);
		assertNotNull(res);
		assertEquals(4, res.size());
	}

	@Test 
	void testGetId() {
		assertEquals(108l, alcampo.getId());
	}

}
