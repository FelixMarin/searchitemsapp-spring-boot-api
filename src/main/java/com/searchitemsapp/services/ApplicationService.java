package com.searchitemsapp.services;

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
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.processdata.IFApplicationData;
import com.searchitemsapp.processdata.IFProcessPrice;
import com.searchitemsapp.processdata.IFUrlComposer;
import com.searchitemsapp.processdata.ProcessDataModule;

@Service("applicationService")
public class ApplicationService implements IFService<String,String> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationService.class);  
	
	private static final String ERROR_RESULT = "[{\"request\": \"Error\", " 
			+ "\"id\" : \"-1\", "
			+ "\"description\": \"No hay resultados\"}]";
	
	@Autowired
	private IFApplicationData iFApplicationData;
	
	@Autowired
	private IFUrlComposer urlComposer;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private IFProcessPrice ifProcessPrice;
	
	public ApplicationService() {
		super();
	}
	
	public String service(final String... params) {

		org.apache.log4j.BasicConfigurator.configure();
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		Map<String,EmpresaDTO> mapEmpresas = Maps.newHashMap();		
		Map<Integer,Boolean> mapIsEmpresasDyn = Maps.newHashMap();
	
		String strDidPais = params[0];
		String strDidCategoria = params[1];
		String strTipoOrdenacion = params[2];
		String strNomProducto = params[3];
		String strEmpresas = params[4];
		
		List<IFProcessPrice> listIfProcessPrice = Lists.newArrayList();
		int contador = 0;
	
		ExecutorService executorService = Executors.newCachedThreadPool();

		try {
			iFApplicationData.applicationData(mapEmpresas, mapIsEmpresasDyn);
			List<MarcasDTO> listTodasMarcas = iFApplicationData.getListTodasMarcas();
			
			List<SelectoresCssDTO> listTodosSelectoresCss = iFApplicationData
					.listSelectoresCssPorEmpresa(strEmpresas);
			
			Collection<UrlDTO> lResultDtoUrlsTratado = urlComposer.replaceWildcardCharacter(strDidPais, 
					strDidCategoria, strNomProducto, strEmpresas, listTodosSelectoresCss, mapEmpresas);

			Collection<ProcessDataModule> colPDMcallables = Lists.newArrayList();
		
			lResultDtoUrlsTratado.forEach(elem -> {
				ProcessDataModule processDataModule = applicationContext.getBean(ProcessDataModule.class);
				
				processDataModule.setListTodasMarcas(listTodasMarcas);
				processDataModule.setMapDynEmpresas(mapIsEmpresasDyn);
				processDataModule.setMapEmpresas(mapEmpresas);
				processDataModule.setOrdenacion(strTipoOrdenacion);
				processDataModule.setProducto(strNomProducto);
				processDataModule.setUrlDto(elem);
	
				colPDMcallables.add(processDataModule);	
			});
	
			List<Future<List<IFProcessPrice>>> listFutureListResDto = executorService.invokeAll(colPDMcallables);
			listIfProcessPrice = executeFuture(listFutureListResDto);

            if(listIfProcessPrice.isEmpty()) {            	            	
    			return ERROR_RESULT;
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

	private List<IFProcessPrice> executeFuture(final List<Future<List<IFProcessPrice>>> listfutureListIfProcessPrice) 
			throws InterruptedException, ExecutionException {
		
		List<IFProcessPrice> listIfProcessPrice = Lists.newArrayList();

		for(Future<List<IFProcessPrice>> futureListIfProcessPrice : listfutureListIfProcessPrice) {
			
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