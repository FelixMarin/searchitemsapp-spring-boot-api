package com.searchitemsapp.services.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.Brands;
import com.searchitemsapp.business.Documents;
import com.searchitemsapp.business.Patterns;
import com.searchitemsapp.business.Prices;
import com.searchitemsapp.business.ProcessProducts;
import com.searchitemsapp.business.Products;
import com.searchitemsapp.business.SelectorsCss;
import com.searchitemsapp.business.Urls;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.SearchedParamsDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.services.ApplicationService;

import lombok.AllArgsConstructor;

@Service("applicationService")
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);  
	
	private ApplicationContext applicationContext;
	private Urls urls;
	private Prices prices;
	private Environment environment;
	private Documents documents;
	private Brands brands;
	private Patterns patterns;
	private Products products;
	private SelectorsCss selectorCss;
	
	public List<ProductDto> orderedByPriceProduts(SearchedParamsDto searchedParamsDto) {

		org.apache.log4j.BasicConfigurator.configure();
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<ProductDto> productListAsResult = Lists.newArrayList();

		try {
			List<CssSelectorsDto> listAllCssSelectors = selectorCss
					.selectorCssListByEnterprise(searchedParamsDto.getPipedEnterprises());
			
			Collection<UrlDto> urlDtoCollection = urls
					.replaceUrlWildcard(searchedParamsDto, listAllCssSelectors);

			Collection<ProcessProducts> applicationBusinessCallables = Lists.newArrayList();
		
			urlDtoCollection.forEach(urlDto -> {
				
				ProcessProducts processProducts = applicationContext.getBean(ProcessProducts.class);
				
				processProducts.setUrlDto(urlDto);
				processProducts.setProductsInParametersDto(searchedParamsDto);
				processProducts.setBrands(brands);
				processProducts.setEnvironment(environment);
				processProducts.setDocumentManager(documents);
				processProducts.setPatternsManager(patterns);
				processProducts.setProductManager(products);
				processProducts.setSelectorCssManager(selectorCss);
	
				applicationBusinessCallables.add(processProducts);	
			});
	
			List<Future<List<ProductDto>>> listFutureListResDto = executorService.invokeAll(applicationBusinessCallables);
			List<ProductDto> listIfProcessPrice = Lists.newArrayList();
			
			listFutureListResDto.forEach(elem -> {
					try {
						listIfProcessPrice.addAll(elem.get(5, TimeUnit.SECONDS));
					} catch (InterruptedException | ExecutionException | TimeoutException e) {
						LOGGER.error(Thread.currentThread().getStackTrace()[1].toString() + ":" + e.getMessage(),e);
					}
				});

			productListAsResult = prices.sortList(listIfProcessPrice);

         	for (int i = 0; i < productListAsResult.size(); i++) {
				productListAsResult.get(i).setIdentificador(i+1);
			}
         	
		}catch(IOException | InterruptedException e) {	
			
			LOGGER.error(Thread.currentThread().getStackTrace()[1].toString() + ":" + e.getLocalizedMessage(),e);
			Thread.currentThread().interrupt();				
			
		} finally {			
				executorService.shutdown();
		}

		return productListAsResult;
	}
}