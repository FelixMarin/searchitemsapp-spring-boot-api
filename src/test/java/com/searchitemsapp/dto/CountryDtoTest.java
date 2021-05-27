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
class CountryDtoTest {
	
	private static CountryDto country;

	@BeforeAll
	static void testBuilder() {
		country = CountryDto.builder().build();
	}

	@Test
	void testDid() {
		country.setDid(101l);
		assertEquals(101l,country.getDid());
	}

	@Test
	void testBolActivo() {
		country.setBolActivo(true);
		assertEquals(true,country.getBolActivo());
	}

	@Test
	void testDesPais() {
		country.setDesPais("test");
		assertEquals("test",country.getDesPais());
	}

	@Test
	void testNomPais() {
		country.setNomPais("test");
		assertEquals("test",country.getNomPais());
	}

	@Test
	void testEmpresas() {
		var lsDto = CompanyDto.builder().did(101l).build();
		country.setEmpresas(lsDto);;
		assertEquals(101l,country.getEmpresas().getDid());
	}
	
	@Test
	void testProductos() {
		var lsDto = LiveSearchDto.builder().did(101l).build();
		country.setProductos(lsDto);
		assertEquals(101l,country.getProductos().getDid());
	}
	
	@Test 
	void tesToString() {
		assertNotNull(country.toString());
		assertNotEquals("", country.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", country.hashCode());
	}
	
	@Test
	void testCanEquals() {
		assertTrue(country.canEqual(CountryDto.builder().build()));
		var isEquals = country.equals(CountryDto.builder().build());
		assertFalse(isEquals);
	}

}
