package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.Products;
import com.searchitemsapp.business.SelectorsCss;
import com.searchitemsapp.business.Urls;
import com.searchitemsapp.business.enterprises.Company;
import com.searchitemsapp.business.enterprises.factory.CompaniesGroup;
import com.searchitemsapp.dao.UrlDao;
import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.CountryDto;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.SearchItemsParamsDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.resources.Constants;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UrlsImpl implements Urls {
	
	private Products products;
	private CompaniesGroup companiesGroup;
	private UrlDao urlDao;
	private CategoryDto categoryDto;
	private CountryDto countryDto;
	private SelectorsCss selectorsCss; 
	
	public List<UrlDto> replaceUrlWildcard(SearchItemsParamsDto productsInParametersDto,
			final List<CssSelectorsDto> listAllCssSelector) 
			throws IOException {
		
		countryDto.setDid(NumberUtils.toInt(productsInParametersDto.getCountryId()));		
		categoryDto.setDid(NumberUtils.toInt(productsInParametersDto.getCategoryId()));
		
		List<UrlDto> listUrlDto  = urlDao.obtenerUrlsPorIdEmpresa(countryDto, categoryDto, productsInParametersDto.getPipedEnterprises());
		
		List<UrlDto> listResultUrlDto = Lists.newArrayList();
	
		listUrlDto.forEach(urlDto -> {
			
			try {			
				urlDto.setSelectores(selectorsCss
						.addCssSelectors(urlDto, listAllCssSelector));
				
				Company company = companiesGroup.getInstance(urlDto.getDidEmpresa());
				String refinedProductName = company.reemplazarCaracteres(productsInParametersDto.getProduct());
				refinedProductName = products.manageProductName(refinedProductName);

				String urlAux = urlDto.getNomUrl();
				urlAux = urlAux.replace(Constants.WILDCARD.getValue(), refinedProductName);
				urlDto.setNomUrl(urlAux);
				listResultUrlDto.add(urlDto);
			}catch(IOException e) {
				throw new UncheckedIOException(e);
			}
		});
		
		return listResultUrlDto;
	}
}