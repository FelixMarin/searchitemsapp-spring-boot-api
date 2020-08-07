package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDTO;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ProcessDataCondis implements IFProcessDataCondis {

	private static final String DOBLE_CERO_STRING = "00";
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
	
	@Autowired
	private Environment env;

	@Override
	public List<String> getListaUrls(final Document document, 
			final UrlDTO urlDto)
					throws MalformedURLException {
		
		String urlBase = urlDto.getNomUrl();
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		return listaUrls;
	}
	
	
	public String tratarTagScript(final Element elem, final String cssSelector) {

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
	
	public String reemplazarCaracteres(final String producto) {
		return producto.replace(STRING_ENIE_MIN, ENIE_CONDIS);
	}

	@Override
	public int get_DID() {
		return NumberUtils.toInt(env.getProperty("flow.value.did.empresa.condis"));
	}

	
}
