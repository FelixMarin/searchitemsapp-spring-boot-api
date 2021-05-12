package com.searchitemsapp.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
class TbSiaCategoriasEmpresaTest {
	
	private static TbSiaCategoriasEmpresa tbSiaCategoriasEmpresa;
	
	@BeforeAll
	static void inicializar() {
		tbSiaCategoriasEmpresa = new TbSiaCategoriasEmpresa();
		tbSiaCategoriasEmpresa.setTbSiaEmpresas(Lists.newArrayList());
	}

	@Test
	void testGetDid() {
		tbSiaCategoriasEmpresa.setDid(101l);
		assertEquals(101l,tbSiaCategoriasEmpresa.getDid());
	}

	@Test
	void testGetBolActivo() {
		tbSiaCategoriasEmpresa.setBolActivo(true);
		assertEquals(true,tbSiaCategoriasEmpresa.getBolActivo());
	}

	@Test
	void testGetDesCatEmpresa() {
		tbSiaCategoriasEmpresa.setDesCatEmpresa("test");
		assertEquals("test",tbSiaCategoriasEmpresa.getDesCatEmpresa());
	}

	@Test
	void testGetNomCatEmpresa() {
		tbSiaCategoriasEmpresa.setNomCatEmpresa("test");
		assertEquals("test",tbSiaCategoriasEmpresa.getNomCatEmpresa());
	}

	@Test
	void testAddTbSiaEmpresa() {
		TbSiaEmpresa tbSiaEmpresa = new TbSiaEmpresa();
		tbSiaCategoriasEmpresa.addTbSiaEmpresa(tbSiaEmpresa);
		assertEquals(tbSiaEmpresa,tbSiaCategoriasEmpresa.getTbSiaEmpresas().get(0));
	}

	@Test
	void testRemoveTbSiaEmpresa() {
		TbSiaEmpresa tbSiaEmpresa = new TbSiaEmpresa();
		tbSiaCategoriasEmpresa.addTbSiaEmpresa(tbSiaEmpresa);
		tbSiaCategoriasEmpresa.removeTbSiaEmpresa(tbSiaEmpresa);
		assertEquals(0,tbSiaCategoriasEmpresa.getTbSiaEmpresas().size());
	}

}
