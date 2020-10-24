package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.CompanyDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

public interface CompanyDao {
	
	abstract Optional<CompanyDto> findByDid(CompanyDto nomProducto) throws IOException, ResourceNotFoundException;
	abstract List<CompanyDto> findByTbSia(CompanyDto r, CategoryDto t) throws IOException;
	abstract List<CompanyDto> findAll() throws IOException;
}
