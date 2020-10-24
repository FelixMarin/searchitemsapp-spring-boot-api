package com.searchitemsapp.company;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
@SpringBootTest
class EroskiTest {
	
	@Autowired
	private Eroski eroski;

	@Test
	void testGetUrls() throws MalformedURLException {
		final String baseUri = "https://supermercado.eroski.es/";
		
		UrlDto urlDto = UrlDto.builder()
				.didEmpresa(116l)
				.nomUrl(baseUri)
				.build();
		
		List<String> res = eroski.getUrls(null, urlDto);
		assertEquals(1, res.size());
	}

	@Test
	void testReplaceCharacters() {
		assertEquals("ca$00f1$00f3n", eroski.replaceCharacters("cañón"));
		assertEquals("", eroski.replaceCharacters(""));
	}

	@Test
	void testGetId() {
		assertEquals(107l, eroski.getId());
	}

}
