package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.CompanyDto;

public interface CompanyDao {
	
	abstract CompanyDto findByDid(CompanyDto nomProducto) throws IOException;
	abstract List<CompanyDto> findByTbSia(CompanyDto r, CategoryDto t) throws IOException;
	abstract List<CompanyDto> findAll() throws IOException;
}
