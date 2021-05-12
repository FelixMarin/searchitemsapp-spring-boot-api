package com.searchitemsapp.exception;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.WebRequest;

import com.searchitemsapp.exception.dto.ErrorDetailsDto;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class GlobalExceptionHandlerTest {
	
	@Autowired
	private GlobalExceptionHandler globalExceptionHandler;
	
    @Mock
    WebRequest webRequest;

	@Test
	void testResourceNotFoundException() {
		assertThrows(ResourceNotFoundException.class, () -> {
			throw new ResourceNotFoundException("test");
		});
	}

	@Test
	void testConflictFoundException() {
		assertThrows(ConfilctFoundException.class, () -> {
			throw new ConfilctFoundException("test");
		});
	}
	
	@Test
	void testGlobalExceptionHandler() {
		ResponseEntity<ErrorDetailsDto> entity = globalExceptionHandler.conflictFoundException(new ConfilctFoundException("test"), webRequest);
		assertNotNull(entity);
		entity = globalExceptionHandler.resourceNotFoundException(new ResourceNotFoundException("test"), webRequest);
		assertNotNull(entity);
	}

}
