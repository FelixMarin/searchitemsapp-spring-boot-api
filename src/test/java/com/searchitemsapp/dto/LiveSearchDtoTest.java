package com.searchitemsapp.dto;

import static org.junit.jupiter.api.Assertions.*;

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
		assertTrue(isEquals);
	}

}
