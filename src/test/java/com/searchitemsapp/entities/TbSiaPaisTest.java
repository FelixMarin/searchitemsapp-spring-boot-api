package com.searchitemsapp.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
class TbSiaPaisTest {
	
	private static TbSiaPais tbSiaPais;
	
	@BeforeAll
	static void inicializar() {
		tbSiaPais = new TbSiaPais();
	}

	@Test
	void testGetId() {
		tbSiaPais.setDid(101l);
		assertEquals(101l,tbSiaPais.getDid());
	}

	@Test
	void testGetBolActivo() {
		tbSiaPais.setBolActivo(true);
		assertTrue(tbSiaPais.getBolActivo());
	}

	@Test
	void testGetDesPais() {
		tbSiaPais.setDesPais("test");
		assertEquals("test",tbSiaPais.getDesPais());
	}

	@Test
	void testGetNomPais() {
		tbSiaPais.setNomPais("test");
		assertEquals("test",tbSiaPais.getNomPais());
	}

	@Test
	void testGetTbSiaEmpresas() {
		tbSiaPais.setTbSiaEmpresas(Lists.newArrayList());
		assertNotNull(tbSiaPais.getTbSiaEmpresas());
	}

	@Test
	void testAddTbSiaEmpresa() {
		tbSiaPais.setTbSiaEmpresas(Lists.newArrayList());
		var empresa = new TbSiaEmpresa();
		tbSiaPais.addTbSiaEmpresa(empresa);
		assertEquals(1,tbSiaPais.getTbSiaEmpresas().size());
		tbSiaPais.removeTbSiaEmpresa(empresa);
		assertEquals(0,tbSiaPais.getTbSiaEmpresas().size());
	}

	@Test
	void testGetTbSiaNomProductos() {
		tbSiaPais.setTbSiaNomProductos(Lists.newArrayList());
		assertNotNull(tbSiaPais.getTbSiaNomProductos());
	}

	@Test
	void testAddTbSiaNomProducto() {
		tbSiaPais.setTbSiaNomProductos(Lists.newArrayList());
		var productos = new TbSiaNomProducto();
		tbSiaPais.addTbSiaNomProducto(productos);
		assertEquals(1,tbSiaPais.getTbSiaNomProductos().size());
		tbSiaPais.removeTbSiaNomProducto(productos);
		assertEquals(0,tbSiaPais.getTbSiaEmpresas().size());
	}
	
	@Test 
	void tesToString() {
		assertNotNull(tbSiaPais.toString());
		assertNotEquals("", tbSiaPais.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", tbSiaPais.hashCode());
	}
	
	@Test
	void testCanEquals() {
		var isEquals = tbSiaPais.equals(new TbSiaPais());
		assertFalse(isEquals);
	}

}
