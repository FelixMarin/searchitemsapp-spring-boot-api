package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.dto.UrlDTO;

public interface IFUrlImpl {
	List<UrlDTO> obtenerUrls(PaisDTO paisDto, CategoriaDTO categoriaDto) throws IOException;
	List<UrlDTO> obtenerUrlsLogin(PaisDTO paisDto, CategoriaDTO categoriaDto) throws IOException;
	List<UrlDTO> obtenerUrlsPorIdEmpresa(PaisDTO paisDto, CategoriaDTO categoriaDto, String strIdsEmpresas) throws IOException;
}