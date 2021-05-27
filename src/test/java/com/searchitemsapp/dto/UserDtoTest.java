package com.searchitemsapp.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
		assertFalse(isEquals);
	}

}
