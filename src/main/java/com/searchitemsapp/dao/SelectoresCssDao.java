package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;

public interface SelectoresCssDao {
	
	abstract SelectoresCssDTO findByDid(SelectoresCssDTO nomProducto) throws IOException;
	abstract List<SelectoresCssDTO> findByTbSia(SelectoresCssDTO r, EmpresaDTO t) throws IOException;
	abstract List<SelectoresCssDTO> findAll() throws IOException;
}
