package com.searchitemsapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.searchitemsapp.dto.LiveSearchProductNameDto;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.services.ApplicationService;
import com.searchitemsapp.services.LiveSearchService;

import lombok.AllArgsConstructor;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@AllArgsConstructor
@RestController
public class ApplicationController {
	
	private ApplicationService applicationService;
	private LiveSearchService liveSearchService;

	@GetMapping(value = "/search", produces={MediaType.APPLICATION_JSON_VALUE})
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public @ResponseBody ResponseEntity<List<ProductDto>> listaProductos(@RequestBody 
				@RequestParam(value = "pais", defaultValue = "101") String countryId,
				@RequestParam(value = "categoria", defaultValue = "101") String categoryId,
				@RequestParam(value = "ordenacion", defaultValue = "1") String sort, 
				@RequestParam(value = "producto") @Validated String product, 
				@RequestParam(value = "empresas") @Validated String pipedEnterprises) {
			
		Map<String,String> requestParams = Maps.newHashMap();
		
		requestParams.put("COUNTRY_ID", countryId);
		requestParams.put("CATEGORY_ID", categoryId);
		requestParams.put("SORT", sort);
		requestParams.put("PRODUCT_NAME", product);
		requestParams.put("ENTERPRISES", pipedEnterprises);
		
		return ResponseEntity.ok(applicationService.orderedByPriceProdutsService(requestParams));
	}
	
	@RequestMapping(value = "/product/{producto}", method = RequestMethod.GET)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<List<LiveSearchProductNameDto>> getProducto(@PathVariable("producto") final String prod) {
		return ResponseEntity.ok(liveSearchService.buscarProducto(prod));
	}
}
