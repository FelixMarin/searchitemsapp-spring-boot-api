package com.searchitemsapp.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test") 
@AutoConfigureMockMvc
@EntityScan("com.searchitemsapp.entities")
@EnableJpaRepositories("com.searchitemsapp.dao.repository")
@PropertySource("classpath:db.properties")
@PropertySource("classpath:flow.properties")
@PropertySource("classpath:text.properties")
@PropertySource("classpath:log4j.properties")
@EnableTransactionManagement
@WithMockUser(username = "user", password = "User1", roles = "USER")
class SearchItemsControllerTest {
			
    @Autowired
    private MockMvc mvc;
    
	@Test
	void statusCodeOkTest() throws Exception {
				 
		 MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/search")
				 .accept(MediaType.APPLICATION_JSON)
				 .param("country", "101")
				 .param("category", "101")
				 .param("sort", "1")
				 .param("product", "miel")
				 .param("pipedcompanies", "101"))
				.andExpect(status().isOk())
				.andReturn();
		 
		String resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
}
