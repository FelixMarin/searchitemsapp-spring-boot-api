package com.searchitemsapp.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.EmpresaDTO;

@SpringBootTest
class EmpresaDaoImplTest {
	
	   @Autowired
	    private EmpresaDaoImpl empresaDaoImpl;
	
	@Test
	void testFindAll() throws IOException {
		
			List<EmpresaDTO> listaEmpresasDto = empresaDaoImpl.findAll();		
			assertEquals(15, listaEmpresasDto.size());
	}

	@Test
	void testFindByDid()  throws IOException {
		
		EmpresaDTO empresaDto = new EmpresaDTO();
		empresaDto.setDid(101);
		
		EmpresaDTO empresaDtoResult = empresaDaoImpl.findByDid(empresaDto);
		
		assertEquals("MERCADONA", empresaDtoResult.getNomEmpresa());
	}

	@Test
	void testFindByTbSia()  throws IOException {
		EmpresaDTO empresaDto = new EmpresaDTO();
		CategoriaDTO categoriaDto = new CategoriaDTO();
		
		empresaDto.setDid(101);
		categoriaDto.setDid(101);
		
		List<EmpresaDTO> listaEmpresasDto = empresaDaoImpl.findByTbSia(empresaDto, categoriaDto);
		
		assertEquals(1, listaEmpresasDto.size());
		assertEquals("MERCADONA", listaEmpresasDto.get(0).getNomEmpresa());
	}
}
