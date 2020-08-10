package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.NomProductoDTO;

public interface NomProductoDao {
	
	abstract NomProductoDTO findByDid(NomProductoDTO nomProducto) throws IOException;
	abstract List<NomProductoDTO> findByNomProducto(String product);
	abstract List<NomProductoDTO> findAll() throws IOException;
}
