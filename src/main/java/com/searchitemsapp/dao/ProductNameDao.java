package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.searchitemsapp.dto.LiveSearchDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

public interface ProductNameDao {
	
	abstract Optional<LiveSearchDto> findByDid(LiveSearchDto nomProducto) throws IOException, ResourceNotFoundException;
	abstract List<LiveSearchDto> findByNomProducto(String product);
	abstract List<LiveSearchDto> findAll() throws IOException;
}
