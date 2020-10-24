package com.searchitemsapp.dto;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class BrandsDtoTest {
	
	private BrandsDto brand;

	@BeforeEach
	void testBuilder() {
		brand = new BrandsDto();
		brand = new BrandsDto(101l,"MERCADONA",101l,"SUP", 101l,"SPAIN");
		brand.hashCode();
		brand.toString();
		brand = BrandsDto.builder().build();
		assertNotNull(brand);
	}

	@Test
	void testDid() {
		brand.setDid(101l);
		assertEquals(101l, brand.getDid());
	}

	@Test
	void testNomMarca() {
		String nomMarca = "MERCADONA";
		brand.setNomMarca(nomMarca);
		assertEquals(nomMarca, brand.getNomMarca());
	}

	@Test
	void testDidCatEmpresas() {
		Long catEmpresa = 101l;
		brand.setDidCatEmpresas(catEmpresa);
		assertEquals(catEmpresa, brand.getDidCatEmpresas());
	}

	@Test
	void testNomCatEmpresas() {
		String nomCatEmpresas = "SUP";
		brand.setNomCatEmpresas(nomCatEmpresas);
		assertEquals(nomCatEmpresas, brand.getNomCatEmpresas());
	}

	@Test
	void testDidPais() {
		Long pais = 101l;
		brand.setDidPais(pais);
		assertEquals(pais, brand.getDidPais());
	}

	@Test
	void testNomPais() {
		String nomCatEmpresas = "SPAIN";
		brand.setNomPais(nomCatEmpresas);
		assertEquals(nomCatEmpresas, brand.getNomPais());
	}
}
