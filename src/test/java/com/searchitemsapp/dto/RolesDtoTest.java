package com.searchitemsapp.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.assertj.core.util.Lists;
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
class RolesDtoTest {
	
	private static RolesDto roles;

	@BeforeAll
	static void testBuilder() {
		roles = RolesDto.builder().build();
	}

	@Test
	void testId() {
		roles.setId(101l);
		assertEquals(101l,roles.getId());
	}

	@Test
	void testRoleName() {
		roles.setRoleName("test");
		assertEquals("test",roles.getRoleName());
	}

	@Test
	void testCreateAt() {
		var time = LocalDateTime.now();
		roles.setCreateAt(time);
		assertEquals(time,roles.getCreateAt());
	}

	@Test
	void testUsers() {
		roles.setUsers(Lists.newArrayList());
		assertEquals(0,roles.getUsers().size());
	}

}
