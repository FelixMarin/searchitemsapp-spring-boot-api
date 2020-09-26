package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.EnterpriseDto;
import com.searchitemsapp.dto.CssSelectorsDto;

public interface CssSelectorsDao {
	
	abstract CssSelectorsDto findByDid(CssSelectorsDto nomProducto) throws IOException;
	abstract List<CssSelectorsDto> findByTbSia(CssSelectorsDto r, EnterpriseDto t) throws IOException;
	abstract List<CssSelectorsDto> findAll() throws IOException;
}
