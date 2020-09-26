package com.searchitemsapp.business;

import java.net.MalformedURLException;
import java.util.List;

import org.jsoup.nodes.Element;

import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.NonNull;

public interface SelectorCssManager {
	
	abstract List<CssSelectorsDto> selectorCssListByEnterprise(final String strDidEmpresas);
	abstract boolean validaSelector(Element elem);
	abstract CssSelectorsDto cargaSelectoresCss(UrlDto urlDTO, List<CssSelectorsDto> listTodosElementNodes);
	abstract String elementoPorCssSelector(@NonNull Element documentElement, 
			@NonNull String cssSelector,
			@NonNull UrlDto urlDto) throws MalformedURLException;
	
}
