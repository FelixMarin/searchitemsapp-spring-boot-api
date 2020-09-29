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
import com.searchitemsapp.business.enterprises.Company;
import com.searchitemsapp.business.enterprises.factory.CompaniesGroup;
import com.searchitemsapp.dao.CssSelectorsDao;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.CompanyDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.resources.Constants;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Component
@AllArgsConstructor
public class SelectorsCssImpl implements SelectorsCss {
	
	private static final String REGEX = "[()]";
	private static final String HTTPS = "https:";
	private CssSelectorsDao cssSelectorsDao;
	private Environment environment;
	private CompaniesGroup companiesGroup;

	@Override
	public List<CssSelectorsDto> selectorCssListByEnterprise(
			final String companyId) {

		String companiesIdSepearatedByCommas;
		
		if(Constants.ALL.getValue().equalsIgnoreCase(companyId)) {
			companiesIdSepearatedByCommas = environment.getProperty("flow.value.all.id.empresa");
		} else {
			companiesIdSepearatedByCommas = companyId;
		}
		
		StringTokenizer tokenizer = new StringTokenizer(companiesIdSepearatedByCommas, Constants.COMMA.getValue()); 			
		List<Integer> companiesIds = Lists.newArrayList();
		
		while (tokenizer.hasMoreElements()) {
			companiesIds.add(Integer.parseInt(String.valueOf(tokenizer.nextElement())));
		}
		
		List<CssSelectorsDto> cssSelectorDtoList = Lists.newArrayList();
		
		companiesIds.forEach(innerCompanyId -> {
			
			try {	
				cssSelectorDtoList.addAll(
						cssSelectorsDao.findByTbSia(
								CssSelectorsDto.builder().build(), 
								CompanyDto.builder().did(innerCompanyId).build()));
			
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
	public CssSelectorsDto addCssSelectors(UrlDto urlDTO, List<CssSelectorsDto> cssSelectors) {
		
		return cssSelectors
				.stream().filter(cssSelector -> cssSelector.getDidEmpresa().equals(urlDTO.getDidEmpresa()))
				.collect(Collectors.toList()).get(0);
	}
	
	public String elementByCssSelector(@NonNull Element documentElement, 
			@NonNull String cssSelector,
			@NonNull UrlDto urlDto) throws MalformedURLException {
				
		List<String> cssSelectorList = Lists.newArrayList();		
		StringTokenizer st = new StringTokenizer(cssSelector, Constants.PIPE.getValue());  
		
		while (st.hasMoreTokens()) {  
			cssSelectorList.add(st.nextToken());
		}
		
		Company company = companiesGroup.getInstance(urlDto.getDidEmpresa());
		
		String textExtracted = company.selectorTextExtractor(documentElement, cssSelectorList, cssSelector);
		
		return cleanProductTextExtractedFromCssSelector(textExtracted, urlDto.getNomUrl());
	}
	
	private String cleanProductTextExtractedFromCssSelector(@NonNull final String textProduct, 
			String strUrl) throws MalformedURLException {
		
		String textProductFiltered = textProduct.replaceAll(REGEX, StringUtils.EMPTY);			
		String urlName = StringUtils.EMPTY;
		
		if(Objects.nonNull(textProductFiltered) && textProductFiltered.trim().startsWith(Constants.DOUBLE_SLASH.getValue())) {
			urlName = HTTPS.concat(textProductFiltered);
		} else if(Objects.nonNull(textProductFiltered) && textProductFiltered.trim().startsWith(Constants.SLASH.getValue())) {
			URL url = new URL(strUrl);
			String companyUrl = url.getProtocol().concat(Constants.PROTOCOL_ACCESSOR.getValue()).concat(url.getHost());
			urlName = companyUrl.concat(textProductFiltered); 
		} else if(Objects.nonNull(textProductFiltered)){
			urlName = textProductFiltered;
		}
		 
		String resultado = urlName.replaceAll("\\(", StringUtils.EMPTY);
		resultado = resultado.replaceAll("\\)", StringUtils.EMPTY);
		
		resultado = resultado.replace("€", " eur");
		resultado = resultado.replace("Kilo", "kg");
		resultado = resultado.replace(" / ", Constants.SLASH.getValue());
		resultado = resultado.replace(" \"", "\"");
		 
		return resultado;
	}

}
