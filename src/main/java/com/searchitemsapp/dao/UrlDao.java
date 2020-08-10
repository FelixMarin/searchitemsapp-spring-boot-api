package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.dto.UrlDTO;

public interface UrlDao {

	abstract UrlDTO findByDid(final UrlDTO urlDTO) throws IOException;
	abstract List<UrlDTO> findByDidAndDesUrl(final Integer didPais, final String didCategoria) throws IOException;
	abstract List<UrlDTO> findByDidAndNomUrl(final Integer didPais, final String didCategoria) throws IOException;
	abstract List<UrlDTO> obtenerUrlsPorIdEmpresa(final PaisDTO paisDto, final CategoriaDTO categoriaDto, final String idsEmpresas)	throws IOException;
}