package com.searchitemsapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.searchitemsapp.dto.LiveSearchDto;
import com.searchitemsapp.service.LiveSearchService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class LiveSearchController {
	
	private LiveSearchService liveSearchService;

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/product/{producto}")
	public ResponseEntity<List<LiveSearchDto>> liveSearchProduct(@PathVariable("producto") final String prod) {
		return ResponseEntity.ok(liveSearchService.buscarProducto(prod));
	}
}
