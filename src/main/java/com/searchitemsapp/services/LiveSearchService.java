package com.searchitemsapp.services;

import java.util.List;

import com.searchitemsapp.dto.NomProductoDTO;

public interface LiveSearchService {

	public abstract List<NomProductoDTO> buscarProducto(String producto); 
}
