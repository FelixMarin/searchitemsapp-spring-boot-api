package com.searchitemsapp.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TbSiaEmpresaTest {
	
	private static TbSiaEmpresa tbSiaEmpresa;
	
	@BeforeAll
	static void inicializar() {
		tbSiaEmpresa = new TbSiaEmpresa();
	}

	@Test
	void testGetId() {
		tbSiaEmpresa.setDid(101l);
		assertEquals(101l,tbSiaEmpresa.getDid());
	}

	@Test
	void testGetBolActivo() {
		tbSiaEmpresa.setBolActivo(true);
		assertTrue(tbSiaEmpresa.getBolActivo());
	}

	@Test
	void testGetDesEmpresa() {
		tbSiaEmpresa.setDesEmpresa("test");
		assertEquals("test",tbSiaEmpresa.getDesEmpresa());
	}

	@Test
	void testGetNomEmpresa() {
		tbSiaEmpresa.setNomEmpresa("test");
		assertEquals("test",tbSiaEmpresa.getNomEmpresa());
	}

	@Test
	void testGetTbSiaCategoriasEmpresa() {
		var catEmpresa = new TbSiaCategoriasEmpresa();
		tbSiaEmpresa.setTbSiaCategoriasEmpresa(catEmpresa);
		assertNotNull(tbSiaEmpresa.getTbSiaCategoriasEmpresa());
	}

	@Test
	void testGetTbSiaPais() {
		var tbSiaPais = new TbSiaPais();
		tbSiaEmpresa.setTbSiaPais(tbSiaPais);
		assertNotNull(tbSiaEmpresa.getTbSiaPais());
	}

	@Test
	void testGetTbSiaUrls() {
		List<TbSiaUrl> tbSiaUrls = Lists.newArrayList();
		tbSiaEmpresa.setTbSiaUrls(tbSiaUrls);
		assertNotNull(tbSiaEmpresa.getTbSiaUrls());
	}

	@Test
	void testAddTbSiaUrl() {
		tbSiaEmpresa.setTbSiaUrls(Lists.newArrayList());
		var url = new TbSiaUrl();
		tbSiaEmpresa.addTbSiaUrl(url);
		assertEquals(1,tbSiaEmpresa.getTbSiaUrls().size());
		tbSiaEmpresa.removeTbSiaUrl(url);
		assertEquals(0,tbSiaEmpresa.getTbSiaUrls().size());
	}

	@Test
	void testGetBolDynScrap() {
		tbSiaEmpresa.setBolDynScrap(true);
		assertTrue(tbSiaEmpresa.getBolDynScrap());
	}

	@Test
	void testGetTbSiaSelectoresCsses() {
		List<TbSiaSelectoresCss> tbSiaSelectoresCss = Lists.newArrayList();
		tbSiaEmpresa.setTbSiaSelectoresCsses(tbSiaSelectoresCss);
		assertNotNull(tbSiaEmpresa.getTbSiaSelectoresCsses());
	}
	
	@Test 
	void tesToString() {
		assertNotNull(tbSiaEmpresa.toString());
		assertNotEquals("", tbSiaEmpresa.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", tbSiaEmpresa.hashCode());
	}
	
	@Test
	void testCanEquals() {
		var isEquals = tbSiaEmpresa.equals(new TbSiaEmpresa());
		assertFalse(isEquals);
	}

}
