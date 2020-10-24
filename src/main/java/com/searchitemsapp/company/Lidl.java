package com.searchitemsapp.company;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Lidl implements Company {

	private Environment env;
	
	@Override
	public List<String> getUrls(final Document document, final UrlDto urlDto)
				throws MalformedURLException {
		
		String urlBase = urlDto.getNomUrl();		
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		return listaUrls;
	}

	public Long getId() {
		return NumberUtils.toLong(env.getProperty("flow.value.did.empresa.lidl"));
	}
}
