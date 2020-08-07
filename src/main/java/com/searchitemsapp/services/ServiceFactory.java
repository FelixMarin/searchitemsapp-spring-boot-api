package com.searchitemsapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

/**
 * Clase Factory encargada de gestionar la creación de 
 * objetos de tipo service. Las peticiones a los services 
 * pasarán siempre por esta clase.
 *
 * @author Felix Marin Ramirez
 */
@Component
@NoArgsConstructor
public class ServiceFactory implements IFServiceFctory {
	
	@Autowired
	private ApplicationService listadoProductosService;

	public IFService<String,String> getService(final String nomService) {

		IFService<String,String> ifs = null;

		if(nomService.equals("LISTA_PRODUCTOS")) {
			ifs = listadoProductosService;
		}
		
		return ifs;
	}

}
