package com.searchitemsapp.dto;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
class UserDtoTest {

	private static UserDto userDto;
	
	@BeforeAll
	static void inicializar() {
		userDto = new UserDto();
	}

	@Test 
	void tesToString() {
		assertNotNull(userDto.toString());
		assertNotEquals("", userDto.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", userDto.hashCode());
	}
	
	@Test
	void testCanEquals() {
		assertTrue(userDto.canEqual(UserDto.builder().build()));
		var isEquals = userDto.equals(UserDto.builder().build());
		assertTrue(isEquals);
	}

}
