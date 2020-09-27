package com.searchitemsapp.business;

import java.io.IOException;

import org.jsoup.nodes.Element;

import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.NonNull;

public interface Products {
	
	abstract boolean checkProduct(String requestProducName, int enterpriseId, 
			ProductDto productDto, Patterns elementPatterns, 
			Brands brands) throws IOException;
	
	abstract String removeTildes(final String cadena);
	
	abstract String manageProductName(final String producto) throws IOException;
	
	abstract ProductDto addElementsToProducts(@NonNull Element elem,
			@NonNull UrlDto urlDto, 
			@NonNull String ordenacion) throws IOException;
}
