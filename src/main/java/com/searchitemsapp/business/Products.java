package com.searchitemsapp.business;

import java.io.IOException;
import java.util.Optional;

import org.jsoup.nodes.Element;

import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.NonNull;

public interface Products {
	
	abstract Optional<ProductDto> checkProduct(String requestProducName, Long companyId, 
			ProductDto productDto, Patterns elementPatterns) throws IOException;
	
	abstract String removeTildes(final String productName);
	
	abstract String manageProductName(final String productName) throws IOException;
	
	abstract ProductDto addElementsToProducts(@NonNull Element documentElement,
			@NonNull UrlDto urlDto, 
			@NonNull String sort) throws IOException;
}
