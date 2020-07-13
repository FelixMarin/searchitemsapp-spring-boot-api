package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import org.springframework.core.env.Environment;
import com.searchitemsapp.dto.UrlDTO;

/**
 * Módulo de scraping especifico diseñado para la 
 * extracción de datos del sitio web de Simply.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class ProcessDataSimply implements IFProcessDataSimply {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataSimply.class);  
	
	private static final String STRING_ENIE_MIN = "ñ";
	private static final String ENIE_URL = "%F1";
	
	@Autowired
	private Environment env;
	
	private ProcessDataSimply() {
		super();
	}
	
	/**
	 * Compone una lista de URLs de la web de Simply.
	 * Con estas URLs se realizarán las peticiones al
	 * sitio web para extraer los datos. 
	 * 
	 * @param document
	 * @param urlDto
	 * @param selectorCssDto
	 * @return List<String>
	 * @exception MalformedURLException
	 */
	@Override
	public List<String> getListaUrls(final Document document, final UrlDTO urlDto)
			throws MalformedURLException {
  
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		String urlAux;
		int fin = 30;
		int max = 10;
		int incremento = 2;
		
		String urlBase = urlDto.getNomUrl();
		
		int numresultados = NumberUtils.toInt(env.getProperty("flow.value.paginacion.url.simply"));
		
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		for (int i = 2; i <= max; i++) {
			
			urlAux = urlBase.replace("=1&", "=".concat(String.valueOf(i).concat("&")));
			
			if(i == 2) {
				urlAux = urlAux.replace("&fin=10", "&fin=" + fin);
			} else {
				urlAux = urlAux.replace("&fin=10", "&fin=" + fin*incremento++);
			}
			
			listaUrls.add(urlAux);
		}
		
		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}
		
		return listaUrls;
	}

	/**
	 * Reemplaza los caracteres especiales y los transforma en
	 * caracteres unicode.<br>
	 * Ejemplo: <b>"ñ" => "%F1"</b>
	 * 
	 * @param producto
	 * @return String
	 */
	public String reemplazarCaracteres(final String producto) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return producto.replace(STRING_ENIE_MIN, ENIE_URL);
	}
}
