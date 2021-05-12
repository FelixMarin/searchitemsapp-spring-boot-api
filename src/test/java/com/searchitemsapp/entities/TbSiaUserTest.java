package com.searchitemsapp.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		LocalDateTime time = LocalDateTime.now();
		tbSiaUser.setCreateAt(time);
		assertEquals(time,tbSiaUser.getCreateAt());
	}

	@Test
	void testGetRoles() {
		List<TbSiaRoles> roles = Lists.newArrayList();
		tbSiaUser.setRoles(roles);
		assertEquals(0, tbSiaUser.getRoles().size());
	}

}
