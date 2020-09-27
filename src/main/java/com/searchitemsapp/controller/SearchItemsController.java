package com.searchitemsapp.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.SearchedParamsDto;
import com.searchitemsapp.services.SearchItemsService;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@AllArgsConstructor
@RestController
public class SearchItemsController {
	
	private SearchItemsService searchItemsService;

	@GetMapping(value = "/search", produces={MediaType.APPLICATION_JSON_VALUE})
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public @ResponseBody ResponseEntity<List<ProductDto>> searchItems(@RequestBody 
				@RequestParam(value = "country", defaultValue = "101") String countryId,
				@RequestParam(value = "category", defaultValue = "101") String categoryId,
				@RequestParam(value = "sort", defaultValue = "1") String sort, 
				@RequestParam(value = "product") @Validated @NonNull String product, 
				@RequestParam(value = "pipedenterprises") @Validated @NonNull String pipedEnterprises) {
			
		return ResponseEntity.ok(searchItemsService.orderedByPriceProducts(
				SearchedParamsDto.builder()
					.countryId(countryId)
					.categoryId(categoryId)
					.sort(sort)
					.product(product)
					.pipedEnterprises(pipedEnterprises)
					.build()));
	}
}
