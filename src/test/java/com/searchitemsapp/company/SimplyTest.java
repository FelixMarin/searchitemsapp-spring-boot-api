package com.searchitemsapp.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
@SpringBootTest
class SimplyTest {
	
	@Autowired
	private Simply simply;

	@Test
	void testGetUrls() {
		final var baseUri = "https://www.simply.es/";
		
		var urlDto = UrlDto.builder()
				.didEmpresa(114l)
				.nomUrl(baseUri)
				.build();
		
		List<String> res = simply.getUrls(null, urlDto);
		assertNotNull(res);
		assertEquals(1, res.size());
	}

	@Test
	void testReplaceCharacters() {
		assertEquals("ca%F1ón", simply.replaceCharacters("cañón"));
		assertEquals("", simply.replaceCharacters(""));
	}

	@Test
	void testGetId() {
		assertEquals(114l, simply.getId());
	}

}
