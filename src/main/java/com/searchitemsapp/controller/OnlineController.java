package com.searchitemsapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping
public class OnlineController {
	
	public static final String MAIN = "/main";
	public static final String BOARD = "/board";
	public static final String LOGIN = "/login";
	
	@GetMapping(value = MAIN)
	public ModelAndView  index() {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("main");
	    return modelAndView;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = BOARD)
	public ModelAndView  dashBoard() {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("board");
	    return modelAndView;
	}
	
	@GetMapping(value = LOGIN)
	public ModelAndView  loginPage() {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("login");
	    return modelAndView;
	}
}
