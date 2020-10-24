package com.searchitemsapp.dao.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.searchitemsapp.dao.CssSelectorsDao;
import com.searchitemsapp.dto.CompanyDto;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

@SpringBootTest
class CssSelectorsDaoImplTest {
	
	@Autowired
	private CssSelectorsDao dao;

	@Test
	void testFindAll() throws IOException {
		List<CssSelectorsDto> cssSelectors = dao.findAll();
		assertEquals(14, cssSelectors.size());
	}

	@Test
	void testFindByDid() throws IOException, ResourceNotFoundException {
		CssSelectorsDto selector = CssSelectorsDto.builder().did(101l).build();
		Optional<CssSelectorsDto> resultado = dao.findByDid(selector);
		assertTrue(resultado.isPresent());
		assertEquals(101l, resultado.get().getDidEmpresa());
	}

	@Test
	void testFindByTbSia() throws IOException {
		CompanyDto empresaDto = CompanyDto.builder().did(101l).build();
		List<CssSelectorsDto> listDto = dao.findByTbSia(empresaDto);
		assertFalse(listDto.isEmpty());
	}

	@Test
	void testFindByDidException() {
	 
	    assertThrows(ResourceNotFoundException.class, () -> {
	    	CssSelectorsDto selector = CssSelectorsDto.builder().did(999l).build();
	    	dao.findByDid(selector);
	    });
	}

}
