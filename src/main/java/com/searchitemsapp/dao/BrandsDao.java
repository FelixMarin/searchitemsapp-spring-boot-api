package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.BrandsDto;

public interface BrandsDao {
	
	abstract BrandsDto findByDid(BrandsDto nomProducto) throws IOException;
	abstract List<BrandsDto> findAll() throws IOException;
}
