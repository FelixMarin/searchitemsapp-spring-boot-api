package com.searchitemsapp.service.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.Brands;
import com.searchitemsapp.business.Documents;
import com.searchitemsapp.business.Patterns;
import com.searchitemsapp.business.Prices;
import com.searchitemsapp.business.ProductBuilder;
import com.searchitemsapp.business.Products;
import com.searchitemsapp.business.SelectorsCss;
import com.searchitemsapp.business.Urls;
import com.searchitemsapp.business.webdriver.WebDriverManager;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.SearchItemsParamsDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.service.SearchItemsService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("searchItemsService")
@AllArgsConstructor
@Slf4j
public class SearchItemsServiceImpl implements SearchItemsService {
	
	private ApplicationContext applicationContext;
	private Urls urls;
	private Prices prices;
	private Documents documents;
	private Brands brands;
	private Patterns patterns;
	private Products products;
	private SelectorsCss selectorCss;
	private WebDriverManager webDriverManager;
	
	public List<ProductDto> orderedByPriceProducts(SearchItemsParamsDto searchedParamsDto) {

		ExecutorService executorService = Executors.newCachedThreadPool();
		List<ProductDto> productsResult = Lists.newArrayList();

		try {
			List<CssSelectorsDto> listAllCssSelectors = selectorCss
					.selectorCssListByEnterprise(searchedParamsDto.getPipedEnterprises());
			
			Collection<UrlDto> urlDtoCollection = urls
					.replaceUrlWildcard(searchedParamsDto, listAllCssSelectors);

			Collection<ProductBuilder> callables = Lists.newArrayList();
		
			urlDtoCollection.forEach(urlDto -> {
				
				ProductBuilder productCore = applicationContext.getBean(ProductBuilder.class);
				
				productCore.setUrlDto(urlDto);
				productCore.setProductsInParametersDto(searchedParamsDto);
				productCore.setBrands(brands);
				productCore.setDocuments(documents);
				productCore.setPatterns(patterns);
				productCore.setProducts(products);
				productCore.setCssSelectors(selectorCss);
				productCore.setWebDriverManager(webDriverManager);
				callables.add(productCore);	
			});
	
			List<Future<List<ProductDto>>> productFutures = executorService.invokeAll(callables);
			List<ProductDto> productList = Lists.newArrayList();
			
			productFutures.forEach(future -> {
					try {
						productList.addAll(future.get(5, TimeUnit.SECONDS));
					} catch (InterruptedException | ExecutionException | TimeoutException e) {
						log.error(e.getMessage(),e);
						Thread.currentThread().interrupt();	
					}
				});

			productsResult = prices.sortProductsByPrice(productList);
         	
		}catch(IOException | InterruptedException e) {	
			
			log.error(e.getLocalizedMessage(),e);
			Thread.currentThread().interrupt();				
			
		} finally {			
			
			if(webDriverManager.isOpen()) {
				webDriverManager.shutdownWebDriver();
			}
			executorService.shutdown();
		}

		return productsResult;
	}
}