package com.searchitemsapp.services.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.processdata.ApplicationData;
import com.searchitemsapp.processdata.ProcessDataModule;
import com.searchitemsapp.processdata.ProcessPrice;
import com.searchitemsapp.processdata.UrlComposer;
import com.searchitemsapp.services.ApplicationService;

import lombok.NoArgsConstructor;

@Service("applicationService")
@NoArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);  
	
	@Autowired
	private ApplicationData iFApplicationData;
	
	@Autowired
	private UrlComposer urlComposer;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private ProcessPrice ifProcessPrice;
	
	public String service(final String strDidPais, final String strDidCategoria,
			final String strTipoOrdenacion, final String strNomProducto, final String strEmpresas) {

		org.apache.log4j.BasicConfigurator.configure();
		
		Map<Integer,Boolean> mapIsEmpresasDyn = Maps.newHashMap();
		
		List<ProcessPrice> listIfProcessPrice = Lists.newArrayList();
		int contador = 0;
	
		ExecutorService executorService = Executors.newCachedThreadPool();

		try {
			iFApplicationData.applicationData(mapIsEmpresasDyn);
			List<MarcasDTO> listTodasMarcas = iFApplicationData.getListTodasMarcas();
			
			List<SelectoresCssDTO> listTodosSelectoresCss = iFApplicationData
					.listSelectoresCssPorEmpresa(strEmpresas);
			
			Collection<UrlDTO> lResultDtoUrlsTratado = urlComposer.replaceWildcardCharacter(strDidPais, 
					strDidCategoria, strNomProducto, strEmpresas, listTodosSelectoresCss);

			Collection<ProcessDataModule> colPDMcallables = Lists.newArrayList();
		
			lResultDtoUrlsTratado.forEach(elem -> {
				ProcessDataModule processDataModule = applicationContext.getBean(ProcessDataModule.class);
				
				processDataModule.setListTodasMarcas(listTodasMarcas);
				processDataModule.setMapDynEmpresas(mapIsEmpresasDyn);
				processDataModule.setOrdenacion(strTipoOrdenacion);
				processDataModule.setProducto(strNomProducto);
				processDataModule.setUrlDto(elem);
	
				colPDMcallables.add(processDataModule);	
			});
	
			List<Future<List<ProcessPrice>>> listFutureListResDto = executorService.invokeAll(colPDMcallables);
			listIfProcessPrice = executeFuture(listFutureListResDto);

            if(listIfProcessPrice.isEmpty()) {            	            	
    			return "[{\"request\": \"Error\", " 
    					+ "\"id\" : \"-1\", "
    					+ "\"description\": \"No hay resultados\"}]";
            }

            listIfProcessPrice = ifProcessPrice.ordenarLista(listIfProcessPrice);

         	for (int i = 0; i < listIfProcessPrice.size(); i++) {
				listIfProcessPrice.get(i).setIdentificador(++contador);
			}
			
		}catch(IOException | InterruptedException | ExecutionException e) {
			
  			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
	
			Thread.currentThread().interrupt();	
			
		} finally {
	
			executorService.shutdown();
		}

		return new Gson().toJson(listIfProcessPrice);
	}

	private List<ProcessPrice> executeFuture(final List<Future<List<ProcessPrice>>> listfutureListIfProcessPrice) 
			throws InterruptedException, ExecutionException {
		
		List<ProcessPrice> listIfProcessPrice = Lists.newArrayList();

		for(Future<List<ProcessPrice>> futureListIfProcessPrice : listfutureListIfProcessPrice) {
			
			try {
				if(Objects.isNull(futureListIfProcessPrice.get())) {
					continue;
				}
				
				listIfProcessPrice.addAll(futureListIfProcessPrice.get(5, TimeUnit.SECONDS));
				
			}catch(TimeoutException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
				}
			}
		}
		
		return listIfProcessPrice;
	}
}