package com.searchitemsapp.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.EnterpriseDto;

@SpringBootTest
class EnterpriseDaoImplTest {
	
	   @Autowired
	    private EnterpriseDaoImpl empresaDaoImpl;
	
	@Test
	void testFindAll() throws IOException {
		
			List<EnterpriseDto> listaEmpresasDto = empresaDaoImpl.findAll();		
			assertEquals(15, listaEmpresasDto.size());
	}

	@Test
	void testFindByDid()  throws IOException {
			
		EnterpriseDto empresaDtoResult = empresaDaoImpl.findByDid(EnterpriseDto.builder().did(101).build());
		assertEquals("MERCADONA", empresaDtoResult.getNomEmpresa());
	}

	@Test
	void testFindByTbSia()  throws IOException {
		
		List<EnterpriseDto> listaEmpresasDto = empresaDaoImpl.findByTbSia(
				EnterpriseDto.builder().did(101).build(), 
				CategoryDto.builder().did(101).build());
		
		assertEquals(1, listaEmpresasDto.size());
		assertEquals("MERCADONA", listaEmpresasDto.get(0).getNomEmpresa());
	}
}
