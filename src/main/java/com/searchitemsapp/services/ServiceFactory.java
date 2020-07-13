package com.searchitemsapp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase Factory encargada de gestionar la creación de 
 * objetos de tipo service. Las peticiones a los services 
 * pasarán siempre por esta clase.
 *
 * @author Felix Marin Ramirez
 */
@Component
public class ServiceFactory implements IFServiceFctory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceFactory.class);  
	private static final String LISTA_PRODUCTOS = "LISTA_PRODUCTOS";

	@Autowired
	private ApplicationService listadoProductosService;
	
	public ServiceFactory() {
		super();
	}
	
	/**
	 * Método de la clase factory que gestiona la creación 
	 * de instancias de servicios.
	 * 
	 * @param String
	 * @return IFService<String,String>
	 */
	public IFService<String,String> getService(final String nomService) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		IFService<String,String> ifs = null;

		if(nomService.equals(LISTA_PRODUCTOS)) {
			ifs = listadoProductosService;
		}
		
		return ifs;
	}

}
