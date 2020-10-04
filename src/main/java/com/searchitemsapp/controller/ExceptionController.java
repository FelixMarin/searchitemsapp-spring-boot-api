package com.searchitemsapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ExceptionController.EXCEPTION)
public class ExceptionController {
	
	public static final String EXCEPTION = "/exception";	
	public static final String OUT_OF_TIME = "/registrationfilter";
	public static final String REGISTRATION = "/timeAccess";
	
	@GetMapping(value = OUT_OF_TIME)
	public String outOfTime() {
		return "{\"state\":\"off\"}";
	}

	@GetMapping(value = REGISTRATION)
	public String registatrion() {
		return "{\"state\":\"registration\"}";
	}
}
