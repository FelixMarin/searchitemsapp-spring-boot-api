package com.searchitemsapp.service;

import java.util.List;

import com.searchitemsapp.dto.LiveSearchDto;

public interface LiveSearchService {

	abstract List<LiveSearchDto> buscarProducto(String producto); 
}
