package com.searchitemsapp.user.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.security.Principal;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UserDto;
import com.searchitemsapp.entities.TbSiaRoles;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
@WithMockUser(username = "manager", password = "Manager1", roles = "MANAGER")
class UserControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
    @Autowired
    private MockMvc mvc;
    	
    @Mock
    private Principal principal;
    
    private final String username = "test63";
    private final String  password = "Test63";

    @Test
	@BeforeClass
	void testSaveUser() throws Exception {
		
		List<TbSiaRoles> roles = Lists.newArrayList();
		var entity = new TbSiaRoles();
		entity.setId(2l);
		entity.setRoleName("USER");
		roles.add(entity);
		var user = UserDto.builder().id(986l)
				.username(username)
				.password(password)
				.roles(roles).build();

		var mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    var ow = mapper.writer().withDefaultPrettyPrinter();
	    var requestJson=ow.writeValueAsString(user);
	     
	    var result = mvc.perform(MockMvcRequestBuilders.post("/user/save")
				 .contentType(APPLICATION_JSON_UTF8)
				 .content(requestJson))
				.andExpect(status().isOk())
				.andReturn();
		 
		var resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
	
	@Test
	void testFindAll() throws Exception {
		var result = mvc.perform(MockMvcRequestBuilders.get("/user/all")
				 .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		 
		var resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
	
	@Test
	void testExistsUser() throws Exception {
		
		var user = mock(Principal.class);
		given(user.getName()).willReturn("user");
		var result = mvc.perform(MockMvcRequestBuilders.get("/user/exists").principal(user))
				.andExpect(status().isOk()) 
				.andReturn(); 
		 
		var resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
	
	@Test
	void mailExists() throws Exception {
		var result = mvc.perform(MockMvcRequestBuilders.get("/user/mailExists"))
					.andExpect(status().isOk()) 
					.andReturn(); 
			 
		var resultado = result.getResponse().getContentAsString();
			assertNotNull(resultado);
	}

	@Test
	
	void testUpdateUser() throws Exception {
		
		List<TbSiaRoles> roles = Lists.newArrayList();
		var entity = new TbSiaRoles();
		entity.setId(3l);
		entity.setRoleName("MANAGER");
		roles.add(entity);
		var user = UserDto.builder().id(986l).email("mail@mail2.com")
				.username(username).password(password).roles(roles).build();

		var mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    var ow = mapper.writer().withDefaultPrettyPrinter();
	    var requestJson=ow.writeValueAsString(user);

	    var result = mvc.perform(MockMvcRequestBuilders.put("/user/update")
				 .contentType(APPLICATION_JSON_UTF8)
				 .content(requestJson))
				.andExpect(status().isOk())
				.andReturn();
		 
	    var resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
		
	}
	
	@Test
	@AfterClass
	void testDeleteUser() throws Exception {
		List<TbSiaRoles> roles = Lists.newArrayList();
		var entity = new TbSiaRoles();
		entity.setId(2l);
		entity.setRoleName("USER");
		roles.add(entity);
		var user = UserDto.builder().id(986l)
				.username(username).password(password).roles(roles).build();

		var mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    var ow = mapper.writer().withDefaultPrettyPrinter();
	    var requestJson=ow.writeValueAsString(user);

	    var result = mvc.perform(MockMvcRequestBuilders.delete("/user/delete")
				 .contentType(APPLICATION_JSON_UTF8)
				 .content(requestJson))
				.andExpect(status().isOk())
				.andReturn();
		 
	    var resultado = result.getResponse().getContentAsString();
		assertNotNull(resultado);
	}
}