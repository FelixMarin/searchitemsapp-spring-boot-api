package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.searchitemsapp.dto.BrandsDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

public interface BrandsDao {
	
	abstract Optional<BrandsDto> findByDid(BrandsDto nomProducto) throws IOException, ResourceNotFoundException;
	abstract List<BrandsDto> findAll() throws IOException;
}
