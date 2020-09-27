package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.searchitemsapp.business.Brands;
import com.searchitemsapp.business.enterprises.Enterprise;
import com.searchitemsapp.business.enterprises.factory.EnterpriseFactory;
import com.searchitemsapp.dao.BrandsDao;
import com.searchitemsapp.dto.BrandsDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BrandsImpl implements Brands {
	
	private BrandsDao brandsDao;
	private EnterpriseFactory enterpriseFactory;

	@Override
	public String brandFilter(final int enterpriseId, final String productName, final List<BrandsDto> listAllBrands) {
		
		Enterprise enterprise = enterpriseFactory.getEnterpriseData(enterpriseId);
		String productNameAux = enterprise.eliminarMarcaPrincipio(productName);

		final String evaluatedProduct = productNameAux;
		listAllBrands.stream()
				.filter(brandDto -> brandDto.getNomMarca().toLowerCase().startsWith(evaluatedProduct.toLowerCase()))
				.collect(Collectors.toList());

		return productNameAux.toLowerCase()
				.replaceAll(listAllBrands.get(0).getNomMarca().toLowerCase(), StringUtils.EMPTY).trim();

	}
	
	@Override
	public List<BrandsDto> allBrandList() throws IOException  {
		return brandsDao.findAll();
	}
}
