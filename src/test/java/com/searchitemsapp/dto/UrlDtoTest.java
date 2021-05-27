package com.searchitemsapp.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
		assertFalse(isEquals);
	}

}
