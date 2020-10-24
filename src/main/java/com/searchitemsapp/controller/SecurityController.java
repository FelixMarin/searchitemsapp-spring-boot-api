package com.searchitemsapp.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("authenticated")
@RestController
@RequestMapping(SecurityController.SECURITY)
public class SecurityController {
	
	public static final String SECURITY = "/security";
	public static final String USER = "/user";
	public static final String MANAGER = "/manager";
	public static final String ADMIN = "/admin";
	
	public static final String  REGISTRATION = "/registrationfilter";
	public static final String OUT_OF_TIME = "/timeAccess";
		
	@PreAuthorize("hasRole('USER') OR hasRole('MANAGER')")
	@GetMapping(value = USER)
	public String readUser(Principal principal) {
		return "Usuario: " + principal.getName();
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping(value = MANAGER)
	public String readManager() {
		return "OK, Acceso permitido al recurso manager";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = ADMIN)
	public String readAdmin() {
		return "OK, Acceso permitido al recurso ADMIN";
	}
	
	@GetMapping(value = OUT_OF_TIME)
	public String outOfTime() {
		return "{\"state\":\"off\"}";
	}

	@GetMapping(value = REGISTRATION)
	public String registatrion() {
		return "{\"state\":\"registration\"}";
	}
	
}
