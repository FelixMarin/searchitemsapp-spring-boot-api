package com.searchitemsapp.company;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
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
		final var baseUri = "https://www.carritus.com/tienda/super/caprabo/cp/08034/buscar/{1}/1/200/pr_asc";
		var document = Document.createShell(baseUri);
		var urlDto = UrlDto.builder().build();
		List<String> res = caprabo.getUrls(document, urlDto);
		assertEquals(1, res.size());
	}
}
