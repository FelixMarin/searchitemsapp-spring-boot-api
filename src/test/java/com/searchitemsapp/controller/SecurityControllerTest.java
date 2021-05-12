package com.searchitemsapp.controller;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class SecurityControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	@WithMockUser(username = "user", password = "User1", roles = "USER")
	void user() throws Exception {
		
		var user = mock(Principal.class);
		given(user.getName()).willReturn("user");
		var result = mvc.perform(MockMvcRequestBuilders.get("/security/user").principal(user))
				.andExpect(status().isOk()).andReturn();
		
		var resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
	
	@Test
	@WithMockUser(username = "manager", password = "Manager1", roles = "MANAGER")
	void manager() throws Exception {
		
		var result = mvc.perform(MockMvcRequestBuilders.get("/security/manager"))
				.andExpect(status().isOk()).andReturn();
		
		var resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
	
	@Test
	@WithMockUser(username = "admin", password = "Admin1", roles = "ADMIN")
	void admin() throws Exception {
		
		var result = mvc.perform(MockMvcRequestBuilders.get("/security/admin"))
				.andExpect(status().isOk()).andReturn();
		
		var resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
		
		result = mvc.perform(MockMvcRequestBuilders.get("/security/timeAccess"))
				.andExpect(status().isOk()).andReturn();
		
		resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
		
		result = mvc.perform(MockMvcRequestBuilders.get("/security/registrationfilter"))
				.andExpect(status().isOk()).andReturn();
		
		resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
}
