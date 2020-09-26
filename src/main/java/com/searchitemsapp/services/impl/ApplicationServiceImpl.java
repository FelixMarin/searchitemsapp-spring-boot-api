package com.searchitemsapp.services.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
import com.searchitemsapp.business.ApplicationBusiness;
import com.searchitemsapp.business.BrandsManager;
import com.searchitemsapp.business.DocumentManager;
import com.searchitemsapp.business.PatternsManager;
import com.searchitemsapp.business.PriceManager;
import com.searchitemsapp.business.ProductManager;
import com.searchitemsapp.business.SelectorCssManager;
import com.searchitemsapp.business.UrlManager;
import com.searchitemsapp.dto.BrandsDto;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.services.ApplicationService;

import lombok.AllArgsConstructor;

@Service("applicationService")
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);  
	
	private ApplicationContext applicationContext;
	private UrlManager urlManager;
	private PriceManager priceManager;
	private Environment environment;
	private DocumentManager documentManager;
	private BrandsManager brandsManager;
	private PatternsManager patternsManager;
	private ProductManager productManager;
	private SelectorCssManager selectorCssManager;
	
	public List<ProductDto> orderedByPriceProdutsService(Map<String,String> requestParams) {

		org.apache.log4j.BasicConfigurator.configure();
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<ProductDto> productList = Lists.newArrayList();

		try {
			List<BrandsDto> listAllBrands = brandsManager.allBrandList();
			
			List<CssSelectorsDto> listAllCssSelectors = selectorCssManager
					.selectorCssListByEnterprise(requestParams.get("ENTERPRISES"));
			
			Collection<UrlDto> collectionOfUrlDto = urlManager
					.replaceUrlWildcard(requestParams, listAllCssSelectors);

			Collection<ApplicationBusiness> colPDMcallables = Lists.newArrayList();
		
			collectionOfUrlDto.forEach(urlDto -> {
				
				ApplicationBusiness processDataModule = applicationContext.getBean(ApplicationBusiness.class);
				
				processDataModule.setUrlDto(urlDto);
				processDataModule.setRequestParams(requestParams);
				processDataModule.setListAllBrands(listAllBrands);
				processDataModule.setEnvironment(environment);
				processDataModule.setDocumentManager(documentManager);
				processDataModule.setPatternsManager(patternsManager);
				processDataModule.setProductManager(productManager);
				processDataModule.setSelectorCssManager(selectorCssManager);
	
				colPDMcallables.add(processDataModule);	
			});
	
			List<Future<List<ProductDto>>> listFutureListResDto = executorService.invokeAll(colPDMcallables);
			List<ProductDto> listIfProcessPrice = Lists.newArrayList();
			
			listFutureListResDto.forEach(elem -> {
					try {
						listIfProcessPrice.addAll(elem.get(5, TimeUnit.SECONDS));
					} catch (InterruptedException | ExecutionException | TimeoutException e) {
						LOGGER.error(Thread.currentThread().getStackTrace()[1].toString() + ":" + e.getMessage(),e);
					}
				});

			productList = priceManager.ordenarLista(listIfProcessPrice);

         	for (int i = 0; i < productList.size(); i++) {
				productList.get(i).setIdentificador(i+1);
			}
         	
		}catch(IOException | InterruptedException e) {	
			
			LOGGER.error(Thread.currentThread().getStackTrace()[1].toString() + ":" + e.getLocalizedMessage(),e);
			Thread.currentThread().interrupt();				
			
		} finally {			
				executorService.shutdown();
		}

		return productList;
	}
}