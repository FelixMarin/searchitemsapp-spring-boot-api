package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDTO;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ProcessDataAlcampoImpl implements ProcessDataEmpresas {
	
	
	private static final String PATTERN = ".*&page=([0-9]+)";
	private static final String ZERO_STRING = "0";
	
	@Autowired
	private Environment env;
	
	@Override
	public List<String> getListaUrls(Document document, UrlDTO urlDto) 
			throws MalformedURLException {
		
		String urlBase = urlDto.getNomUrl();
		
		String selectorPaginacion = urlDto.getSelectores().getSelPaginacion();	
		String strPaginacion = ZERO_STRING;
			
		int numresultados = NumberUtils.toInt(env.getProperty("flow.value.paginacion.url.alcampo"));
		
		List<String> liSelectorAtr = Lists.newArrayList();
		
		StringTokenizer st = new StringTokenizer(selectorPaginacion,"|");  
		
		while (st.hasMoreTokens()) {  
			liSelectorAtr.add(st.nextToken());
		}
		
		Elements elements = document.select(liSelectorAtr.get(0));
		List<String> listaUrls = Lists.newArrayList();
		
		if(elements.isEmpty()) {
			listaUrls.add(urlBase);
			return listaUrls;
		}
		
		String ultimoElemento = elements.get(elements.size()-1).attr(liSelectorAtr.get(1));
		
		Matcher m = Pattern.compile(PATTERN).matcher(ultimoElemento);
		
		if(m.find()) {
			strPaginacion=m.group(1);
		}
		
		int intPaginacion = NumberUtils.toInt(strPaginacion.trim());
		
		for (int i = 2; i <= intPaginacion; i++) {
			listaUrls.add(urlBase.replace("&page=1", "&page=".concat(String.valueOf(i))));
		}
		
		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}		
		
		return listaUrls;
	}
	
	public int get_DID() {
		return NumberUtils.toInt(env.getProperty("flow.value.did.empresa.alcampo"));
	}
}
