package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDTO;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ProcessDataECI implements IFProcessDataECI {
	
	private static final String PATTERN = ".*de ([0-9]+)";
	
	@Autowired
	private Environment env;

	@Override
	public int get_DID() {
		return NumberUtils.toInt(env.getProperty("flow.value.did.empresa.eci"));
	}

	@Override
	public List<String> getListaUrls(final Document document, 
			final UrlDTO urlDto) throws MalformedURLException {

		String urlBase = urlDto.getNomUrl();
	
		String selectorPaginacion = urlDto.getSelectores().getSelPaginacion();	

		String strPaginacion = document.select(selectorPaginacion).text();

		int numresultados = NumberUtils.toInt(env.getProperty("flow.value.paginacion.url.eci"));

		Matcher m = Pattern.compile(PATTERN).matcher(strPaginacion);	
		if(m.find()) {
			strPaginacion=m.group(1);
		}

		int intPaginacion = NumberUtils.toInt(strPaginacion.trim());

		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);

		for (int i = 2; i <= intPaginacion; i++) {
			listaUrls.add(urlBase.replace("/1/", "/".concat(String.valueOf(i).concat("/"))));
		}

		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}	
		
		return listaUrls;
	}
}
