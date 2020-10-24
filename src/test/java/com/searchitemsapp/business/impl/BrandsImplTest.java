package com.searchitemsapp.business.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.searchitemsapp.business.Brands;

@SpringBootTest
class BrandsImplTest {
	
	@Autowired
	Brands brand;

	@Test
	void testBrandFilter() throws IOException {
		Optional<String> result = brand.brandFilter(101l, "miel");
		assertNotNull(result);
		assertTrue(result.isPresent());
	}

}
