package com.searchitemsapp.config.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class RegistrationFilterTest {
	

	@Test
	void testDoFilterInternal() throws ServletException, IOException {
		var registrationFilter = new RegistrationFilter();

		var request = mock(HttpServletRequest.class);
		var response = mock(HttpServletResponse.class);
		var filterChain = mock(FilterChain.class);
		when(response.getStatus()).thenReturn(200);
		registrationFilter.doFilter(request, response, filterChain);
		
		assertEquals(200,response.getStatus());
	}

}
