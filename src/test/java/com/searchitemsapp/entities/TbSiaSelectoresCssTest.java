package com.searchitemsapp.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TbSiaSelectoresCssTest {
	
	private static TbSiaSelectoresCss tbSiaSelectoresCss;
	
	@BeforeAll
	static void inicializar() {
		tbSiaSelectoresCss = new TbSiaSelectoresCss();
		assertNotNull(tbSiaSelectoresCss);
	}

	@Test
	void testGetDid() {
		tbSiaSelectoresCss.setDid(101l);
		assertEquals(101l,tbSiaSelectoresCss.getDid());
	}

	@Test
	void testGetBolActivo() {
		tbSiaSelectoresCss.setBolActivo(true);
		assertEquals(true,tbSiaSelectoresCss.getBolActivo());
	}

	@Test
	void testGetFecModificacion() {
		var fecModificacion = LocalDate.now();
		tbSiaSelectoresCss.setFecModificacion(fecModificacion);
		assertEquals(fecModificacion, tbSiaSelectoresCss.getFecModificacion());
	}

	@Test
	void testGetScrapNoPattern() {
		tbSiaSelectoresCss.setScrapNoPattern("test");
		assertEquals("test",tbSiaSelectoresCss.getScrapNoPattern());
	}

	@Test
	void testGetScrapPattern() {
		tbSiaSelectoresCss.setScrapPattern("test");
		assertEquals("test",tbSiaSelectoresCss.getScrapPattern());
	}

	@Test
	void testGetSelImage() {
		tbSiaSelectoresCss.setSelImage("test");
		assertEquals("test",tbSiaSelectoresCss.getSelImage());
	}

	@Test
	void testGetSelImage2() {
		tbSiaSelectoresCss.setSelImage2("test");
		assertEquals("test",tbSiaSelectoresCss.getSelImage2());
	}

	@Test
	void testGetSelLinkProd() {
		tbSiaSelectoresCss.setSelLinkProd("test");
		assertEquals("test",tbSiaSelectoresCss.getSelLinkProd());
	}

	@Test
	void testGetSelPreKilo() {
		tbSiaSelectoresCss.setSelPreKilo("test");
		assertEquals("test",tbSiaSelectoresCss.getSelPreKilo());
	}

	@Test
	void testGetSelPrecio() {
		tbSiaSelectoresCss.setSelPrecio("test");
		assertEquals("test",tbSiaSelectoresCss.getSelPrecio());
	}

	@Test
	void testGetSelProducto() {
		tbSiaSelectoresCss.setSelProducto("test");
		assertEquals("test",tbSiaSelectoresCss.getSelProducto());
	}

	@Test
	void testGetTbSiaEmpresa() {
		var tbSiaEmpresa = new TbSiaEmpresa();
		tbSiaSelectoresCss.setTbSiaEmpresa(tbSiaEmpresa);
		assertEquals(tbSiaEmpresa,tbSiaSelectoresCss.getTbSiaEmpresa());
	}

	@Test
	void testGetTbSiaUrl() {
		var tbSiaUrl = new TbSiaUrl();
		tbSiaSelectoresCss.setTbSiaUrl(tbSiaUrl);
		assertEquals(tbSiaUrl,tbSiaSelectoresCss.getTbSiaUrl());
	}

	@Test
	void testGetSelPaginacion() {
		tbSiaSelectoresCss.setSelPaginacion("test");
		assertEquals("test",tbSiaSelectoresCss.getSelPaginacion());
	}
	
	@Test 
	void tesToString() {
		assertNotNull(tbSiaSelectoresCss.toString());
		assertNotEquals("", tbSiaSelectoresCss.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", tbSiaSelectoresCss.hashCode());
	}
	
	@Test
	void testCanEquals() {
		var isEquals = tbSiaSelectoresCss.equals(new TbSiaSelectoresCss());
		assertFalse(isEquals);
	}

}
