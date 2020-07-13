package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * extracción de datos del sitio web de ECI.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class ProcessDataECI implements IFProcessDataEmpresas {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataECI.class);  
	
	private static final String PATTERN = ".*de ([0-9]+)";
	
	@Autowired
	private Environment env;

	public ProcessDataECI() {
		super();
	}

	/**
	 * Compone una lista de URLs de la empresa ECI.
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
	
		String selectorPaginacion = urlDto.getSelectores().get("SEL_PAGINACION");

		String strPaginacion = document.select(selectorPaginacion).text();

		int numresultados = NumberUtils.toInt(env.getProperty("flow.value.paginacion.url.eci"));

		Matcher m = Pattern.compile(PATTERN).matcher(strPaginacion);	
		if(m.find()) {
			strPaginacion=m.group(1);
		}

		int intPaginacion = NumberUtils.toInt(strPaginacion.trim());

		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);

		for (int i = 2; i <= intPaginacion; i++) {
			listaUrls.add(urlBase.replace("/1/", "/".concat(String.valueOf(i).concat("/"))));
		}

		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}	
		
		return listaUrls;
	}
}
