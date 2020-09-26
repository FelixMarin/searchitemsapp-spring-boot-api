package com.searchitemsapp.business;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.BrandsDto;

public interface BrandsManager {

	abstract public String filtroMarca(final int iIdEmpresa, final String nomProducto, final List<BrandsDto> listTodasMarcas);
	abstract public List<BrandsDto> allBrandList() throws IOException;
}
