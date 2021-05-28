package com.searchitemsapp.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
class SearchItemsParamsDtoTest {

	private static SearchItemsParamsDto searchItemsParamsDto;
	
	@BeforeAll
	static void inicializar() {
		searchItemsParamsDto = new SearchItemsParamsDto();
	}

	@Test 
	void tesToString() {
		assertNotNull(searchItemsParamsDto.toString());
		assertNotEquals("", searchItemsParamsDto.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", searchItemsParamsDto.hashCode());
	}
	
	@Test
	void testCanEquals() {
		assertTrue(searchItemsParamsDto.canEqual(SearchItemsParamsDto.builder().build()));
		var isEquals = searchItemsParamsDto.equals(SearchItemsParamsDto.builder().build());
		assertTrue(isEquals);
	}

}
