package com.searchitemsapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller 
@EnableWebMvc
@RequestMapping
public class OnlineController {
	
	public static final String MAIN = "/main";
	public static final String BOARD = "/board";
	public static final String LOGIN = "/login";
	public static final String TERMS = "/terminos";
	
	@GetMapping(value = MAIN, produces = "application/html")
	public @ResponseBody ModelAndView  index() {
		var modelAndView = new ModelAndView();
	    modelAndView.setViewName("main");
	    return modelAndView;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = BOARD, produces = "application/html")
	public @ResponseBody ModelAndView  dashBoard() {
		var modelAndView = new ModelAndView();
	    modelAndView.setViewName("board");
	    return modelAndView;
	}
	
	@GetMapping(value = LOGIN, produces = "application/html")
	public @ResponseBody ModelAndView  loginPage() {
		var modelAndView = new ModelAndView();
	    modelAndView.setViewName("login");
	    return modelAndView;
	}
	
	@GetMapping(value = TERMS, produces = "application/html")
	public @ResponseBody ModelAndView  terminosPage() {
		var modelAndView = new ModelAndView();
	    modelAndView.setViewName("terminos");
	    return modelAndView;
	}
}
