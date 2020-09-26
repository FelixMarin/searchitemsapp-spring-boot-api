package com.searchitemsapp.business.enterprises.impl;

import java.net.MalformedURLException;
import java.util.List;

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
public class LidlImpl implements Enterprise {

	private Environment env;
	
	@Override
	public List<String> getListaUrls(final Document document, final UrlDto urlDto)
				throws MalformedURLException {
		
		String urlBase = urlDto.getNomUrl();		
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		return listaUrls;
	}

	public int get_DID() {
		return NumberUtils.toInt(env.getProperty("flow.value.did.empresa.lidl"));
	}
}
