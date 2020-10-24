package com.searchitemsapp.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.searchitemsapp.dao.BrandsDao;
import com.searchitemsapp.dto.BrandsDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

@SpringBootTest
class BrandsDaoImplTest {
	
	@Autowired
	private BrandsDao brandsDao;

	@Test
	void testFindAll() throws IOException {
		List<BrandsDto> listDto = brandsDao.findAll();
		assertEquals(1306, listDto.size());
	}
	
	@Test
	void testFindByDid() throws IOException, ResourceNotFoundException {
		BrandsDto brands = BrandsDto.builder().did(101l).build();
		Optional<BrandsDto> optionalBrand = brandsDao.findByDid(brands);
		assertTrue(optionalBrand.isPresent());
		assertNotNull(optionalBrand);
		assertEquals("5 Cobalt ", optionalBrand.get().getNomMarca());
	}
	
}
