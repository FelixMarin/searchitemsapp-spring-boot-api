package com.searchitemsapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.SearchItemsParamsDto;
import com.searchitemsapp.service.SearchItemsService;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor=Exception.class)
@AllArgsConstructor
@RestController
@Validated
public class SearchItemsController {
	
	private static final String SEARCH = "/search";
	private SearchItemsService searchItemsService;

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = SEARCH, produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<List<ProductDto>> searchItems(@RequestBody 
				@RequestParam(value = "country", defaultValue = "101") String countryId,
				@RequestParam(value = "category", defaultValue = "101") String categoryId,
				@RequestParam(value = "sort", defaultValue = "1") String sort, 
				@RequestParam(value = "product") @Validated @NonNull String product, 
				@RequestParam(value = "pipedcompanies") @Validated @NonNull String pipedCompanies) {
		
		SearchItemsParamsDto parameters = SearchItemsParamsDto.builder()
				.countryId(countryId)
				.categoryId(categoryId)
				.sort(sort)
				.product(product)
				.pipedEnterprises(pipedCompanies)
				.build();
		
		return ResponseEntity
		        .status(HttpStatus.OK)
		        .body(searchItemsService.orderedByPriceProducts(parameters));
	}
}
