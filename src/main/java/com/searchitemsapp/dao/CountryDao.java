package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.CountryDto;

public interface CountryDao {
	
	abstract CountryDto findByDid(CountryDto nomProducto) throws IOException;
	abstract List<CountryDto> findAll() throws IOException;
}
