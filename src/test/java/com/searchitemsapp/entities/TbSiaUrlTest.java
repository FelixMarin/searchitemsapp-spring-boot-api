package com.searchitemsapp.entities;

import static org.junit.jupiter.api.Assertions.*;

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
class TbSiaUrlTest {
	
	private static TbSiaUrl tbSiaUrl;
	
	@BeforeAll
	static void inicializar() {
		tbSiaUrl = new TbSiaUrl();
		assertNotNull(tbSiaUrl);
	}


	@Test
	void testGetDid() {
		tbSiaUrl.setDid(101l);
		assertEquals(101l,tbSiaUrl.getDid());
	}

	@Test
	void testGetBolActivo() {
		tbSiaUrl.setBolActivo(true);
		assertEquals(true,tbSiaUrl.getBolActivo());
	}

	@Test
	void testGetDesUrl() {
		tbSiaUrl.setDesUrl("test");
		assertEquals("test",tbSiaUrl.getDesUrl());
	}

	@Test
	void testGetNomUrl() {
		tbSiaUrl.setNomUrl("test");
		assertEquals("test",tbSiaUrl.getNomUrl());
	}

	@Test
	void testGetTbSiaEmpresa() {
		var tbSiaEmpresa = new TbSiaEmpresa();
		tbSiaUrl.setTbSiaEmpresa(tbSiaEmpresa);
		assertEquals(tbSiaEmpresa,tbSiaUrl.getTbSiaEmpresa());
	}

	@Test
	void testGetBolStatus() {
		tbSiaUrl.setBolStatus(true);
		assertEquals(true,tbSiaUrl.getBolStatus());
	}

	@Test
	void testGetBolLogin() {
		tbSiaUrl.setBolLogin(true);
		assertEquals(true,tbSiaUrl.getBolLogin());
	}

	@Test
	void testGetTbSiaSelectoresCsses() {
		tbSiaUrl.setTbSiaSelectoresCsses(Lists.newArrayList());
		tbSiaUrl.getTbSiaSelectoresCsses().add(new TbSiaSelectoresCss());
		assertEquals(1,tbSiaUrl.getTbSiaSelectoresCsses().size());
	}
	
	@Test 
	void tesToString() {
		assertNotNull(tbSiaUrl.toString());
		assertNotEquals("", tbSiaUrl.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", tbSiaUrl.hashCode());
	}
	
	@Test
	void testCanEquals() {
		var isEquals = tbSiaUrl.equals(new TbSiaUrl());
		assertFalse(isEquals);
	}

}
