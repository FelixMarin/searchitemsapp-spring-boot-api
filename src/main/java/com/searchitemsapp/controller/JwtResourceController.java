package com.searchitemsapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.searchitemsapp.service.JwtService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(JwtResourceController.JWST)
@AllArgsConstructor 
public class JwtResourceController {

	public static final String JWST = "/jwst";
	
	public static final String TOKEN = "/token";
	
	private JwtService jwsService;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PreAuthorize("authenticated")
	@PostMapping(value = TOKEN)
	public ResponseEntity<String> login(@AuthenticationPrincipal User activeUser) {
		List<String> roleList = activeUser.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
				
		return ResponseEntity.ok(jwsService.crearToken(activeUser.getUsername(), roleList));
	}
	
	@GetMapping
	public String verify() {
		return "OK, permitido JWT";
	}
	
}
