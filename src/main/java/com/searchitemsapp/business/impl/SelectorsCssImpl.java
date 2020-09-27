package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.SelectorsCss;
import com.searchitemsapp.business.enterprises.Enterprise;
import com.searchitemsapp.business.enterprises.factory.EnterpriseFactory;
import com.searchitemsapp.dao.CssSelectorsDao;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.EnterpriseDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Component
@AllArgsConstructor
public class SelectorsCssImpl implements SelectorsCss {
	
	private CssSelectorsDao cssSelectorsDao;
	private Environment environment;
	private EnterpriseFactory enterpriseFactory;

	@Override
	public List<CssSelectorsDto> selectorCssListByEnterprise(
			final String enterpriseId) {

		String enterprisesIdSepearatedByCommas;
		
		if("ALL".equalsIgnoreCase(enterpriseId)) {
			enterprisesIdSepearatedByCommas = environment.getProperty("flow.value.all.id.empresa");
		} else {
			enterprisesIdSepearatedByCommas = enterpriseId;
		}
		
		StringTokenizer tokenizer = new StringTokenizer(enterprisesIdSepearatedByCommas, ","); 			
		List<Integer> enterpriseIdsList = Lists.newArrayList();
		
		while (tokenizer.hasMoreElements()) {
			enterpriseIdsList.add(Integer.parseInt(String.valueOf(tokenizer.nextElement())));
			
		}
		
		List<CssSelectorsDto> cssSelectorDtoList = Lists.newArrayList();
		
		enterpriseIdsList.forEach(enterpriseIdLocal -> {
			
			try {	
				
				cssSelectorDtoList.addAll(
						cssSelectorsDao.findByTbSia(
								CssSelectorsDto.builder().build(), 
								EnterpriseDto.builder().did(enterpriseIdLocal).build()));
			
			}catch(IOException e) {
				throw new UncheckedIOException(e);
			}
			
		});
		
		return cssSelectorDtoList;
	}
	
	@Override
	public boolean validateSelector(Element documentElement) {
		return Objects.nonNull(documentElement.selectFirst(environment.getProperty("flow.value.pagina.siguiente.carrefour"))) ||
		Objects.nonNull(documentElement.selectFirst(environment.getProperty("flow.value.pagina.acceso.popup.peso")));
	}
	
	@Override
	public CssSelectorsDto addCssSelectors(UrlDto urlDTO, List<CssSelectorsDto> cssSelectorList) {
		
		return cssSelectorList
				.stream().filter(x -> x.getDidEmpresa().equals(urlDTO.getDidEmpresa()))
				.collect(Collectors.toList()).get(0);
	}
	
	public String elementByCssSelector(@NonNull Element documentElement, 
			@NonNull String cssSelector,
			@NonNull UrlDto urlDto) throws MalformedURLException {
				
		List<String> cssSelectorList = Lists.newArrayList();		
		StringTokenizer st = new StringTokenizer(cssSelector,"|");  
		
		while (st.hasMoreTokens()) {  
			cssSelectorList.add(st.nextToken());
		}
		
		Enterprise enterprise = enterpriseFactory.getInstance(urlDto.getDidEmpresa());
		
		String textExtracted = enterprise.selectorTextExtractor(documentElement, cssSelectorList, cssSelector);
		
		return cleanProductTextExtractedFromCssSelector(textExtracted, urlDto.getNomUrl());
	}
	
	private String cleanProductTextExtractedFromCssSelector(@NonNull final String textProduct, 
			String strUrl) throws MalformedURLException {
		
		String textProductFiltered = textProduct.replaceAll("[()]", "");			
		String caracteres = StringUtils.EMPTY;
		
		if(Objects.nonNull(textProductFiltered) && textProductFiltered.trim().startsWith("//")) {
			caracteres = "https:".concat(textProductFiltered);
		} else if(Objects.nonNull(textProductFiltered) && textProductFiltered.trim().startsWith("/")) {
			URL url = new URL(strUrl);
			String enterpriseUrl = url.getProtocol().concat("://").concat(url.getHost());
			caracteres = enterpriseUrl.concat(textProductFiltered); 
		} else if(Objects.nonNull(textProductFiltered)){
			caracteres = textProductFiltered;
		}
		 
		String resultado = caracteres.replaceAll("\\(", StringUtils.EMPTY);
		resultado = resultado.replaceAll("\\)", StringUtils.EMPTY);
		
		resultado = resultado.replace("€", " eur");
		resultado = resultado.replace("Kilo", "kg");
		resultado = resultado.replace(" / ", "/");
		resultado = resultado.replace(" \"", "\"");
		 
		return resultado;
	}

}
