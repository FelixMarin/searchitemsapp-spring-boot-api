package com.searchitemsapp.business;

import java.io.IOException;

import org.jsoup.nodes.Element;

import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.NonNull;

public interface Products {
	
	abstract boolean checkProduct(String requestProducName, int companyId, 
			ProductDto productDto, Patterns elementPatterns, 
			Brands brands) throws IOException;
	
	abstract String removeTildes(final String productName);
	
	abstract String manageProductName(final String productName) throws IOException;
	
	abstract ProductDto addElementsToProducts(@NonNull Element documentElement,
			@NonNull UrlDto urlDto, 
			@NonNull String sort) throws IOException;
}
