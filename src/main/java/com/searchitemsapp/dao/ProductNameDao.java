package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.LiveSearchProductNameDto;

public interface ProductNameDao {
	
	abstract LiveSearchProductNameDto findByDid(LiveSearchProductNameDto nomProducto) throws IOException;
	abstract List<LiveSearchProductNameDto> findByNomProducto(String product);
	abstract List<LiveSearchProductNameDto> findAll() throws IOException;
}
