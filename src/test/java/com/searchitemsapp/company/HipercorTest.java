package com.searchitemsapp.company;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class HipercorTest {
	
	@Autowired
	private Hipercor hipercor;

	@Test
	void testGetId() {
		assertEquals(103l, hipercor.getId());
	}

	@Test
	void testGetUrls() throws MalformedURLException {
		final var baseUri = "https://www.hipercor.es/";
		var document = Document.createShell(baseUri);
		var element = document.getAllElements().first();
		var cssSelectorsDto = CssSelectorsDto.builder().didEmpresa(111l).selPaginacion("a.link").build();
		var urlDto = UrlDto.builder().didEmpresa(111l)
			.selectores(cssSelectorsDto).nomUrl(baseUri).build();
		element.setBaseUri(baseUri);
		element.getElementsByTag("body")
			.append("<div><a class='link' href='test'>1 de 6</a></div>");
		List<String> res = hipercor.getUrls(document, urlDto);
		assertEquals(1, res.size());
	}

	@Test
	void testRemoveInitialBrand() {
		assertEquals("HIPERCOR aceite ", hipercor.removeInitialBrand("HIPERCOR aceite"));
	}

}
