package com.searchitemsapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.SearchedParamsDto;


@FunctionalInterface
@Service
public interface ApplicationService {
	
	abstract List<ProductDto> orderedByPriceProduts(SearchedParamsDto listaProductosDto);
}