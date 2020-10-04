package com.searchitemsapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(OnlineController.MAIN)
public class OnlineController {
	
	public static final String MAIN = "/main";
	
	@GetMapping
	public ModelAndView  index() {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("main");
	    return modelAndView;
	}
}
