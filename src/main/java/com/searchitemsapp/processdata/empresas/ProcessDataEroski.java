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
 * extracción de datos del sitio web de Eroski.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class ProcessDataEroski implements IFProcessDataEroski {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataEroski.class);  
	
	private static final String STRING_ENIE_MIN = "ñ";
	private static final String ENIE_EROSKI = "$00f1";
	private static final String[] ARRAY_TILDES_EROSKI = {"$00e1","$00e9","$00ed","$00f3","$00fa"};
	private static final String[] ARRAY_TILDES_NORMALES_MIN = {"á","é","í","ó","ú"};
	
	@Autowired
	private Environment env;
	
	private ProcessDataEroski() {
		super();
	}
	
	/**
	 * Compone una lista de URLs de la empresa Eroski.
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
		
		/**
		 * Se obtiene la URL base. Esta es la URL principal 
		 * del conjunto de páginas obtenidas como resultado
		 * de la búsqueda del producto. A partir de esta URL 
		 * se generan las de paginación.
		 */
		String urlBase = urlDto.getNomUrl();
		
		/**
		 * Se obtiene del fichero de propiedades el número máximo de
		 * páginas que se van a pedir al sitio web.
		 */	
		int numresultados = NumberUtils.toInt(env.getProperty("flow.value.paginacion.url.eroski"));
		
		/**
		 * Se añade la URL base a la lista en formato string.
		 */
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		/**
		 * Se compone la lista de URLs que se van a solicitar 
		 * al sitio web.
		 */
		for (int i = 1; i <= 20; i++) {
			listaUrls.add(urlBase.replace("=0&", "=".concat(String.valueOf(i).concat("&"))));
		}
		
		/**
		 * Se eliminan las URLs que superen el número maximo
		 * de resultados permitidos indicados en la variable
		 * 'numeroresultados'.
		 */
		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}		
		
		return listaUrls;
	}

	/**
	 * Reemplaza los caracteres especiales que pueda tener
	 * el nombre del producto por sus correspondientes unicode. 
	 * <br>Ejemplo: "ñ" -> "$00f1".
	 *  
	 * @param producto
	 * @return String
	 */
	public String reemplazarCaracteres(final String producto) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		String productoTratado = producto
				.replace(STRING_ENIE_MIN, ENIE_EROSKI);
		
		for (int i = 0; i < ARRAY_TILDES_EROSKI.length; i++) {
			productoTratado = productoTratado
					.replace(ARRAY_TILDES_NORMALES_MIN[i], ARRAY_TILDES_EROSKI[i]);
		}
		
		return productoTratado;
	}
}
