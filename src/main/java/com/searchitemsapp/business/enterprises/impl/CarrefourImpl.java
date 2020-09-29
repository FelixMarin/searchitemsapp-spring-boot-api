package com.searchitemsapp.business.enterprises.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.enterprises.Company;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.resources.Constants;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CarrefourImpl implements Company {
	
	private Environment environment;

	@Override
	public List<String> getUrls(final Document document, 
			final UrlDto urlDto) throws MalformedURLException {
		
		String urlBase = urlDto.getNomUrl();
		
		String paginationSelector = urlDto.getSelectores().getSelPaginacion();	
		
		int numresultados = NumberUtils.toInt(environment.getProperty("flow.value.paginacion.url.carrefour"));
		
		StringTokenizer tokenizer = new StringTokenizer(paginationSelector, Constants.PIPE.getValue());  
		
		List<String> selectors = Lists.newArrayList();
		
		while (tokenizer.hasMoreTokens()) {  
			selectors.add(tokenizer.nextToken());
		}
			
		List<String> listaUrls = Lists.newArrayList();
		
		Elements elements = document.select(selectors.get(0));
		
		listaUrls.add(urlBase);
		
		URL url = new URL(urlBase);
		String strUrlEmpresa = url.getProtocol()
				.concat(Constants.PROTOCOL_ACCESSOR.getValue()).concat(url.getHost());
		
		for (Element element : elements) {
			listaUrls.add(strUrlEmpresa.concat(element.attr(selectors.get(1))));
		}
		
		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}
		
		return listaUrls;
	}
	
	public int get_DID() {
		return NumberUtils.toInt(environment.getProperty("flow.value.did.empresa.carrefour"));
	}
}
