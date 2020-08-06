package com.searchitemsapp.processdata;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.dto.UrlDTO;

public interface IFUrlComposer {

	abstract List<UrlDTO> replaceWildcardCharacter(final String strDidPais, 
			final String strDidCategoria, 
			final String strNomProducto,
			final String strEmpresas,
			final List<SelectoresCssDTO> listTodosSelectoresCss,
			final Map<String,EmpresaDTO> mapEmpresas) 
			throws IOException;

	
}
