package com.searchitemsapp.company;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.resource.Constants;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Eroski implements Company {
	
	private static final String EQUALS_ZERO_AMPERSAND = "=0&";
	private static final String[] ARRAY_TILDES = {"$00e1","$00e9","$00ed","$00f3","$00fa"};
	private static final String[] ARRAY_TILDES_NORMALES_MIN = {"á","é","í","ó","ú"};
	
	private Environment environment;

	@Override
	public List<String> getUrls(final Document document, final UrlDto urlDto) 
			throws MalformedURLException {
		
		var urlBase = urlDto.getNomUrl();
	
		var relsultLength = NumberUtils.toInt(environment.getProperty("flow.value.paginacion.url.eroski"));
	
		List<String> urls = Lists.newArrayList();
		urls.add(urlBase);

		for (var i = 1; i <= 20; i++) {
			urls.add(urlBase.replace(EQUALS_ZERO_AMPERSAND, Constants.EQUALS.getValue().concat(String.valueOf(i).concat(Constants.AMPERSAND.getValue()))));
		}
	
		if(relsultLength > 0 && relsultLength <= urls.size()) {
			urls = urls.subList(0, relsultLength);
		}		
		
		return urls;
	}

	@Override
	public String replaceCharacters(final String productName) {
		
		var productoTratado = productName
				.replace(Constants.ENIE_MIN.getValue(), Constants.ENIE_EROSKI.getValue());
		 
		for (var i = 0; i < ARRAY_TILDES.length; i++) {
			productoTratado = productoTratado
					.replace(ARRAY_TILDES_NORMALES_MIN[i], ARRAY_TILDES[i]);
		}
		
		return productoTratado;
	}

	@Override
	public Long getId() {
		return NumberUtils.toLong(environment.getProperty("flow.value.did.empresa.eroski"));
	}
}
