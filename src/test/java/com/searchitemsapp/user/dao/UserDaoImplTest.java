package com.searchitemsapp.user.dao;

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

import com.searchitemsapp.exception.ConfilctFoundException;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.user.dto.UserDto;

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
	void testSave() throws ConfilctFoundException {
		assertNotNull(userDao.save(UserDto.builder()
				.id(999l)
				.username("test6")
				.password("Test6")
				.build()));
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
		assertThrows(ResourceNotFoundException.class, () -> {
			userDao.findByUsernameAndPassword("user", "User1");
		});
	}

}
