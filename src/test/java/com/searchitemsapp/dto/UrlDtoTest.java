package com.searchitemsapp.dto;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UrlDtoTest {

	private static UrlDto urlDto;
	
	@BeforeAll
	static void inicializar() {
		urlDto = new UrlDto();
	}

	@Test 
	void tesToString() {
		assertNotNull(urlDto.toString());
		assertNotEquals("", urlDto.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", urlDto.hashCode());
	}
	
	@Test
	void testCanEquals() {
		assertTrue(urlDto.canEqual(UrlDto.builder().build()));
		var isEquals = urlDto.equals(UrlDto.builder().build());
		assertTrue(isEquals);
	}

}
