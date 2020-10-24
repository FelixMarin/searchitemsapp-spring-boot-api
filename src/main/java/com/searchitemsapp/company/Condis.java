package com.searchitemsapp.company;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.resource.Constants;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Component
@AllArgsConstructor
public class Condis implements Company {
	
	private static final String SCRIPT = "script";
	private static final char LEFT_SLASH_CHAR = '\'';
	private static final String SPECIALS_CHARS_STRING = "\r\n|\r|\n";
	
	private Environment environment;
	
	@Override
	public List<String> getUrls(final Document document, 
			final UrlDto urlDto)
					throws MalformedURLException {
		
		String urlBase = urlDto.getNomUrl();
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		return listaUrls;
	}
	
	@Override
	public Long getId() {
		return NumberUtils.toLong(environment
				.getProperty("flow.value.did.empresa.condis"));
	}

	@Override
	public String replaceCharacters(String producto) {
		
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
		
		productoAux = productoAux.replace(Constants.ENIE_MIN.getValue(),
				Constants.ENIE_URL_D.getValue());
		
		return productoAux;
	}
	
	@Override
	public String selectorTextExtractor(@NonNull Element documentElement,
			List<String> cssSelectorList, String cssSelector) {
		
		if(cssSelectorList.isEmpty()) {
			return StringUtils.EMPTY;
		}else if(SCRIPT.equalsIgnoreCase(cssSelectorList.get(0))) {
 			return stractor(documentElement, cssSelector);		
		} else {
			return extractText(cssSelectorList.size(), 
				cssSelectorList, documentElement);
		}
	}
	
	private String stractor(Element documentElement, String cssSelector) {

		String resultado;
		Matcher matcher;
	  
		resultado = documentElement.select(cssSelector).html()
				.replace(Constants.DOT.getValue(), Constants.COMMA.getValue());
		
		if(resultado.split(SPECIALS_CHARS_STRING).length > 1) {
			resultado = resultado.split(SPECIALS_CHARS_STRING)[1].trim();
			
			matcher = Pattern.compile("\\d*[,][0-9]*").matcher(resultado);
			
			if(matcher.find()) {
				resultado = matcher.group(0);
			}
		
		} else {
			resultado = standarizeString(resultado);
		}
		
		if(resultado.startsWith(Constants.COMMA.getValue())) {
			resultado = Constants.ZERO.getValue().concat(resultado);
		}
		
		if(resultado.endsWith(Constants.COMMA.getValue())) {
			resultado = resultado.concat(Constants.DOUBLE_ZERO.getValue());
		}
		
		return resultado;
	}

	private String extractText(int selectorsListSize, List<String> cssSelectorList, 
			Element documentElement) {
		return selectorsListSize == 1?documentElement.select(cssSelectorList.get(0)).text():
			documentElement.select(cssSelectorList.get(0)).attr(cssSelectorList.get(1));
	}
	
	private String standarizeString(String valor) {
		String tratedValue = valor.substring(valor.indexOf(LEFT_SLASH_CHAR)+1, valor.length());
		
		Optional<Integer> indexCommilla = Optional.of(valor.indexOf(LEFT_SLASH_CHAR));
		
		Integer index = indexCommilla.filter(elem -> elem != -1).orElse(0);
		
		tratedValue = tratedValue.substring(0, index);
		 
		if(tratedValue.contains(Constants.COMMA.getValue()) &&
				tratedValue.substring(tratedValue.indexOf(Constants.COMMA.getValue()), 
						tratedValue.length()).length()  == 2) {
			tratedValue += Constants.ZERO.getValue();
		}else {
			tratedValue = valor.concat(Constants.COMMA_DECIMALS_EXTENSION.getValue());
		}
		
		return tratedValue;
	}
	
}
