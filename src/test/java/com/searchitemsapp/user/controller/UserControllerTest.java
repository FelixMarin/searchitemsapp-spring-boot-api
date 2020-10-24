package com.searchitemsapp.user.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.searchitemsapp.entities.TbSiaRoles;
import com.searchitemsapp.user.dto.UserDto;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
    @Autowired
    private MockMvc mvc;
    
    private final String username = "test5";
    private final String  password = "Test5";
    private final String basic = username + ":" + password;

	@Test
	@BeforeEach
	void testSaveUser() throws Exception {
		
		List<TbSiaRoles> roles = Lists.newArrayList();
		TbSiaRoles entity = new TbSiaRoles();
		entity.setId(2l);
		entity.setRoleName("USER");
		roles.add(entity);
		UserDto user = UserDto.builder().id(986l)
				.username(username)
				.password(password)
				.roles(roles).build();

	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(user);
	     
	    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/user/save")
				 .contentType(APPLICATION_JSON_UTF8)
				 .content(requestJson))
				.andExpect(status().isOk())
				.andReturn();
		 
		String resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
	
	@Test
	@WithMockUser(username = "manager", password = "Manager1", roles = "MANAGER")
	void testFindAll() throws Exception {
		 MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/user/all")
				 .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		 
		String resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
	
	@Test
	void testExistsUser() throws Exception {
		 MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/user/exists")
				 .accept(MediaType.APPLICATION_JSON)
				 .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(basic.getBytes())))
				.andExpect(status().isOk())
				.andReturn();
		 
		String resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}

	@Test
	@WithMockUser(username = "manager", password = "Manager1", roles = "MANAGER")
	void testUpdateUser() throws Exception {
		
		List<TbSiaRoles> roles = Lists.newArrayList();
		TbSiaRoles entity = new TbSiaRoles();
		entity.setId(2l);
		entity.setRoleName("USER");
		roles.add(entity);
		UserDto user = UserDto.builder().id(986l).email("mail@mail2.com")
				.username(username).password(password).roles(roles).build();

	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(user);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/user/update")
				 .contentType(APPLICATION_JSON_UTF8)
				 .content(requestJson))
				.andExpect(status().isOk())
				.andReturn();
		 
		String resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
		
	}
	
	@Test
	@AfterEach
	@WithMockUser(username = "manager", password = "Manager1", roles = "MANAGER")
	void testDeleteUser() throws Exception {
		List<TbSiaRoles> roles = Lists.newArrayList();
		TbSiaRoles entity = new TbSiaRoles();
		entity.setId(2l);
		entity.setRoleName("USER");
		roles.add(entity);
		UserDto user = UserDto.builder().id(986l)
				.username(username).password(password).roles(roles).build();

	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(user);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/user/delete")
				 .contentType(APPLICATION_JSON_UTF8)
				 .content(requestJson))
				.andExpect(status().isOk())
				.andReturn();
		 
		String resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
}