package com.searchitemsapp.company;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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
public class Alcampo implements Company {
	
	private Environment environment;
	
	@Override
	public List<String> getUrls(Document document, UrlDto urlDto) 
			throws MalformedURLException {
		
		var urlBase = urlDto.getNomUrl();
		var lastElement = StringUtils.EMPTY;
		
		var paginationSelector = urlDto.getSelectores().getSelPaginacion();	
		var strPaginacion = Constants.ZERO.getValue();
			
		List<String> selectors = Lists.newArrayList();
		
		var tokenizer = new StringTokenizer(paginationSelector, Constants.PIPE.getValue());  
		
		while (tokenizer.hasMoreTokens()) {  
			selectors.add(tokenizer.nextToken());
		}
		 
		var elements = document.select(selectors.get(0));
		List<String> urls = Lists.newArrayList();
		
		urls.add(urlBase);
		
		if(!elements.isEmpty()) {
			lastElement = elements.get(elements.size()-1).attr(selectors.get(1));
		}
		
		var m = Pattern.compile(Constants.PAGE_REGEX.getValue()).matcher(lastElement);
		
		if(m.find()) {
			strPaginacion=m.group(1);
		}
		
		var intPaginacion = NumberUtils.toInt(strPaginacion.trim());
		
		var mainURL = new URL(urlBase);
		var path = new StringBuilder(10);
		path.append(mainURL.getProtocol())
		.append(Constants.PROTOCOL_ACCESSOR.getValue())
		.append(mainURL.getHost())
		.append(lastElement);
		
		for (var i = 1; i <= 3; i++) {
			urls.add(path.toString().replace("&page="+intPaginacion, "&page=".concat(String.valueOf(i))));
		}	
		
		return urls;
	}
	
	public Long getId() {
		return NumberUtils.toLong(environment.getProperty("flow.value.did.empresa.alcampo"));
	}
}
