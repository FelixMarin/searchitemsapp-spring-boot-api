package com.searchitemsapp.business;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.UrlDto;

public interface UrlManager {

	abstract List<UrlDto> replaceUrlWildcard(Map<String,String> requestParams,
			final List<CssSelectorsDto> listAllCssSelector) 
			throws IOException;

	
}
