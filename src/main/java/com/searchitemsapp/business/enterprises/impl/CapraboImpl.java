package com.searchitemsapp.business.enterprises.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.enterprises.Company;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CapraboImpl implements Company {
	
	private Environment environment;
	
	public int get_DID() {
		return NumberUtils.toInt(environment.getProperty("flow.value.did.empresa.caprabo"));
	}

	@Override
	public List<String> getUrls(final Document document, final UrlDto urlDto)
			throws MalformedURLException {
		
		String urlBase = urlDto.getNomUrl();
		List<String> urls = Lists.newArrayList();
		urls.add(urlBase);
		
		return urls;
	}
}
