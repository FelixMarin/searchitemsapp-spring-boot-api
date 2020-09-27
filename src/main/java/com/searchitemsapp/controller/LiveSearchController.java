package com.searchitemsapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.searchitemsapp.dto.LiveSearchDto;
import com.searchitemsapp.services.LiveSearchService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class LiveSearchController {
	
	private LiveSearchService liveSearchService;

	@RequestMapping(value = "/product/{producto}", method = RequestMethod.GET)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<List<LiveSearchDto>> liveSearchProduct(@PathVariable("producto") final String prod) {
		return ResponseEntity.ok(liveSearchService.buscarProducto(prod));
	}
}
