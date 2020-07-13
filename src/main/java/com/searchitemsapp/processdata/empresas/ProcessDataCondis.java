package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDTO;

/**
 * Módulo de scraping especifico diseñado para la 
 * extracción de datos del sitio web de Condis.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class ProcessDataCondis implements IFProcessDataCondis {

	private static final String DOBLE_CERO_STRING = "00";

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataCondis.class);  
	
	private static final String REGEX_NUMERO_DECIMAL = "\\d*[,][0-9]*";
	private static final String[] ARRAY_TILDES_NORMALES_MIN = {"á","é","í","ó","ú"};
	private static final String[] ARRAY_VOCALES_MIN = {"a","e","i","o","u"};
	private static final String STRING_ENIE_MIN = "ñ";
	private static final String ZERO_STRING = "0";
	private static final String COMMA_STRING = ",";
	private static final String DOT_STRING = ".";
	private static final String DECIMALES_STRING = ",00";
	private static final char LEFT_SLASH_CHAR = '\'';
	private static final String SPECIALS_CHARS_STRING = "\r\n|\r|\n";
	
	private static final String ENIE_CONDIS = "%D1";

	public ProcessDataCondis() {
		super();
	}
	
	/**
	 * Compone una lista de URLs de la empresa Condis.
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
			final UrlDTO urlDto)
					throws MalformedURLException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		String urlBase = urlDto.getNomUrl();
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		return listaUrls;
	}
	
	
	public String tratarTagScript(final Element elem, final String cssSelector) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		String resultado = StringUtils.EMPTY;
		Matcher matcher;
		
		if(Objects.isNull(elem) || StringUtils.isAllEmpty(cssSelector)) {
			return resultado;
		}
		resultado = elem.select(cssSelector).html().replace(DOT_STRING, COMMA_STRING);
		
		if(resultado.split(SPECIALS_CHARS_STRING).length > 1) {
			resultado = resultado.split(SPECIALS_CHARS_STRING)[1].trim();
			
			matcher = Pattern.compile(REGEX_NUMERO_DECIMAL).matcher(resultado);
			
			if(matcher.find()) {
				resultado = matcher.group(0);
			}
		
		} else {
			resultado = resultado.substring(resultado.indexOf(LEFT_SLASH_CHAR)+1, resultado.length());
			resultado = resultado.substring(0, resultado.indexOf(LEFT_SLASH_CHAR));
			
			if(resultado.contains(COMMA_STRING) &&
					resultado.substring(resultado.indexOf(COMMA_STRING), 
							resultado.length()).length()  == 2) {
				resultado += ZERO_STRING;
			}else {
				resultado = resultado.concat(DECIMALES_STRING);
			}
		}
		
		if(resultado.startsWith(COMMA_STRING)) {
			resultado = ZERO_STRING.concat(resultado);
		}
		
		if(resultado.endsWith(COMMA_STRING)) {
			resultado = resultado.concat(DOBLE_CERO_STRING);
		}
		
		return resultado;
	}
	
	/**
	 * Reemplaza los caracteres con tilde por los mismos
	 * caracteres sin tilde.
	 * 
	 * @param producto
	 * @return String
	 */
	public String eliminarTildesProducto(final String producto) {
		
		if(StringUtils.isAllEmpty(producto)) {
			return producto;
		}
		
		String productoAux = producto.toLowerCase();
		
		for (int i = 0; i < ARRAY_VOCALES_MIN.length; i++) {
			productoAux = productoAux.replace(ARRAY_TILDES_NORMALES_MIN[i], 
					ARRAY_VOCALES_MIN[i]);
		}
		
		return productoAux;
	}
	
	/**
	 * Metodo que reemplaza el caracter 'ñ'en la URL por
	 * un caracter especial codigficado.
	 * 
	 * @param producto
	 * @return String
	 */
	public String reemplazarCaracteres(final String producto) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return producto.replace(STRING_ENIE_MIN, ENIE_CONDIS);
		
	}

}
