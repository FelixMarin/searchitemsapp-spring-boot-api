package com.searchitemsapp.company;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

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
public class Carrefour implements Company {
	
	private Environment environment;

	@Override
	public List<String> getUrls(final Document document, 
			final UrlDto urlDto) throws MalformedURLException {
		
		var urlBase = urlDto.getNomUrl();
		
		var paginationSelector = urlDto.getSelectores().getSelPaginacion();	
		
		var numresultados = NumberUtils.toInt(environment.getProperty("flow.value.paginacion.url.carrefour"));
		
		var tokenizer = new StringTokenizer(paginationSelector, Constants.PIPE.getValue());  
		
		List<String> selectors = Lists.newArrayList();
		
		while (tokenizer.hasMoreTokens()) {  
			selectors.add(tokenizer.nextToken());
		}
			
		List<String> listaUrls = Lists.newArrayList();
		
		var elements = document.select(selectors.get(0));
		
		var url = new URL(urlBase);
		var strUrlEmpresa = url.getProtocol()
				.concat(Constants.PROTOCOL_ACCESSOR.getValue()).concat(url.getHost());
		
		for (var element : elements) {
			listaUrls.add(strUrlEmpresa.concat(element.attr(selectors.get(1))));
		}
		
		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}
		
		return listaUrls;
	}
	
	public Long getId() {
		return NumberUtils.toLong(environment.getProperty("flow.value.did.empresa.carrefour"));
	}
}
