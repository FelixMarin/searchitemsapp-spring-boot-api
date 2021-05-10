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

import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
@SpringBootTest
class CapraboTest {
	
	@Autowired
	private Caprabo caprabo;

	@Test
	void testGetId() {
		assertEquals(109l, caprabo.getId());
	}

	@Test
	void testGetUrls() throws MalformedURLException {
		final String baseUri = "https://www.carritus.com/tienda/super/caprabo/cp/08034/buscar/{1}/1/200/pr_asc";
		Document document = Document.createShell(baseUri);
		UrlDto urlDto = UrlDto.builder().build();
		List<String> res = caprabo.getUrls(document, urlDto);
		assertEquals(0, res.size());
	}
}
