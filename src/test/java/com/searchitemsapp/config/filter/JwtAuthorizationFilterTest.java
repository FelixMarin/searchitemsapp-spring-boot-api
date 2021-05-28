package com.searchitemsapp.config.filter;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class JwtAuthorizationFilterTest {

	@Test
	void testDoFilterInternal() throws ServletException, IOException {
		var authenticationManager = mock(AuthenticationManager.class);
		var jwtAuthorizationFilter = new JwtAuthorizationFilter(authenticationManager);
		assertNotNull(jwtAuthorizationFilter);		
	}

}
