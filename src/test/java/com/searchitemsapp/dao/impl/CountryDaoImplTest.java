package com.searchitemsapp.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.dao.CountryDao;
import com.searchitemsapp.dto.CountryDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class CountryDaoImplTest {
	
	@Autowired
	private CountryDao countryDao;

	@Test
	void testFindAll() throws IOException {
		 List<CountryDto> country = countryDao.findAll();
		 assertNotNull(country);
		 assertEquals(1, country.size());
	}

	@Test
	void testFindByDid() throws IOException, ResourceNotFoundException {
		CountryDto country = CountryDto.builder().did(101l).build();
		Optional<CountryDto> resultado = countryDao.findByDid(country);
		assertTrue(resultado.isPresent());
		assertEquals("SPAIN", resultado.get().getNomPais());
	}
	
	@Test
	void testFindByDidException() {
	 
	    assertThrows(ResourceNotFoundException.class, () -> {
			CountryDto country = CountryDto.builder().did(102l).build();
			countryDao.findByDid(country);
	    });
	}
}
