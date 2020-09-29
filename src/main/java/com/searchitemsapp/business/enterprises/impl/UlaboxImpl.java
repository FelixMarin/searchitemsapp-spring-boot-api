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
import com.searchitemsapp.business.enterprises.Company;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.resources.Constants;

import lombok.AllArgsConstructor;

/**
 * @author Felix Marin Ramirez
 *
 */
@Component
@AllArgsConstructor
public class UlaboxImpl implements Company {
	
	private Environment environment;

	@Override
	public List<String> getUrls(final Document document, 
			final UrlDto urlDto) throws MalformedURLException {
	
		String urlBase = urlDto.getNomUrl();
	
		List<String> urls = Lists.newArrayList();
		urls.add(urlBase);

		String paginationSelector = urlDto.getSelectores().getSelPaginacion();		
		String pagination = document.select(paginationSelector).text();

		int resultLength = NumberUtils.toInt(environment.getProperty("flow.value.paginacion.url.ulabox"));

		if(!StringUtils.isAllEmpty(pagination)) {
	
			if(pagination.contains(Constants.TREE_DOTS.getValue())) {
				
				Matcher m = Pattern.compile(Constants.NUMERIC_REGEX.getValue()).matcher(pagination);
				
				if(m.find()) {
					pagination=m.group(1);
				}
				
			} else {
				pagination = pagination.substring(pagination.length()-1, pagination.length());
			}

			int intPaginacion = NumberUtils.toInt(pagination.trim());

			for (int i = 2; i <= intPaginacion; i++) {
				urls.add(urlBase.concat("&p=") + i);
			}	

			if(resultLength > 0 && resultLength <= urls.size()) {
				urls = urls.subList(0, resultLength);
			}
		}
		
		return urls;
	}
	
	public int get_DID() {
		return NumberUtils.toInt(environment.getProperty("flow.value.did.empresa.ulabox"));
	}
}
