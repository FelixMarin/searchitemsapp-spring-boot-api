package com.searchitemsapp.dto;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
class CategoryDtoTest {
	
	private static CategoryDto categoriyDto;
	
	@BeforeAll
	static void testBuilder() {
		categoriyDto = CategoryDto.builder().build();
		assertNotNull(categoriyDto);
	}

	@Test
	void testDid() {
		categoriyDto.setDid(101l);
		assertEquals(101l,categoriyDto.getDid());
	}

	@Test
	void testBolActivo() {
		categoriyDto.setBolActivo(true);
		assertEquals(true,categoriyDto.getBolActivo());
	}

	@Test
	void testDesCatEmpresa() {
		categoriyDto.setDesCatEmpresa("test");
		assertEquals("test",categoriyDto.getDesCatEmpresa());
	}

	@Test
	void testNomCatEmpresa() {
		categoriyDto.setNomCatEmpresa("test");
		assertEquals("test",categoriyDto.getNomCatEmpresa());
	}

	@Test
	void testEmpresas() {
		var lsDto = CompanyDto.builder().did(101l).build();
		categoriyDto.setEmpresas(lsDto);;
		assertEquals(101l,categoriyDto.getEmpresas().getDid());
	}

	@Test
	void testProductos() {
		var lsDto = LiveSearchDto.builder().did(101l).build();
		categoriyDto.setProductos(lsDto);
		assertEquals(101l,categoriyDto.getProductos().getDid());
	}

}
