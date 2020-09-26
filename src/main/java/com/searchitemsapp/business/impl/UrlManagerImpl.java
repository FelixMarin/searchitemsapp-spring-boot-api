package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.ProductManager;
import com.searchitemsapp.business.SelectorCssManager;
import com.searchitemsapp.business.UrlManager;
import com.searchitemsapp.business.enterprises.Enterprise;
import com.searchitemsapp.business.enterprises.factory.EnterpriseFactory;
import com.searchitemsapp.dao.UrlDao;
import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.CountryDto;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UrlManagerImpl implements UrlManager {
	
	private ProductManager productManager;
	private EnterpriseFactory enterpriseFactory;
	private UrlDao urlDao;
	private CategoryDto categoryDto;
	private CountryDto countryDto;
	private SelectorCssManager selectorCssManager;
	
	public List<UrlDto> replaceUrlWildcard(Map<String,String> requestParams,
			final List<CssSelectorsDto> listAllCssSelector) 
			throws IOException {
		
		countryDto.setDid(NumberUtils.toInt(requestParams.get("COUNTRY_ID")));		
		categoryDto.setDid(NumberUtils.toInt(requestParams.get("CATEGORY_ID")));
		
		List<UrlDto> listUrlDto  = urlDao.obtenerUrlsPorIdEmpresa(countryDto, categoryDto, requestParams.get("ENTERPRISES"));
		
		List<UrlDto> listResultUrlDto = Lists.newArrayList();
	
		listUrlDto.forEach(urlDto -> {
			
			try {			
				urlDto.setSelectores(selectorCssManager
						.cargaSelectoresCss(urlDto, listAllCssSelector));
				
				Enterprise enterprise = enterpriseFactory.getEnterpriseData(urlDto.getDidEmpresa());
				String productoTratado = enterprise.reemplazarCaracteres(requestParams.get("PRODUCT_NAME"));
				productoTratado = productManager.tratarProducto(productoTratado);
								
				String urlAux = urlDto.getNomUrl();
				urlAux = urlAux.replace("{1}", productoTratado);
				urlDto.setNomUrl(urlAux);
				listResultUrlDto.add(urlDto);
			}catch(IOException e) {
				throw new UncheckedIOException(e);
			}
		});
		
		return listResultUrlDto;
	}
}