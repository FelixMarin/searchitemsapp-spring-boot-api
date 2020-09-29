package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.Brands;
import com.searchitemsapp.business.enterprises.Company;
import com.searchitemsapp.business.enterprises.factory.CompaniesGroup;
import com.searchitemsapp.dao.BrandsDao;
import com.searchitemsapp.dto.BrandsDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BrandsImpl implements Brands {
	
	private BrandsDao brandsDao;
	private CompaniesGroup companiesGroup;
	private static final List<BrandsDto> ALL_BRANDS = Lists.newArrayList();

	@Override
	public String brandFilter(final int companyId, final String productName) throws IOException {
		
		Company company = companiesGroup.getInstance(companyId);
		String productNameAux = company.removeInitialBrand(productName);

		final String evaluatedProduct = productNameAux;
		
		if(ALL_BRANDS.isEmpty()) {
			ALL_BRANDS.addAll(brandsDao.findAll());
		}
		
		ALL_BRANDS.stream()
				.filter(brandDto -> brandDto.getNomMarca().toLowerCase().startsWith(evaluatedProduct.toLowerCase()))
				.collect(Collectors.toList());

		return productNameAux.toLowerCase()
				.replaceAll(ALL_BRANDS.get(0).getNomMarca().toLowerCase(), StringUtils.EMPTY).trim();

	}

}
