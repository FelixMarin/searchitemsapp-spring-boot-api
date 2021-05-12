package com.searchitemsapp.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class OnlineControllerTest {

	@Autowired
	private MockMvc mvc;

	private String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
	
	@Test
	@WithMockUser(username = "user10", password = "0000", roles = "USER")
	void testIndex() throws Exception {
		
		var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
		
		var result = mvc.perform(MockMvcRequestBuilders.get("/main")
				.sessionAttr(TOKEN_ATTR_NAME, csrfToken)
				.param(csrfToken.getParameterName(), csrfToken.getToken()))
				.andExpect(status().isOk()).andReturn();

		var resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);

		result = mvc.perform(MockMvcRequestBuilders.get("/login")
				.sessionAttr(TOKEN_ATTR_NAME, csrfToken)
				.param(csrfToken.getParameterName(), csrfToken.getToken()))
				.andExpect(status().isOk()).andReturn();

		resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);

	}
	
	@Test
	@WithMockUser(username = "admin", password = "Admin1", roles = "ADMIN")
	void testBoard() throws Exception {
		
		var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
		
		var result = mvc.perform(MockMvcRequestBuilders.get("/board")
				.sessionAttr(TOKEN_ATTR_NAME, csrfToken)
				.param(csrfToken.getParameterName(), csrfToken.getToken()))
				.andExpect(status().isOk()).andReturn();

		var resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
}
