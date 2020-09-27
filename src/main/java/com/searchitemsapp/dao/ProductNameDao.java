package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.LiveSearchDto;

public interface ProductNameDao {
	
	abstract LiveSearchDto findByDid(LiveSearchDto nomProducto) throws IOException;
	abstract List<LiveSearchDto> findByNomProducto(String product);
	abstract List<LiveSearchDto> findAll() throws IOException;
}
