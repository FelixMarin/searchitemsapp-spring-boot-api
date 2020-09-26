package com.searchitemsapp.business.enterprises.impl;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.enterprises.Enterprise;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class HipercorImpl implements Enterprise {

	private Environment env;

	@Override
	public List<String> getListaUrls(final Document document, 
			final UrlDto urlDto) throws MalformedURLException {
		
		String urlBase = urlDto.getNomUrl();
		
		String selectorPaginacion = urlDto.getSelectores().getSelPaginacion();	
		
		String strPaginacion = document.select(selectorPaginacion).text();
			
		int numresultados = NumberUtils.toInt(env.getProperty("flow.value.paginacion.url.hipercor"));
		
		Matcher m = Pattern.compile(".*de ([0-9]+)").matcher(strPaginacion);
		
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

	@Override
	public int get_DID() {
		return NumberUtils.toInt(env.getProperty("flow.value.did.empresa.hipercor"));
	}
	
	@Override
	public String eliminarMarcaPrincipio(String productName) {

		String[] nomProdSeparado = productName.trim().split(StringUtils.SPACE);

		StringBuilder stringBuilder = new StringBuilder(10);

		Arrays.asList(nomProdSeparado).stream().filter(palabra -> !palabra.toUpperCase().equals(palabra))
				.forEach(palabra -> {
					stringBuilder.append(palabra).append(StringUtils.SPACE);
				});

		return stringBuilder.toString();
	}
}
