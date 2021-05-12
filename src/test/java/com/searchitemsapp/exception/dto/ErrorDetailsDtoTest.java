package com.searchitemsapp.exception.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		LocalDateTime time = LocalDateTime.now();
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

}
