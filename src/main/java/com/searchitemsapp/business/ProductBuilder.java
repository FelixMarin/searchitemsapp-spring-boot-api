package com.searchitemsapp.business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Callable;

import org.codehaus.jettison.json.JSONException;

import com.searchitemsapp.business.webdriver.WebDriverManager;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.SearchItemsParamsDto;
import com.searchitemsapp.dto.UrlDto;

public interface ProductBuilder extends Callable<List<ProductDto>> {

	abstract List<ProductDto> call() throws IOException, URISyntaxException, InterruptedException, JSONException;
	abstract void setProductsInParametersDto(SearchItemsParamsDto productsInParametersDto);
	abstract void setUrlDto(UrlDto urlDto);
	abstract void setDocuments(Documents documents);
	abstract void setPatterns(Patterns patterns);
	abstract void setProducts(Products products);
	abstract void setCssSelectors(SelectorsCss selectorCss);
	abstract void setWebDriverManager(WebDriverManager webDriverManager);
}
