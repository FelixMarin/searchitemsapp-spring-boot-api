package com.searchitemsapp.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class JwtResourceControllerTest {
	
    @Autowired
    private MockMvc mvc;

	@Test
	@WithMockUser(username = "user", password = "User1", roles = "USER")
	void testToken() throws Exception {
		var result = mvc.perform(MockMvcRequestBuilders.post("/jwst/token")
				 .accept(MediaType.APPLICATION_JSON)
				 .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("user:User1".getBytes())))
				.andExpect(status().isOk())
				.andReturn();
		 
		var resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
	
	@Test
	@WithMockUser(username = "user", password = "User1", roles = "USER")
	void testJwst() throws Exception {
		
		var result = mvc.perform(MockMvcRequestBuilders.post("/jwst/token")
				 .accept(MediaType.APPLICATION_JSON)
				 .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("user:User1".getBytes())))
				.andExpect(status().isOk())
				.andReturn();
		
		result = mvc.perform(MockMvcRequestBuilders.get("/jwst")
				 .accept(MediaType.APPLICATION_JSON)
				 .header("Authorization", "Bearer " + result))
				.andExpect(status().isOk())
				.andReturn();
		 
		var resultado2 = result.getResponse().getContentAsString();
		assertNotNull(resultado2);
	}	
}
