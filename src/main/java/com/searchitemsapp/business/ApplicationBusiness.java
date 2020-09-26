package com.searchitemsapp.business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.codehaus.jettison.json.JSONException;
import org.springframework.core.env.Environment;

import com.searchitemsapp.dto.BrandsDto;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;

public interface ApplicationBusiness extends Callable<List<ProductDto>> {

	abstract List<ProductDto> call() throws IOException, URISyntaxException, InterruptedException, JSONException;
	abstract void setListAllBrands(List<BrandsDto> listAllBrands);
	abstract void setRequestParams(Map<String, String> requestParams);
	abstract void setUrlDto(UrlDto urlDto);
	abstract void setEnvironment(Environment environment);
	abstract void setDocumentManager(DocumentManager documentManager);
	abstract void setPatternsManager(PatternsManager patternsManager);
	abstract void setProductManager(ProductManager productManager);
	abstract void setSelectorCssManager(SelectorCssManager selectorCssManager);
}
