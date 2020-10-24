package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.searchitemsapp.dto.CompanyDto;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

public interface CssSelectorsDao {

	abstract Optional<CssSelectorsDto> findByDid(CssSelectorsDto nomProducto)
			throws IOException, ResourceNotFoundException;

	abstract List<CssSelectorsDto> findByTbSia(CompanyDto empresaDto) 
			throws IOException;

	abstract List<CssSelectorsDto> findAll() throws IOException;
}
