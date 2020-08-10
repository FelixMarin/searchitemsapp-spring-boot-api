package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.EmpresaDTO;

public interface EmpresaDao {
	
	abstract EmpresaDTO findByDid(EmpresaDTO nomProducto) throws IOException;
	abstract List<EmpresaDTO> findByTbSia(EmpresaDTO r, CategoriaDTO t) throws IOException;
	abstract List<EmpresaDTO> findAll() throws IOException;
}
