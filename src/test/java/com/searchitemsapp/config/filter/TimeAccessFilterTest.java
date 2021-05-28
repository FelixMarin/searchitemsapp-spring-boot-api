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
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TimeAccessFilterTest {

	@Test
	void testDoFilterInternalHttpServletRequestHttpServletResponseFilterChain() throws ServletException, IOException {
		var  timeAccessFilter = new  TimeAccessFilter();

		var request = mock(HttpServletRequest.class);
		var response = mock(HttpServletResponse.class);
		var filterChain = mock(FilterChain.class);
		when(response.getStatus()).thenReturn(200);
		timeAccessFilter.doFilter(request, response, filterChain);
		
		assertEquals(200,response.getStatus());
	}

}
