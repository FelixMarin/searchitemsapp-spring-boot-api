package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jettison.json.JSONException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.Brands;
import com.searchitemsapp.business.Documents;
import com.searchitemsapp.business.Patterns;
import com.searchitemsapp.business.ProductBuilder;
import com.searchitemsapp.business.Products;
import com.searchitemsapp.business.SelectorsCss;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.SearchItemsParamsDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Setter
@NoArgsConstructor
@Scope("prototype")
public class ProductsBuilderImpl implements ProductBuilder {
	
	private UrlDto urlDto; 
	private SearchItemsParamsDto productsInParametersDto;
	private Brands brands;
	private Documents documents;
	private Patterns patterns;
	private Products products;
	private SelectorsCss cssSelectors;

	private  List<ProductDto> createProductsList() 
			throws IOException, URISyntaxException, InterruptedException, JSONException {
		
		List<ProductDto> productListAsResult = Lists.newArrayList();
				
    	List<Document> documentList = documents.getHtmlDocument(urlDto, productsInParametersDto.getProduct());
    	
    	documentList.stream()
    		.filter(ObjectUtils::allNotNull)
	    	.forEach(document -> {
	    		
	            Elements documentElements = patterns.selectScrapPattern(document,
	            		urlDto.getSelectores().getScrapPattern(), 
	            		urlDto.getSelectores().getScrapNoPattern()); 
	            
	            documentElements.stream()
		            .filter(element -> cssSelectors.validateSelector(element).isEmpty())
		            .forEach(element -> {
		            	
		            	try { 
		            		
		            		ProductDto productDto = products.addElementsToProducts(element, urlDto, productsInParametersDto.getSort());
		            		
		            		Optional<ProductDto> opt = products
		            				.checkProduct(productsInParametersDto.getProduct(), 
			    					urlDto.getDidEmpresa(), productDto, patterns, brands);
		            		
		            		opt.ifPresent(productListAsResult::add); 
		    			
		            	}catch(IOException e) {  
		            		throw new UncheckedIOException(e);
		            	}
		           });
	    	});  
        
        return productListAsResult;
	}

	@Override
	public List<ProductDto> call() throws IOException, URISyntaxException, InterruptedException, JSONException {
		return createProductsList();
	}
}
