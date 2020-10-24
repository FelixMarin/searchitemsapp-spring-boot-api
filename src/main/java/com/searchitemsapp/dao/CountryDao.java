package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.searchitemsapp.dto.CountryDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

public interface CountryDao {
	
	abstract Optional<CountryDto> findByDid(CountryDto nomProducto) throws IOException, ResourceNotFoundException;
	abstract List<CountryDto> findAll() throws IOException;
}
