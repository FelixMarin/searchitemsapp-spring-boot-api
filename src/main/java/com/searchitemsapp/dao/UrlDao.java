package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.CountryDto;
import com.searchitemsapp.dto.UrlDto;

public interface UrlDao {

	abstract UrlDto findByDid(final UrlDto urlDTO) throws IOException;
	abstract List<UrlDto> findByDidAndDesUrl(final Integer didPais, final String didCategoria) throws IOException;
	abstract List<UrlDto> findByDidAndNomUrl(final Integer didPais, final String didCategoria) throws IOException;
	abstract List<UrlDto> obtenerUrlsPorIdEmpresa(final CountryDto paisDto, final CategoryDto categoriaDto, final String idsEmpresas)	throws IOException;
}