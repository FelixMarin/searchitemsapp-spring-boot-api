package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import org.springframework.core.env.Environment;
import com.searchitemsapp.dto.UrlDTO;

/**
 * Módulo de scraping especifico diseñado para la 
 * extracción de datos del sitio web de Alcampo.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class ProcessDataAlcampo implements IFProcessDataEmpresas {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataAlcampo.class);   
	
	private static final String PATTERN = ".*&page=([0-9]+)";
	private static final String ZERO_STRING = "0";
	
	@Autowired
	private Environment env;

	public ProcessDataAlcampo() {
		super();
	}
	
	/**
	 * Compone una lista de URLs de la web de Alcampo.
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
	public List<String> getListaUrls(Document document, UrlDTO urlDto) 
			throws MalformedURLException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		String urlBase = urlDto.getNomUrl();
		
		String selectorPaginacion = urlDto.getSelectores().getSelPaginacion();	
		String strPaginacion = ZERO_STRING;
			
		int numresultados = NumberUtils.toInt(env.getProperty("flow.value.paginacion.url.alcampo"));
		
		List<String> liSelectorAtr = Lists.newArrayList();
		
		StringTokenizer st = new StringTokenizer(selectorPaginacion,"|");  
		
		while (st.hasMoreTokens()) {  
			liSelectorAtr.add(st.nextToken());
		}
		
		Elements elements = document.select(liSelectorAtr.get(0));
		List<String> listaUrls = Lists.newArrayList();
		
		if(elements.isEmpty()) {
			listaUrls.add(urlBase);
			return listaUrls;
		}
		
		String ultimoElemento = elements.get(elements.size()-1).attr(liSelectorAtr.get(1));
		
		Matcher m = Pattern.compile(PATTERN).matcher(ultimoElemento);
		
		if(m.find()) {
			strPaginacion=m.group(1);
		}
		
		int intPaginacion = NumberUtils.toInt(strPaginacion.trim());
		
		for (int i = 2; i <= intPaginacion; i++) {
			listaUrls.add(urlBase.replace("&page=1", "&page=".concat(String.valueOf(i))));
		}
		
		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}		
		
		return listaUrls;
	}
}
