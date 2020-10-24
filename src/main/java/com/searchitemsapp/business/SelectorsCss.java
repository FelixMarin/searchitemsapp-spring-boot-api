package com.searchitemsapp.business;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import org.jsoup.nodes.Element;

import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.NonNull;

public interface SelectorsCss {
	
	abstract List<CssSelectorsDto> selectorCssListByEnterprise(final String pipedCompaniesIds);
	abstract Optional<Element> validateSelector(Element element);
	abstract CssSelectorsDto addCssSelectors(UrlDto urlDTO, List<CssSelectorsDto> allCssSelectors);
	abstract String elementByCssSelector(@NonNull Element element, 
			@NonNull String cssSelector, @NonNull UrlDto urlDto) 
					throws MalformedURLException;
	
}
