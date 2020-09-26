package com.searchitemsapp.services;

import java.util.List;

import com.searchitemsapp.dto.LiveSearchProductNameDto;

public interface LiveSearchService {

	public abstract List<LiveSearchProductNameDto> buscarProducto(String producto); 
}
