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
import com.searchitemsapp.processdata.IFProcessPrice;
import com.searchitemsapp.processdata.IFUrlComposer;
import com.searchitemsapp.processdata.ProcessDataModule;

/**
 * @author Felix Marin Ramirez
 * 
 * Servicio que contiene la lógica para obtener listados 
 * de productos ordenados de los distintos supermercados.
 * 
 */
@Service("applicationService")
public class ApplicationService implements IFService<String,String> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationService.class);  
	
	private static final String ERROR_RESULT = "[{\"request\": \"Error\", " 
			+ "\"id\" : \"-1\", "
			+ "\"description\": \"No hay resultados\"}]";
	
	@Autowired
	private IFUrlComposer urlComposer;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private IFProcessPrice ifProcessPrice;
	
	public ApplicationService() {
		super();
	}
	
	
	/**
	 * Método principal de servicio web.  Este método contiene 
	 * toda la lógica de negocio del servicio. {@link ProcessDataModule}
	 */
	public String service(final String... params) {

		org.apache.log4j.BasicConfigurator.configure();
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		Map<String,EmpresaDTO> mapEmpresas = Maps.newHashMap();		
		List<MarcasDTO> listTodasMarcas = Lists.newArrayList();		
		Map<Integer,Boolean> mapDynEmpresas = Maps.newHashMap();
	
		urlComposer.applicationData(mapEmpresas, listTodasMarcas, mapDynEmpresas);
		
		String didPais = params[0];
		String didCategoria = params[1];
		String ordenacion = params[2];
		String producto = params[3];
		String empresas = params[4];
		
		StringBuilder sbParams = new StringBuilder(1);
		sbParams.append("Pais: ").append(didPais);
		sbParams.append(" Categoria: ").append(didCategoria);
		sbParams.append(" Ordenacio: ").append(didPais);
		sbParams.append(" Producto: ").append(producto);
		sbParams.append(" Empresas: ").append(empresas);

		List<IFProcessPrice> listResultDtoFinal = Lists.newArrayList();
		int contador = 0;
	
		ExecutorService executorService = Executors.newCachedThreadPool();

		try {
						
			List<SelectoresCssDTO> lselectores = urlComposer.listSelectoresCssPorEmpresa(empresas, didPais, didCategoria);
			Collection<UrlDTO> lResultDtoUrlsTratado = urlComposer.replaceWildcardCharacter(didPais, 
					didCategoria, producto, empresas, lselectores, mapEmpresas);

			Collection<ProcessDataModule> colPDMcallables = Lists.newArrayList();
		
			lResultDtoUrlsTratado.forEach(elem -> {
				ProcessDataModule processDataModule = applicationContext
						.getBean(ProcessDataModule.class, elem, producto, ordenacion, 
								mapEmpresas, listTodasMarcas, mapDynEmpresas);
	
				colPDMcallables.add(processDataModule);	
			});
	
			List<Future<List<IFProcessPrice>>> listFutureListResDto = executorService.invokeAll(colPDMcallables);
			listResultDtoFinal = executeFuture(listFutureListResDto);

            if(listResultDtoFinal.isEmpty()) {
            	
    			if(LOGGER.isErrorEnabled()) {
    				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString());
    				LOGGER.error("Sin resultados para: ".concat(sbParams.toString()));
    			}
            	
    			return ERROR_RESULT;
            }

            listResultDtoFinal = ifProcessPrice.ordenarLista(listResultDtoFinal);

         	for (int i = 0; i < listResultDtoFinal.size(); i++) {
				listResultDtoFinal.get(i).setIdentificador(++contador);
			}
			
		}catch(IOException | InterruptedException | ExecutionException e) {
			
  			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
	
			Thread.currentThread().interrupt();	
			
		} finally {
	
			executorService.shutdown();
		}

		return new Gson().toJson(listResultDtoFinal);
	}
	
	/**
	 * La clase Future representa un resultado futuro de un cálculo 
	 * asincrónico, un resultado que finalmente aparecerá en el Futuro 
	 * después de que se complete el procesamiento.
	 * 
	 * @param resultList Una lista de listas de resultados.
	 * @return List<IFProcessPrice>
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private List<IFProcessPrice> executeFuture(final List<Future<List<IFProcessPrice>>> resultList) 
			throws InterruptedException, ExecutionException {
		
		List<IFProcessPrice> listResultFinal = Lists.newArrayList();

		for(Future<List<IFProcessPrice>> future : resultList) {
			
			try {
				if(Objects.isNull(future.get())) {
					continue;
				}
				
				listResultFinal.addAll(future.get(5, TimeUnit.SECONDS));
				
				if(LOGGER.isInfoEnabled()) {
					LOGGER.info(future.get().toString());
					LOGGER.info(String.valueOf(future.isDone()));
				}
				
			}catch(TimeoutException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
				}
			}
		}
		
		return listResultFinal;
	}
}