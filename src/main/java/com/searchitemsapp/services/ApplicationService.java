package com.searchitemsapp.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.searchitemsapp.dto.ProductDto;


@FunctionalInterface
@Service
public interface ApplicationService {
	
	abstract List<ProductDto> orderedByPriceProdutsService(Map<String,String> requestParams);
}