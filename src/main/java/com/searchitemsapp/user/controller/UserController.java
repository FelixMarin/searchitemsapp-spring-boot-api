package com.searchitemsapp.user.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.searchitemsapp.exception.ConfilctFoundException;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.user.dto.UserDto;
import com.searchitemsapp.user.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(UserController.USER)
public class UserController {
	
	public static final String USER = "/user";
	
	public static final String EXISTS = "/exists";
	
	public static final String SAVE = "/save";
	
	public static final String DELETE = "/delete";
	
	public static final String UPDATE = "/update";
	
	public static final String ALL = "/all";
	
	private UserService userService;
		
	@GetMapping(value = EXISTS)
	public ResponseEntity<UserDto> existsUser(@NonNull Principal user) throws ResourceNotFoundException {
		return ResponseEntity
		        .status(HttpStatus.OK)
		        .body(userService.existsByUserName(user.getName()));
	}
	
	@PostMapping(value = SAVE)
	public ResponseEntity<Integer> saveUser(@RequestBody @NonNull UserDto user) 
					throws ConfilctFoundException {
		userService.save(user);
		return ResponseEntity
		        .status(HttpStatus.OK)
		        .body(HttpStatus.OK.value());
	}
	
	@PreAuthorize("hasRole('MANAGER') OR hasRole('ADMIN')")
	@PutMapping(value = UPDATE)
	public ResponseEntity<Integer> updateUser(@RequestBody @NonNull UserDto user) throws ConfilctFoundException, ResourceNotFoundException{
		userService.update(user);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(HttpStatus.OK.value());
	}
	
	@PreAuthorize("hasRole('MANAGER') OR hasRole('ADMIN')")
	@DeleteMapping(value = DELETE)
	public ResponseEntity<Integer> deleteUser(@RequestBody @NonNull UserDto user) throws ResourceNotFoundException {
		userService.delete(user);
		return ResponseEntity
		        .status(HttpStatus.OK)
		        .body(HttpStatus.OK.value());
	}
	
	@PreAuthorize("hasRole('MANAGER') OR hasRole('ADMIN')")
	@GetMapping(value = ALL)
	public ResponseEntity<List<UserDto>> findAll() {
		return ResponseEntity
		        .status(HttpStatus.OK)
		        .body(userService.findAll());
	}
}
