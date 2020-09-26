package com.searchitemsapp.business.enterprises.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.enterprises.Enterprise;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CondisImpl implements Enterprise {
	
	private static final String ZERO_STRING = "0";
	private static final String COMMA_STRING = ",";
	private static final char LEFT_SLASH_CHAR = '\'';
	private static final String SPECIALS_CHARS_STRING = "\r\n|\r|\n";
	
	private Environment environment;
	
	@Override
	public List<String> getListaUrls(final Document document, 
			final UrlDto urlDto)
					throws MalformedURLException {
		
		String urlBase = urlDto.getNomUrl();
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		return listaUrls;
	}
	
	@Override
	public int get_DID() {
		return NumberUtils.toInt(environment.getProperty("flow.value.did.empresa.condis"));
	}

	@Override
	public String reemplazarCaracteres(String producto) {
		
		String[] arVocales = {"a","e","i","o","u"};
		String[] arTildes = {"á","é","í","ó","ú"};
		
		if(StringUtils.isAllEmpty(producto)) {
			return producto;
		}
		
		String productoAux = producto.toLowerCase();
		
		for (int i = 0; i < arVocales.length; i++) {
			productoAux = productoAux.replace(arTildes[i], 
					arVocales[i]);
		}
		
		productoAux = productoAux.replace("ñ", "%D1");
		
		return productoAux;
	}
	
	@Override
	public String selectorTextExtractor(Element documentElement,
			List<String> cssSelectorList, String cssSelector) {
		
		int selectorsListSize = cssSelectorList.size();
		
		if("script".equalsIgnoreCase(cssSelectorList.get(0))) {

			String resultado = StringUtils.EMPTY;
			Matcher matcher;
			
			if(Objects.isNull(documentElement) || StringUtils.isAllEmpty(cssSelector)) {
				return resultado;
			}
			
			resultado = documentElement.select(cssSelector).html().replace(".", COMMA_STRING);
			
			if(resultado.split(SPECIALS_CHARS_STRING).length > 1) {
				resultado = resultado.split(SPECIALS_CHARS_STRING)[1].trim();
				
				matcher = Pattern.compile("\\d*[,][0-9]*").matcher(resultado);
				
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
					resultado = resultado.concat(",00");
				}
			}
			
			if(resultado.startsWith(COMMA_STRING)) {
				resultado = ZERO_STRING.concat(resultado);
			}
			
			if(resultado.endsWith(COMMA_STRING)) {
				resultado = resultado.concat("00");
			}
			
			return resultado;
				
		} else {

			if(selectorsListSize == 1) {
				return documentElement.select(cssSelectorList.get(0)).text();
			} else if(selectorsListSize == 2) {
				return documentElement.select(cssSelectorList.get(0)).attr(cssSelectorList.get(1));
			} else {
				return documentElement.select(cssSelector).text();
			}
		}
	}
	
}
