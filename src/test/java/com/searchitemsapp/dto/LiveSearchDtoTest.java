package com.searchitemsapp.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LiveSearchDtoTest {
	
	private static LiveSearchDto liveSearchDto;
	
	@BeforeAll
	static void inicializar() {
		liveSearchDto = new LiveSearchDto();
	}

	@Test
	void testGetDid() {
		liveSearchDto.setDid(101l);
		assertEquals(101l,liveSearchDto.getDid());
	}

	@Test
	void testGetNomProducto() {
		liveSearchDto.setNomProducto("test");
		assertEquals("test",liveSearchDto.getNomProducto());
	}
	
	@Test 
	void tesToString() {
		assertNotNull(liveSearchDto.toString());
		assertNotEquals("", liveSearchDto.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", liveSearchDto.hashCode());
	}
	
	@Test
	void testCanEquals() {
		assertTrue(liveSearchDto.canEqual(new LiveSearchDto()));
		var isEquals = liveSearchDto.equals(new LiveSearchDto());
		assertFalse(isEquals);
	}

}
