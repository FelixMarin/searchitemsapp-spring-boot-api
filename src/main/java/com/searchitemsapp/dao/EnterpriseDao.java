package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.EnterpriseDto;

public interface EnterpriseDao {
	
	abstract EnterpriseDto findByDid(EnterpriseDto nomProducto) throws IOException;
	abstract List<EnterpriseDto> findByTbSia(EnterpriseDto r, CategoryDto t) throws IOException;
	abstract List<EnterpriseDto> findAll() throws IOException;
}
