package com.searchitemsapp.user.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserDetailsServiceImplTest {
	
	@Autowired
	private UserDetailsService service;

	@Test
	void testLoadUserByUsername() {
		assertNotNull(service.loadUserByUsername("user"));
		
		assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername("");
		});
	}

}
