package com.searchitemsapp.business.enterprises.impl;

import java.net.MalformedURLException;
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

/**
 * @author Felix Marin Ramirez
 *
 */
@Component
@AllArgsConstructor
public class UlaboxImpl implements Enterprise {
	
	private static final String PATTERN = ".*… ([0-9]+)";
	private static final String CHARSET = "…";
	
	private Environment env;

	@Override
	public List<String> getListaUrls(final Document document, 
			final UrlDto urlDto) throws MalformedURLException {
	
		String urlBase = urlDto.getNomUrl();
	
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);

		String selectorPaginacion = urlDto.getSelectores().getSelPaginacion();		
		String strPaginacion = document.select(selectorPaginacion).text();

		int numresultados = NumberUtils.toInt(env.getProperty("flow.value.paginacion.url.ulabox"));

		if(!StringUtils.isAllEmpty(strPaginacion)) {
	
			if(strPaginacion.contains(CHARSET)) {
				
				Matcher m = Pattern.compile(PATTERN).matcher(strPaginacion);
				
				if(m.find()) {
					strPaginacion=m.group(1);
				}
				
			} else {
				strPaginacion = strPaginacion.substring(strPaginacion.length()-1, strPaginacion.length());
			}

			int intPaginacion = NumberUtils.toInt(strPaginacion.trim());

			for (int i = 2; i <= intPaginacion; i++) {
				listaUrls.add(urlBase.concat("&p=") + i);
			}	

			if(numresultados > 0 && numresultados <= listaUrls.size()) {
				listaUrls = listaUrls.subList(0, numresultados);
			}
		}
		
		return listaUrls;
	}
	
	public int get_DID() {
		return NumberUtils.toInt(env.getProperty("flow.value.did.empresa.ulabox"));
	}
}
