package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
 * extracción de datos del sitio web de Carrefour.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class ProcessDataCarrefour implements IFProcessDataEmpresas {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataCarrefour.class);   

	private static final String PROTOCOL_ACCESSOR ="://";
	
	@Autowired
	private Environment env;
	
	public ProcessDataCarrefour() {
		super();
	}

	/**
	 * Compone una lista de URLs de la empresa Carrefour.
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
		
		int numresultados = NumberUtils.toInt(env.getProperty("flow.value.paginacion.url.carrefour"));
		
		StringTokenizer st = new StringTokenizer(selectorPaginacion,"|");  
		
		List<String> liSelectorAtr = Lists.newArrayList();
		
		while (st.hasMoreTokens()) {  
			liSelectorAtr.add(st.nextToken());
		}
			
		List<String> listaUrls = Lists.newArrayList();
		
		Elements elements = document.select(liSelectorAtr.get(0));
		
		listaUrls.add(urlBase);
		
		URL url = new URL(urlBase);
		String strUrlEmpresa = url.getProtocol()
				.concat(PROTOCOL_ACCESSOR).concat(url.getHost());
		
		for (Element element : elements) {
			listaUrls.add(strUrlEmpresa.concat(element.attr(liSelectorAtr.get(1))));
		}
		
		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}
		
		return listaUrls;
	}
}
