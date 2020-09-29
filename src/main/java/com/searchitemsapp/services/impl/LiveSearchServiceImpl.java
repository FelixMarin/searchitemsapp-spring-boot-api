package com.searchitemsapp.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.searchitemsapp.dao.ProductNameDao;
import com.searchitemsapp.dto.LiveSearchDto;
import com.searchitemsapp.services.LiveSearchService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LiveSearchServiceImpl implements LiveSearchService {
	
	private ProductNameDao productNameDao;

	@Override
	public List<LiveSearchDto> buscarProducto(String product) {
		return productNameDao.findByNomProducto(product);
	}
}
