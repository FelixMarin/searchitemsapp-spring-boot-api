package com.searchitemsapp.company;

import java.net.MalformedURLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class Hipercor implements Company {

	private Environment environment;

	@Override
	public List<String> getUrls(final Document document, 
			final UrlDto urlDto) throws MalformedURLException {
		
		String urlBase = urlDto.getNomUrl();
		
		String paginationSelector = urlDto.getSelectores().getSelPaginacion();	
		
		String pagination = document.select(paginationSelector).text();
			
		int resultLength = NumberUtils.toInt(environment.getProperty("flow.value.paginacion.url.hipercor"));
		
		Matcher m = Pattern.compile(Constants.FROM_TO_ECI.getValue()).matcher(pagination);
		
		if(m.find()) {
			pagination=m.group(1);
		}
		
		int intPaginacion = NumberUtils.toInt(pagination.trim());
		
		List<String> urls = Lists.newArrayList();
		urls.add(urlBase);
	
		for (int i = 2; i <= intPaginacion; i++) {
			urls.add(urlBase.replace("/1/", Constants.SLASH.getValue(
					).concat(String.valueOf(i)
							.concat(Constants.SLASH.getValue()))));
		}
		
		if(resultLength > 0 && resultLength <= urls.size()) {
			urls = urls.subList(0, resultLength);
		}		
		
		return urls;
	}

	@Override
	public Long getId() {
		return NumberUtils.toLong(environment.getProperty("flow.value.did.empresa.hipercor"));
	}
}
