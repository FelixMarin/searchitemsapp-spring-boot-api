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
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
class TbSiaUserTest {
	
	private static TbSiaUser tbSiaUser;
	
	@BeforeAll
	static void inicializar() {
		tbSiaUser = new TbSiaUser();
	}

	@Test
	void testGetId() {
		tbSiaUser.setId(101l);
		assertEquals(101l,tbSiaUser.getId());
	}

	@Test
	void testGetUsername() {
		tbSiaUser.setUsername("test");
		assertEquals("test",tbSiaUser.getUsername());
	}

	@Test
	void testGetPassword() {
		tbSiaUser.setPassword("test");
		assertEquals("test",tbSiaUser.getPassword());
	}

	@Test
	void testGetEmail() {
		tbSiaUser.setEmail("test");
		assertEquals("test",tbSiaUser.getEmail());
	}

	@Test
	void testGetEnabled() {
		tbSiaUser.setEnabled(true);
		assertEquals(true,tbSiaUser.getEnabled());
	}

	@Test
	void testGetAccountNonExpired() {
		tbSiaUser.setAccountNonExpired(true);
		assertEquals(true,tbSiaUser.getAccountNonExpired());
	}

	@Test
	void testGetAccountNonLocked() {
		tbSiaUser.setAccountNonLocked(true);
		assertEquals(true,tbSiaUser.getAccountNonLocked());
	}

	@Test
	void testGetCreateAt() {
		var time = LocalDateTime.now();
		tbSiaUser.setCreateAt(time);
		assertEquals(time,tbSiaUser.getCreateAt());
	}

	@Test
	void testGetRoles() {
		List<TbSiaRoles> roles = Lists.newArrayList();
		tbSiaUser.setRoles(roles);
		assertEquals(0, tbSiaUser.getRoles().size());
	}
	
	@Test 
	void tesToString() {
		assertNotNull(tbSiaUser.toString());
		assertNotEquals("", tbSiaUser.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", tbSiaUser.hashCode());
	}
	
	@Test
	void testCanEquals() {
		assertTrue(tbSiaUser.canEqual(new TbSiaUser()));
		var isEquals = tbSiaUser.equals(new TbSiaUser());
		assertFalse(isEquals);
	}

}
