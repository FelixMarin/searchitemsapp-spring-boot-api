package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.CountryDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

public interface UrlDao {

	abstract Optional<UrlDto> findByDid(final UrlDto urlDTO) throws IOException, ResourceNotFoundException;
	abstract List<UrlDto> obtenerUrlsPorIdEmpresa(final CountryDto paisDto, final CategoryDto categoriaDto, final String idsEmpresas)	throws IOException;
}