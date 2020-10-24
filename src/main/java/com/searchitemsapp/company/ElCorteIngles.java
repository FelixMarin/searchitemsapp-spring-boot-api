package com.searchitemsapp.company;

import java.net.MalformedURLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.resource.Constants;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ElCorteIngles implements Company {
	
	private Environment environment;

	@Override
	public Long getId() {
		return NumberUtils.toLong(environment.getProperty("flow.value.did.empresa.eci"));
	}

	@Override
	public List<String> getUrls(final Document document, 
			final UrlDto urlDto) throws MalformedURLException {

		String urlBase = urlDto.getNomUrl();
	
		String selectorPaginacion = urlDto.getSelectores().getSelPaginacion();	

		String strPaginacion = document.select(selectorPaginacion).text();

		int numresultados = NumberUtils.toInt(environment.getProperty("flow.value.paginacion.url.eci"));

		Matcher m = Pattern.compile(Constants.FROM_TO_ECI.getValue()).matcher(strPaginacion);	
		if(m.find()) {
			strPaginacion=m.group(1);
		}

		int intPaginacion = NumberUtils.toInt(strPaginacion.trim());

		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);

		for (int i = 2; i <= intPaginacion; i++) {
			listaUrls.add(urlBase.replace("/1/", Constants.SLASH.getValue(
					).concat(String.valueOf(i)
							.concat(Constants.SLASH.getValue()))));
		}

		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}	
		
		return listaUrls;
	}
	
	@Override
	public String selectorTextExtractor(Element documentElement,
			List<String> cssSelectorList, String cssSelector) {
		return documentElement.selectFirst(environment
				.getProperty("flow.value.pagina.precio.eci.offer")).text();
	}
}
