package com.searchitemsapp.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

import com.searchitemsapp.dao.UrlDao;
import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.CountryDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class UrlDaoImplTest {
	
	@Autowired
	private UrlDao dao;

	@Test
	void testObtenerUrlsPorIdEmpresa() throws IOException {
		CountryDto paisDto = CountryDto.builder().did(101l).build();
		CategoryDto categoriaDto = CategoryDto.builder().did(101l).build();
		List<UrlDto> urls = dao.obtenerUrlsPorIdEmpresa(paisDto, categoriaDto, "101");
		assertNotNull(urls);
		assertFalse(urls.isEmpty());
		
		urls = dao.obtenerUrlsPorIdEmpresa(paisDto, categoriaDto, "101,103");
		assertNotNull(urls);
		assertFalse(urls.isEmpty());
		
		urls = dao.obtenerUrlsPorIdEmpresa(paisDto, categoriaDto, "ALL");
		assertNotNull(urls);
		assertFalse(urls.isEmpty());
	}
	
	@Test
	void testObtenerUrlsPorIdEmpresaException() throws IOException {
		CountryDto paisDto = CountryDto.builder().did(101l).build();
		CategoryDto categoriaDto = CategoryDto.builder().did(101l).build();

	    assertThrows(NullPointerException.class, () -> {
	    	dao.obtenerUrlsPorIdEmpresa(paisDto, categoriaDto, null);
	    });
	}

	@Test
	void testFindByDid() throws IOException, ResourceNotFoundException {
		UrlDto url = UrlDto.builder().did(101l).build();
		Optional<UrlDto> optional = dao.findByDid(url);
		assertTrue(optional.isPresent());
		assertEquals(101l, optional.get().getDid());
	}
	
	@Test
	void testFindByDidException() {
	 
	    assertThrows(ResourceNotFoundException.class, () -> {
	    	UrlDto productName = UrlDto.builder().did(999l).build();
	    	dao.findByDid(productName);
	    });
	}
}
