package com.searchitemsapp.company;

import java.net.MalformedURLException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
		
		String urlBase = urlDto.getNomUrl();
		
		String paginationSelector = urlDto.getSelectores().getSelPaginacion();	
		String strPaginacion = Constants.ZERO.getValue();
			
		int numresultados = NumberUtils.toInt(environment.getProperty("flow.value.paginacion.url.alcampo"));
		
		List<String> selectors = Lists.newArrayList();
		
		StringTokenizer tokenizer = new StringTokenizer(paginationSelector, Constants.PIPE.getValue());  
		
		while (tokenizer.hasMoreTokens()) {  
			selectors.add(tokenizer.nextToken());
		}
		 
		Elements elements = document.select(selectors.get(0));
		List<String> urls = Lists.newArrayList();
		
		if(elements.isEmpty()) {
			urls.add(urlBase);
			return urls;
		}
		
		String lastElement = elements.get(elements.size()-1).attr(selectors.get(1));
		
		Matcher m = Pattern.compile(Constants.PAGE_REGEX.getValue()).matcher(lastElement);
		
		if(m.find()) {
			strPaginacion=m.group(1);
		}
		
		int intPaginacion = NumberUtils.toInt(strPaginacion.trim());
		
		for (int i = 2; i <= intPaginacion; i++) {
			urls.add(urlBase.replace("&page=1", "&page=".concat(String.valueOf(i))));
		}
		
		if(numresultados > 0 && numresultados <= urls.size()) {
			urls = urls.subList(0, numresultados);
		}		
		
		return urls;
	}
	
	public Long getId() {
		return NumberUtils.toLong(environment.getProperty("flow.value.did.empresa.alcampo"));
	}
}
