package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.codehaus.jettison.json.JSONException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.ApplicationBusiness;
import com.searchitemsapp.business.DocumentManager;
import com.searchitemsapp.business.PatternsManager;
import com.searchitemsapp.business.ProductManager;
import com.searchitemsapp.business.SelectorCssManager;
import com.searchitemsapp.dto.BrandsDto;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@Scope("prototype")
public class ApplicationBusinessImpl implements ApplicationBusiness {
	
	private static final String PRODUCT_NAME = "PRODUCT_NAME";
	
	private UrlDto urlDto; 
	private Map<String,String> requestParams;
	private List<BrandsDto> listAllBrands;
	private Environment environment;
	private DocumentManager documentManager;
	private PatternsManager patternsManager;
	private ProductManager productManager;
	private SelectorCssManager selectorCssManager;

	public  List<ProductDto> checkHtmlDocument() 
			throws IOException, URISyntaxException, InterruptedException, JSONException {
		
		List<ProductDto> productListAsResult = Lists.newArrayList();
				
    	List<Document> documentList = documentManager.getHtmlDocument(urlDto, requestParams.get(PRODUCT_NAME));
    	
    	documentList.stream()
	    	.filter(document -> Objects.nonNull(document))
	    	.forEach(document -> {
	    		
	            Elements documentElements = patternsManager.selectScrapPattern(document,
	            		urlDto.getSelectores().getScrapPattern(), 
	            		urlDto.getSelectores().getScrapNoPattern());
	            
	            documentElements.stream()
		            .filter(element -> !selectorCssManager.validaSelector(element))
		            .forEach(element -> {
		            	
		            	try {
		            		
		            		ProductDto productDto = productManager.addElementsToProducts(element, urlDto, requestParams.get("SORT"));
			    			
			    			if(productManager.checkProduct(requestParams.get(PRODUCT_NAME), 
			    					urlDto.getDidEmpresa(), productDto, patternsManager, listAllBrands)) {
			    				productListAsResult.add(productDto);
			    			}
		    			
		            	}catch(IOException e) {
		            		throw new UncheckedIOException(e);
		            	}
		            });
	    	});  
        
        return productListAsResult;
	}

	@Override
	public List<ProductDto> call() throws IOException, URISyntaxException, InterruptedException, JSONException {
		return checkHtmlDocument();
	}
}
