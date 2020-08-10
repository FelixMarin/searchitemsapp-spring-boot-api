package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.PaisDTO;

public interface PaisDao {
	
	abstract PaisDTO findByDid(PaisDTO nomProducto) throws IOException;
	abstract List<PaisDTO> findAll() throws IOException;
}
