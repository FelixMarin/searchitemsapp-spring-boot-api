package com.searchitemsapp.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

class TbSiaRolesTest {
	
	private static TbSiaRoles tbSiaRoles;
	
	@BeforeAll
	static void inicializar() {
		tbSiaRoles = new TbSiaRoles();
	}

	@Test
	void testGetId() {
		tbSiaRoles.setId(101l);
		assertEquals(101l,tbSiaRoles.getId());
	}

	@Test
	void testGetRoleName() {
		tbSiaRoles.setRoleName("test");
		assertEquals("test",tbSiaRoles.getRoleName());
	}

	@Test
	void testGetCreateAt() {
		var time = LocalDateTime.now();
		tbSiaRoles.setCreateAt(time);
		assertEquals(time,tbSiaRoles.getCreateAt());
	}

	@Test
	void testGetUsers() {
		List<TbSiaUser> list = Lists.newArrayList();
		list.add(new TbSiaUser());
		tbSiaRoles.setUsers(list);
		assertEquals(1,tbSiaRoles.getUsers().size());
	}
	
	@Test 
	void tesToString() {
		assertNotNull(tbSiaRoles.toString());
		assertNotEquals("", tbSiaRoles.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", tbSiaRoles.hashCode());
	}
	
	@Test
	void testCanEquals() {
		assertTrue(tbSiaRoles.canEqual(new TbSiaRoles()));
		var isEquals = tbSiaRoles.equals(new TbSiaRoles());
		assertFalse(isEquals);
	}

}
