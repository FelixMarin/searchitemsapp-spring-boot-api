package com.searchitemsapp.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.searchitemsapp.dao.CompanyDao;
import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.CompanyDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

@SpringBootTest
class CompanyDaoImplTest {
	
	   @Autowired
	    private CompanyDao companyDao;
	
	@Test
	void testFindAll() throws IOException {
		
			List<CompanyDto> listaEmpresasDto = companyDao.findAll();		
			assertEquals(15, listaEmpresasDto.size());
	}

	@Test
	void testFindByDid()  throws IOException, ResourceNotFoundException {
			
		Optional<CompanyDto> empresaDtoResult = companyDao.findByDid(CompanyDto.builder().did(101l).build());
		assertEquals("MERCADONA", empresaDtoResult.get().getNomEmpresa());
	}

	@Test
	void testFindByTbSia()  throws IOException {
		
		List<CompanyDto> listaEmpresasDto = companyDao.findByTbSia(
				CompanyDto.builder().did(101l).build(), 
				CategoryDto.builder().did(101l).build());
		
		assertEquals(1, listaEmpresasDto.size());
		assertEquals("MERCADONA", listaEmpresasDto.get(0).getNomEmpresa());
	}
}
