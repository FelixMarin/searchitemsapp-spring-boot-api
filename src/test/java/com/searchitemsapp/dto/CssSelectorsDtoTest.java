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
class CssSelectorsDtoTest {
	
	private static CssSelectorsDto cssSelectorsDto;
	
	@BeforeAll
	static void inicializar() {
		cssSelectorsDto = new CssSelectorsDto();
	}

	@Test 
	void tesToString() {
		assertNotNull(cssSelectorsDto.toString());
		assertNotEquals("", cssSelectorsDto.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", cssSelectorsDto.hashCode());
	}
	
	@Test
	void testCanEquals() {
		assertTrue(cssSelectorsDto.canEqual(CssSelectorsDto.builder().build()));
		var isEquals = cssSelectorsDto.equals(CssSelectorsDto.builder().build());
		assertTrue(isEquals);
	}

}
