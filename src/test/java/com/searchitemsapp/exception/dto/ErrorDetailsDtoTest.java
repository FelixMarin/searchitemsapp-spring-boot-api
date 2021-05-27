package com.searchitemsapp.exception.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

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
class ErrorDetailsDtoTest {
	
	private static ErrorDetailsDto errorDetails;

	@BeforeAll
	static void testBuilder() {
		errorDetails = ErrorDetailsDto.builder().build();
	}
	
	@Test
	void testGetTimestamp() {
		var time = LocalDateTime.now();
		errorDetails.setTimestamp(time);
		assertEquals(time,errorDetails.getTimestamp());
	}

	@Test
	void testGetMessage() {
		errorDetails.setMessage("test");
		assertEquals("test",errorDetails.getMessage());
	}

	@Test
	void testGetDetails() {
		errorDetails.setDetails("test");
		assertEquals("test",errorDetails.getDetails());
	}

	@Test
	void testGetStatus() {
		errorDetails.setStatus(200);
		assertEquals(200,errorDetails.getStatus());
	}
	
	@Test 
	void tesToString() {
		assertNotNull(errorDetails.toString());
		assertNotEquals("", errorDetails.toString());
	}
	
	@Test
	void testHashCode() {
		assertNotEquals("", errorDetails.hashCode());
	}
	
	@Test
	void testCanEquals() {
		assertTrue(errorDetails.canEqual(ErrorDetailsDto.builder().build()));
		var isEquals = errorDetails.equals(ErrorDetailsDto.builder().build());
		assertTrue(isEquals);
	}

}
