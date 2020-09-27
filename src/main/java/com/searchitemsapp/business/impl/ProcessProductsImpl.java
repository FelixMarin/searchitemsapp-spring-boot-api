package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.codehaus.jettison.json.JSONException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.Brands;
import com.searchitemsapp.business.Documents;
import com.searchitemsapp.business.Patterns;
import com.searchitemsapp.business.ProcessProducts;
import com.searchitemsapp.business.Products;
import com.searchitemsapp.business.SelectorsCss;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.SearchedParamsDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@Scope("prototype")
public class ProcessProductsImpl implements ProcessProducts {
	
	private UrlDto urlDto; 
	private SearchedParamsDto productsInParametersDto;
	private Brands brands;
	private Environment environment;
	private Documents documentManager;
	private Patterns patternsManager;
	private Products productManager;
	private SelectorsCss selectorCssManager;

	private  List<ProductDto> generateProductList() 
			throws IOException, URISyntaxException, InterruptedException, JSONException {
		
		List<ProductDto> productListAsResult = Lists.newArrayList();
				
    	List<Document> documentList = documentManager.getHtmlDocument(urlDto, productsInParametersDto.getProduct());
    	
    	documentList.stream()
	    	.filter(document -> Objects.nonNull(document))
	    	.forEach(document -> {
	    		
	            Elements documentElements = patternsManager.selectScrapPattern(document,
	            		urlDto.getSelectores().getScrapPattern(), 
	            		urlDto.getSelectores().getScrapNoPattern());
	            
	            documentElements.stream()
		            .filter(element -> !selectorCssManager.validateSelector(element))
		            .forEach(element -> {
		            	
		            	try {
		            		
		            		ProductDto productDto = productManager.addElementsToProducts(element, urlDto, productsInParametersDto.getSort());
			    			
			    			if(productManager.checkProduct(productsInParametersDto.getProduct(), 
			    					urlDto.getDidEmpresa(), productDto, patternsManager, brands)) {
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
		return generateProductList();
	}
}
