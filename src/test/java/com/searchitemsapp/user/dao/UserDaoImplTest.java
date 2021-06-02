package com.searchitemsapp.user.dao;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.dao.UserDao;
import com.searchitemsapp.exception.ResourceNotFoundException;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserDaoImplTest {
	
	@Autowired
	private UserDao userDao;
	
	@Test
	void testFindByUserName() throws ResourceNotFoundException {
		assertNotNull(userDao.findByUserName("user"));
	}

	@Test
	void testExistById() {
		assertNotNull(userDao.existById(1l));
	}

	@Test
	void testDelete() {
		assertThrows(EmptyResultDataAccessException.class, () -> {
			userDao.delete(999l);
		});
	}

	@Test
	void testFindAll() {
		assertNotNull(userDao.findAll());
	}

	@Test
	void testFindByUsernameAndPassword() throws ResourceNotFoundException {
		var user = userDao.findByUsernameAndPassword("user", "7qrSWwJyUdMNVTiQlabhiOrPs3HBX4dZYET1WIxBNRPS0SV4utG5G");
		assertNotNull(user.getUsername());
		user = userDao.findByUsernameAndPassword("test", "test");
		assertNull(user.getUsername());
	}

}
