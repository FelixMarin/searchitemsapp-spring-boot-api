package com.searchitemsapp.user.service.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UserDto;
import com.searchitemsapp.entities.TbSiaRoles;
import com.searchitemsapp.exception.ConfilctFoundException;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.service.UserService;

@Transactional
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@WithMockUser(username = "manager", password = "Manager1", roles = "MANAGER")
class UserServiceImplTest {
	
	@Autowired
	private UserService service;
	
    private static final String username = "test60";
    private static final String  password = "Test60";
    private static final String email = "test60@mail.com";
    private static final Long id = 996l;
    
	private static final List<TbSiaRoles> roles = Lists.newArrayList();
	private static final TbSiaRoles entity = new TbSiaRoles();
	
	@BeforeAll()
	static void setUpEntity() {
		entity.setId(id);
		entity.setRoleName("USER");
		roles.add(entity);
	}
	
	UserDto user = UserDto.builder().id(id)
			.username(username)
			.password(password)
			.email(email)
			.roles(roles).build();

	@Test
	@BeforeClass
	void testSave() throws ConfilctFoundException {		
		assertTrue(service.save(user));
	}

	@Test
	@AfterClass
	void testDelete() throws ResourceNotFoundException, ConfilctFoundException {
		service.save(user);
		service.delete(user);
		
		assertNotNull(service.existsByUserName(username));
		
	}

	@Test
	void testUpdate() throws ConfilctFoundException, ResourceNotFoundException {
		assertTrue(service.update(user));
	}

	@Test
	void testExistsByUserName() throws ResourceNotFoundException {
		assertNotNull(service.existsByUserName("user"));
	}

	@Test
	void testFindAll() {
		assertNotNull(service.findAll());
	}
	
	@Test
	void testMailExists() throws ResourceNotFoundException {
		assertTrue(service.mailExists("user@mail.com"));
		assertFalse(service.mailExists(""));
	}

}
