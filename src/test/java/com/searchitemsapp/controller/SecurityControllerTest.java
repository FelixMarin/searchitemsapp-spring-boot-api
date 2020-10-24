package com.searchitemsapp.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SecurityControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	private final String[] paths = {
			"/security/user",
			"/security/manager",
			"/security/admin",
			"/security/timeAccess",
			"/security/registrationfilter"
	};
	
	@Test
	@WithMockUser(username = "admin", password = "Admin1", roles = "ADMIN")
	void testRead() throws Exception {
		
		for (int i = 0; i < paths.length; i++) {
			MvcResult result = mvc.perform(MockMvcRequestBuilders.get(paths[i]))
					.andExpect(status().isOk()).andReturn();
			
			String resultado = result.getResponse().getContentAsString();
			assertNotNull(resultado);
		}
	}
}
