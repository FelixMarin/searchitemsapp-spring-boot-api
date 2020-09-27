package com.searchitemsapp.business;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.SearchedParamsDto;
import com.searchitemsapp.dto.UrlDto;

public interface Urls {

	abstract List<UrlDto> replaceUrlWildcard(SearchedParamsDto listaProductosDto,
			final List<CssSelectorsDto> listAllCssSelector) 
			throws IOException;

	
}
