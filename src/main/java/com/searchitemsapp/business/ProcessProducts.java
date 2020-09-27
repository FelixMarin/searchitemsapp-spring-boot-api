package com.searchitemsapp.business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Callable;

import org.codehaus.jettison.json.JSONException;
import org.springframework.core.env.Environment;

import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.SearchedParamsDto;
import com.searchitemsapp.dto.UrlDto;

public interface ProcessProducts extends Callable<List<ProductDto>> {

	abstract List<ProductDto> call() throws IOException, URISyntaxException, InterruptedException, JSONException;
	abstract void setBrands(Brands brands);
	abstract void setProductsInParametersDto(SearchedParamsDto productsInParametersDto);
	abstract void setUrlDto(UrlDto urlDto);
	abstract void setEnvironment(Environment environment);
	abstract void setDocumentManager(Documents documentManager);
	abstract void setPatternsManager(Patterns patternsManager);
	abstract void setProductManager(Products productManager);
	abstract void setSelectorCssManager(SelectorsCss selectorCssManager);
}
