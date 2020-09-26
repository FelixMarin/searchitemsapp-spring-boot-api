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
		
		EnterpriseDto empresaDto = new EnterpriseDto();
		empresaDto.setDid(101);
		
		EnterpriseDto empresaDtoResult = empresaDaoImpl.findByDid(empresaDto);
		
		assertEquals("MERCADONA", empresaDtoResult.getNomEmpresa());
	}

	@Test
	void testFindByTbSia()  throws IOException {
		EnterpriseDto empresaDto = new EnterpriseDto();
		CategoryDto categoriaDto = new CategoryDto();
		
		empresaDto.setDid(101);
		categoriaDto.setDid(101);
		
		List<EnterpriseDto> listaEmpresasDto = empresaDaoImpl.findByTbSia(empresaDto, categoriaDto);
		
		assertEquals(1, listaEmpresasDto.size());
		assertEquals("MERCADONA", listaEmpresasDto.get(0).getNomEmpresa());
	}
}
