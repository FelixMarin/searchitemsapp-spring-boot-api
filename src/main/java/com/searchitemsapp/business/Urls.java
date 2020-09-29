package com.searchitemsapp.business;

import java.io.IOException;
import java.util.List;

import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.SearchItemsParamsDto;
import com.searchitemsapp.dto.UrlDto;

public interface Urls {

	abstract List<UrlDto> replaceUrlWildcard(SearchItemsParamsDto searchedParams,
			final List<CssSelectorsDto> listAllCssSelector) throws IOException;

	
}
