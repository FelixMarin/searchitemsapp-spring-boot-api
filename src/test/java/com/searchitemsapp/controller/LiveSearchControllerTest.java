package com.searchitemsapp.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
@WithMockUser(username = "user", password = "User1", roles = "USER")
class LiveSearchControllerTest {
	
    @Autowired
    private MockMvc mvc;

	@Test
	void testLiveSearchProduct() throws Exception {

		var result = mvc.perform(MockMvcRequestBuilders.get("/product/LE")
				 .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		 
		var resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
}
