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
public class Caprabo implements Company {
	
	private Environment environment;
	
	public Long getId() {
		return NumberUtils.toLong(environment.getProperty("flow.value.did.empresa.caprabo"));
	}

	@Override
	public List<String> getUrls(final Document document, final UrlDto urlDto)
			throws MalformedURLException {
		List<String> url = Lists.newArrayList();
		url.add(urlDto.getNomUrl());
		return url;
	}
}
