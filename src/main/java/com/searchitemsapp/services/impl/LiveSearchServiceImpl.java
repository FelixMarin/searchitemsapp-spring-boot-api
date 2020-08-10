package com.searchitemsapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.searchitemsapp.dao.NomProductoDao;
import com.searchitemsapp.dto.NomProductoDTO;
import com.searchitemsapp.services.LiveSearchService;

@Service
public class LiveSearchServiceImpl implements LiveSearchService {
	
	@Autowired
	private NomProductoDao ifNomProductoRepository;

	@Override
	public List<NomProductoDTO> buscarProducto(String product) {
		return ifNomProductoRepository.findByNomProducto(product);
	}
}
