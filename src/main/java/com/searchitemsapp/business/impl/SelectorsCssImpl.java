package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.SelectorsCss;
import com.searchitemsapp.company.factory.CompaniesGroup;
import com.searchitemsapp.dao.CssSelectorsDao;
import com.searchitemsapp.dto.CompanyDto;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.resource.Constants;

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
		
		var tokenizer = new StringTokenizer(companiesIdSepearatedByCommas, Constants.COMMA.getValue()); 			
		List<Long> companiesIds = Lists.newArrayList();
		
		while (tokenizer.hasMoreElements()) {
			companiesIds.add(Long.parseLong(String.valueOf(tokenizer.nextElement())));
		}
		
		List<CssSelectorsDto> cssSelectorDtoList = Lists.newArrayList();
		
		companiesIds.forEach(innerCompanyId -> {
			
			try {	
				cssSelectorDtoList.addAll(
						cssSelectorsDao.findByTbSia(CompanyDto
								.builder()
								.did(innerCompanyId)
								.build())); 
			
			}catch(IOException e) {
				throw new UncheckedIOException(e);
			}
		});
		
		return cssSelectorDtoList;
	}
	
	@Override
	public Optional<Element> validateSelector(Element documentElement) {
		boolean isValid = Objects.nonNull(documentElement.selectFirst(environment.getProperty("flow.value.pagina.siguiente.carrefour"))) ||
		Objects.nonNull(documentElement.selectFirst(environment.getProperty("flow.value.pagina.acceso.popup.peso")));
	
		return isValid?Optional.of(documentElement):Optional.empty();
	}
	
	@Override
	public CssSelectorsDto addCssSelectors(UrlDto urlDTO, List<CssSelectorsDto> cssSelectors) {
		
		return cssSelectors
				.stream().filter(cssSelector -> cssSelector.getDidEmpresa().equals(urlDTO.getDidEmpresa()))
				.collect(Collectors.toList()).get(0);
	} 
	
	public String elementByCssSelector(@NonNull Element documentElement, 
			@NonNull String cssSelector, @NonNull UrlDto urlDto) throws MalformedURLException {
				
		List<String> cssSelectorList = Lists.newArrayList();		
		var st = new StringTokenizer(cssSelector, Constants.PIPE.getValue());  
		
		while (st.hasMoreTokens()) {  
			cssSelectorList.add(st.nextToken());
		}
		
		var company = companiesGroup.getInstance(urlDto.getDidEmpresa());
		
		var textExtracted = company.selectorTextExtractor(documentElement, cssSelectorList, cssSelector);
		
		return cleanProductTextExtractedFromCssSelector(textExtracted, urlDto.getNomUrl());
	}
	
	private String cleanProductTextExtractedFromCssSelector(@NonNull final String elementValue, 
			String mainUrl) throws MalformedURLException {
		
		var tratedUrl = elementValue.replaceAll(REGEX, StringUtils.EMPTY);
		var filteredResult = addProtocolToUrl(tratedUrl, mainUrl);
		
		filteredResult = filteredResult.replace("\\(", StringUtils.EMPTY);
		filteredResult = filteredResult.replace("\\)", StringUtils.EMPTY);		
		filteredResult = filteredResult.replace("€", " eur");
		filteredResult = filteredResult.replace("Kilo", "kg");
		filteredResult = filteredResult.replace(" / ", Constants.SLASH.getValue());
		filteredResult = filteredResult.replace(" \"", "\"");
		 
		return filteredResult;
	}
	
	private String addProtocolToUrl(@NonNull String tratedUrl, @NonNull String mainUrl)
			throws MalformedURLException {
		
		if(tratedUrl.trim().startsWith(Constants.DOUBLE_SLASH.getValue())) {
			return HTTPS.concat(tratedUrl);
		} else if(tratedUrl.trim().startsWith(Constants.SLASH.getValue())) {
			var url = new URL(mainUrl);
			var companyUrl = url.getProtocol()
					.concat(Constants.PROTOCOL_ACCESSOR.getValue())
					.concat(url.getHost());
			return companyUrl.concat(tratedUrl); 
		}
		
		return tratedUrl;
	}

}
