package com.searchitemsapp.company;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.util.List;

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
class LidlTest {
	
	@Autowired
	private Lidl lidl;

	@Test
	void testGetUrls() throws MalformedURLException {
		final var baseUri = "https://www.lidlonline.es/";
		
		var urlDto = UrlDto.builder()
				.didEmpresa(102l)
				.nomUrl(baseUri)
				.build();
		
		List<String> res = lidl.getUrls(null, urlDto);
		assertEquals(0, res.size());
	}

	@Test
	void testGetId() {
		assertEquals(102l, lidl.getId());
	}

}
