package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.MarcasDTO;

public interface MarcasDao {
	
	abstract MarcasDTO findByDid(MarcasDTO nomProducto) throws IOException;
	abstract List<MarcasDTO> findAll() throws IOException;
}
