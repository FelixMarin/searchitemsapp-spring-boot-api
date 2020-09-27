package com.searchitemsapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.SearchedParamsDto;


@FunctionalInterface
@Service
public interface SearchItemsService {
	
	abstract List<ProductDto> orderedByPriceProducts(SearchedParamsDto listaProductosDto);
}