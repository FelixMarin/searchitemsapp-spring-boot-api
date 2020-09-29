package com.searchitemsapp.business.enterprises.impl;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.enterprises.Company;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.resources.Constants;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SimplyImpl implements Company {
	
	private Environment environment;

	@Override
	public List<String> getUrls(final Document document, final UrlDto urlDto) {
 
		String urlAux;
		int itemsToShow = 30;
		int maxNumberOfUrls = 10;
		int increment = 2;
		
		String urlBase = urlDto.getNomUrl();
		
		int numresultados = NumberUtils.toInt(environment.getProperty("flow.value.paginacion.url.simply"));
		
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		for (int i = 2; i <= maxNumberOfUrls; i++) {
			
			urlAux = urlBase.replace("=1&", "=".concat(String.valueOf(i).concat("&")));
			
			if(i == 2) {
				urlAux = urlAux.replace("&fin=10", "&fin=" + itemsToShow);
			} else {
				urlAux = urlAux.replace("&fin=10", "&fin=" + itemsToShow*increment++);
			}
			
			listaUrls.add(urlAux);
		}
		
		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}
		
		return listaUrls;
	}

	@Override
	public String reemplazarCaracteres(final String producto) {
		return producto.replace(Constants.ENIE_MIN.getValue(), 
				Constants.ENIE_URL.getValue());
	}

	@Override
	public int get_DID() {
		return NumberUtils.toInt(environment.getProperty("flow.value.did.empresa.simply"));
	}
}
