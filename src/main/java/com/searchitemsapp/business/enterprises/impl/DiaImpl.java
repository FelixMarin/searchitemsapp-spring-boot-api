package com.searchitemsapp.business.enterprises.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.enterprises.Enterprise;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DiaImpl implements Enterprise {
	
	private static final String PROTOCOL_ACCESSOR ="://";
	
	private Environment env;

	@Override
	public List<String> getListaUrls(final Document document, final UrlDto urlDto) 
					throws MalformedURLException {
		
		String urlBase = urlDto.getNomUrl();
		List<String> liSelectorAtr = Lists.newArrayList();
		int numresultados = NumberUtils.toInt(env.getProperty("flow.value.paginacion.url.dia"));
		String selectorPaginacion = urlDto.getSelectores().getSelPaginacion();	

		StringTokenizer st = new StringTokenizer(selectorPaginacion, "|");

		while (st.hasMoreTokens()) {
			liSelectorAtr.add(st.nextToken());
		}

		Elements elements = document.select(liSelectorAtr.get(0));
		List<String> listaUrls = Lists.newArrayList();

		listaUrls.add(urlBase);

		URL url = new URL(urlBase);
		String strUrlEmpresa = url.getProtocol().concat(PROTOCOL_ACCESSOR).concat(url.getHost());

		for (Element element : elements) {
			listaUrls.add(strUrlEmpresa.concat(element.attr(liSelectorAtr.get(1))));
		}

		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}
		
		return listaUrls;
	}

	@Override
	public int get_DID() {
		return NumberUtils.toInt(env.getProperty("flow.value.did.empresa.dia"));
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
