package com.searchitemsapp.user.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.searchitemsapp.entities.TbSiaRoles;
import com.searchitemsapp.exception.ConfilctFoundException;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.user.dto.UserDto;
import com.searchitemsapp.user.service.UserService;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@WithMockUser(username = "manager", password = "Manager1", roles = "MANAGER")
class UserServiceImplTest {
	
	@Autowired
	private UserService service;
	
    private final String username = "test6";
    private final String  password = "Test6";
    private final Long id = 996l;
    
	List<TbSiaRoles> roles = Lists.newArrayList();
	TbSiaRoles entity = new TbSiaRoles();
	UserDto user = UserDto.builder().id(id)
			.username(username)
			.password(password)
			.roles(roles).build();

	@Test
	@BeforeEach
	void testSave() throws ConfilctFoundException {
		entity.setId(id);
		entity.setRoleName("USER");
		roles.add(entity);
		assertTrue(service.save(user));
	}

	@Test
	@AfterEach
	void testDelete() throws ResourceNotFoundException, ConfilctFoundException {
		entity.setId(id);
		entity.setRoleName("USER");
		roles.add(entity);
		service.delete(user);
		assertNull(service.existsByUserName(username));
	}

	@Test
	void testUpdate() throws ConfilctFoundException {
		entity.setId(id);
		entity.setRoleName("USER");
		roles.add(entity);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.update(user);
		});
		
	}

	@Test
	void testExistsByUserName() throws ResourceNotFoundException {
		assertNotNull(service.existsByUserName("user"));
	}

	@Test
	void testFindAll() {
		assertNotNull(service.findAll());
	}

}
