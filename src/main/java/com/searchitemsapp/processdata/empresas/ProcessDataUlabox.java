package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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
 * extracción de datos del sitio web de Ulabox.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class ProcessDataUlabox implements IFProcessDataEmpresas {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataUlabox.class);  
	
	private static final String PATTERN = ".*… ([0-9]+)";
	private static final String CHARSET = "…";
	
	@Autowired
	private Environment env;

	public ProcessDataUlabox() {
		super();
	}
	
	/**
	 * Compone una lista de URLs de la web de Ulabox.
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
	public List<String> getListaUrls(final Document document, 
			final UrlDTO urlDto) throws MalformedURLException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
	
		String urlBase = urlDto.getNomUrl();
	
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);

		String selectorPaginacion = urlDto.getSelectores().get("SEL_PAGINACION");		
		String strPaginacion = document.select(selectorPaginacion).text();

		int numresultados = NumberUtils.toInt(env.getProperty("flow.value.paginacion.url.ulabox"));

		if(!StringUtils.isAllEmpty(strPaginacion)) {
	
			if(strPaginacion.contains(CHARSET)) {
				
				Matcher m = Pattern.compile(PATTERN).matcher(strPaginacion);
				
				if(m.find()) {
					strPaginacion=m.group(1);
				}
				
			} else {
				strPaginacion = strPaginacion.substring(strPaginacion.length()-1, strPaginacion.length());
			}

			int intPaginacion = NumberUtils.toInt(strPaginacion.trim());

			for (int i = 2; i <= intPaginacion; i++) {
				listaUrls.add(urlBase.concat("&p=") + i);
			}	

			if(numresultados > 0 && numresultados <= listaUrls.size()) {
				listaUrls = listaUrls.subList(0, numresultados);
			}
		}
		
		return listaUrls;
	}
}
