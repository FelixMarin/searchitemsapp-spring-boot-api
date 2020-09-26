package com.searchitemsapp.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.searchitemsapp.dao.ProductNameDao;
import com.searchitemsapp.dto.LiveSearchProductNameDto;
import com.searchitemsapp.services.LiveSearchService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LiveSearchServiceImpl implements LiveSearchService {
	
	private ProductNameDao ifNomProductoRepository;

	@Override
	public List<LiveSearchProductNameDto> buscarProducto(String product) {
		return ifNomProductoRepository.findByNomProducto(product);
	}
}
